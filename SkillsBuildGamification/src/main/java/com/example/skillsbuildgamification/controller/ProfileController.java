package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.config.PasswordEncoder;
import com.example.skillsbuildgamification.model.Course;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.CourseRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CourseRepository courseRepository;


    @GetMapping("/{username}")
    public String showUserProfile(@PathVariable String username, Model model, Principal principal) {
        Student student = studentRepository.findByUsername(username);
        if (student == null) {
            return "redirect:/login-page";
        }

        String loggedInUsername = (principal != null) ? principal.getName() : null;
        boolean isOwner = loggedInUsername != null && loggedInUsername.equals(username);
        int rank = studentRepository.findLeaderboardPosition(username);

        model.addAttribute("student", student);
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("loggedInStudentUsername", loggedInUsername);
        model.addAttribute("leaderboardPosition", rank);

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

        model.addAttribute("student", student);
        return "profile/edit"; // edit.jsp
    }


    @PostMapping("/{username}/update")
    public String updateProfile(@PathVariable String username,
                                @RequestParam String newUsername,
                                @RequestParam String password,
                                @RequestParam String bio,
                                @RequestParam String profilePic,
                                Principal principal) {

        if (principal == null || !principal.getName().equals(username)) {
            return "redirect:/denied"; // Prevent unauthorized changes
        }

        Student student = studentRepository.findByUsername(username);
        if (student != null) {
            student.setUsername(newUsername);

            if (!password.isEmpty()) {
                student.setPassword(passwordEncoder.encoder().encode(password));
            }
            student.setBio(bio);
            student.setProfilePic(profilePic);
            studentRepository.save(student);
        }
        return "redirect:/profile/" + newUsername; // Redirect to updated profile
    }

}
