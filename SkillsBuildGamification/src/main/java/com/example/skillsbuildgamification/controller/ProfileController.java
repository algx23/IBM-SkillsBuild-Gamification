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
            return "redirect:/login-page";
        }
        model.addAttribute("student", student);
        return "profile/profile";  // This is your profile.jsp
    }


    /**
     * To display a form for editing profile details.
     */
    @GetMapping("/{username}/edit")
    public String showEditProfileForm(@PathVariable String username, Model model) {
        Student student = studentRepository.findByUsername(username);
        if (student == null) {
            return "redirect:/login-page";
        }
        model.addAttribute("student", student);
        return "profile/edit";
    }


    /**
     * Handle profile updates
     */

    // Needs Correct Implementation to update ones profile. !!!!
    @PostMapping("/update")
    public String updateProfile(@RequestParam String username,
                                @RequestParam int level,
                                @RequestParam String completedCourses) {
        Student student = studentRepository.findByUsername(username);
        if (student != null) {
            student.setLevel(level);
            student.setCompletedCourses(completedCourses);
            studentRepository.save(student);
        }
        return "redirect:/profile/" + username;  // Redirect to updated profile
    }

}
