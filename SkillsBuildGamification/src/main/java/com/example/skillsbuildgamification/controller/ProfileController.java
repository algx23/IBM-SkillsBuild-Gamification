package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.config.PasswordEncoder;
import com.example.skillsbuildgamification.model.Course;
import com.example.skillsbuildgamification.model.Enrollment;
import com.example.skillsbuildgamification.model.EnrollmentStatus;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.EnrollmentRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    // Defined requirements for each badge to pass as parameter for display
    private static final Map<String, Integer> badgeRequirements = Map.of(
            "Novice Badge", 1,
            "Intermediate Badge", 3,
            "Advanced Badge", 5,
            "Expert Badge", 7
    );

    @GetMapping("/{username}")
    public String showUserProfile(@PathVariable String username, Model model, Principal principal) {
        Student student = studentRepository.findByUsername(username);
        if (student == null) {
            return "redirect:/login-page";
        }

        String loggedInUsername = (principal != null) ? principal.getName() : null;
        boolean isOwner = loggedInUsername != null && loggedInUsername.equals(username);
        int rank = studentRepository.findLeaderboardPosition(username);

        List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);

        // Separate "Completed" & "In Progress" courses
        List<Course> completedCourses = enrollments.stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.COMPLETED)
                .map(Enrollment::getCourse)
                .collect(Collectors.toList());

        List<Course> inProgressCourses = enrollments.stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.IN_PROGRESS)
                .map(Enrollment::getCourse)
                .collect(Collectors.toList());

        model.addAttribute("student", student);
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("loggedInStudentUsername", loggedInUsername);
        model.addAttribute("leaderboardPosition", rank);
        model.addAttribute("completedCourses", completedCourses);
        model.addAttribute("inProgressCourses", inProgressCourses);
        model.addAttribute("badgeRequirements", badgeRequirements);
        model.addAttribute("url", student.getUserRank().getUrl());

        return "profile/profile"; // profile.jsp
    }


    @GetMapping("/{username}/edit")
    public String showEditProfileForm(@PathVariable String username, Model model, Principal principal) {
        if (principal == null || !principal.getName().equals(username)) {
            return "redirect:/denied"; // Restrict unauthorized access
        }

        Student student = studentRepository.findByUsername(username);
        if (student == null) {
            return "redirect:/login-page";
        }

        List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);

        // Separate "Completed" & "In Progress" courses
        List<Course> completedCourses = enrollments.stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.COMPLETED)
                .map(Enrollment::getCourse)
                .collect(Collectors.toList());

        List<Course> inProgressCourses = enrollments.stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.IN_PROGRESS)
                .map(Enrollment::getCourse)
                .collect(Collectors.toList());

        model.addAttribute("student", student);
        model.addAttribute("completedCourses", completedCourses);
        model.addAttribute("inProgressCourses", inProgressCourses);

        return "profile/edit"; // edit.jsp
    }


    @PostMapping("/{username}/update")
    public String updateProfile(@PathVariable String username,
                                @RequestParam String password,
                                @RequestParam String bio,
                                @RequestParam String profilePic,
                                Principal principal) {

        if (principal == null || !principal.getName().equals(username)) {
            return "redirect:/denied"; // Prevent unauthorized changes
        }

        Student student = studentRepository.findByUsername(username);
        if (student != null) {
            if (!password.isEmpty()) {
                student.setPassword(passwordEncoder.encoder().encode(password));
            }
            student.setBio(bio);
            student.setProfilePic(profilePic);
            studentRepository.save(student);
        }
        return "redirect:/profile/" + username; // Redirect to updated profile
    }
}
