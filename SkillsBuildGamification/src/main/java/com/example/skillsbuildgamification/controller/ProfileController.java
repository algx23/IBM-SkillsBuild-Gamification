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
public class ProfileController { // Handles all profile management related endpoints

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    // Defined requirements for each badge to add as attribute for display
    private static final Map<String, Integer> badgeRequirements = Map.of(
            "Novice Badge", 1,
            "Intermediate Badge", 3,
            "Advanced Badge", 5,
            "Expert Badge", 7
    );

    @GetMapping("/{username}") // Method displays User information along with their courses
    public String showUserProfile(@PathVariable String username, Model model, Principal principal) {
        Student student = studentRepository.findByUsername(username);
        if (student == null) {
            return "redirect:/login-page";
        }

        String loggedInUsername = (principal != null) ? principal.getName() : null;
        boolean isOwner = loggedInUsername != null && loggedInUsername.equals(username); // defining logged in user
        int rank = studentRepository.findLeaderboardPosition(username); // find rank from repo method

        List<Enrollment> enrollments = enrollmentRepository.findByStudent(student); // get all student enrollments

        // Separate "Completed" & "In Progress" courses
        List<Course> completedCourses = enrollments.stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.COMPLETED)
                .map(Enrollment::getCourse)
                .collect(Collectors.toList());

        List<Course> inProgressCourses = enrollments.stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.IN_PROGRESS)
                .map(Enrollment::getCourse)
                .collect(Collectors.toList());

        model.addAttribute("student", student); // add student
        model.addAttribute("isOwner", isOwner); // add type of user to grant edit privilege
        model.addAttribute("loggedInStudentUsername", loggedInUsername); // add username
        model.addAttribute("leaderboardPosition", rank); // add rank
        model.addAttribute("completedCourses", completedCourses); // add courses completed
        model.addAttribute("inProgressCourses", inProgressCourses); // add courses in progress
        model.addAttribute("badgeRequirements", badgeRequirements); // add requirements to display badges
        model.addAttribute("url", student.getUserRank().getUrl()); // add url for rank img

        return "profile/profile"; // profile.jsp
    }


    @GetMapping("/{username}/edit") // method to display edit profile form
    public String showEditProfileForm(@PathVariable String username, Model model, Principal principal) {
        if (principal == null || !principal.getName().equals(username)) {
            return "redirect:/denied"; // Restrict unauthorized access
        }

        Student student = studentRepository.findByUsername(username);
        if (student == null) {
            return "redirect:/login-page"; // ensuring student is actually present
        }

        List<Enrollment> enrollments = enrollmentRepository.findByStudent(student); // finding student enrollments

        // Separate "Completed" & "In Progress" courses
        List<Course> completedCourses = enrollments.stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.COMPLETED)
                .map(Enrollment::getCourse)
                .collect(Collectors.toList());

        List<Course> inProgressCourses = enrollments.stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.IN_PROGRESS)
                .map(Enrollment::getCourse)
                .collect(Collectors.toList());

        model.addAttribute("student", student); // add student
        model.addAttribute("completedCourses", completedCourses); // add courses completed
        model.addAttribute("inProgressCourses", inProgressCourses); // add courses in progress

        return "profile/edit"; // edit.jsp
    }


    @PostMapping("/{username}/update") // method to handle user info update
    public String updateProfile(@PathVariable String username, // pass username as path variable
                                @RequestParam String password, // password param
                                @RequestParam String bio, // bio as param
                                @RequestParam String profilePic, // profile pic url as param
                                Principal principal) {

        if (principal == null || !principal.getName().equals(username)) {
            return "redirect:/denied"; // Prevent unauthorized changes
        }

        Student student = studentRepository.findByUsername(username);
        if (student != null) {
            if (!password.isEmpty()) {
                student.setPassword(passwordEncoder.encoder().encode(password)); // encode new password
            }
            student.setBio(bio); // add bio
            student.setProfilePic(profilePic); // set profile pic
            studentRepository.save(student); // save changes
        }
        return "redirect:/profile/" + username; // Redirect to updated profile
    }
}
