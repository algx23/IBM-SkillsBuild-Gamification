package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private StudentRepository studentRepository;

    /**
     * View a user's profile page, by retrieving a student's profile by username.
     */
    @GetMapping("/{username}")
    public String showUserProfile(@PathVariable String username, Model model) {
        Student student = studentRepository.findByUsername(username);
        if (student == null) {
            return "redirect:/login-page"; // Redirect if user not found
        }
        model.addAttribute("student", student);
        return "profile/profile";  // profile.jsp (view profile page)
    }

    /**
     * To display a form for editing profile details.
     */
    @GetMapping("/{username}/edit")
    public String showEditProfileForm(@PathVariable String username, Model model) {
        // TODO: Retrieve user data and populate edit form
        return "profile/edit";  // Placeholder for edit profile page (edit.jsp)
    }

    /**
     * Handle profile updates
     */

    @PostMapping("/update")
    public String updateProfile() {
        // TODO: Implement update logic
        return "redirect:/profile/{username}";  // Redirect back to profile after updating
    }
}
