package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.Goal;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GoalController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GoalService goalService;

    /**
     * Displays the goal creation form for a specific student.
     */
    @RequestMapping("{username}/goal-creation")
    public String goalCreation(@PathVariable("username") String username, Model model) {
        Student student = studentRepository.findByUsername(username);

        // Redirect to login if the student does not exist
        if (student == null) {
            return "redirect:/login-page";
        }

        // Add goal object and username to the model for JSP binding
        model.addAttribute("goal", new Goal());
        model.addAttribute("username", username);

        return "dashboard/goal-creation"; // Ensure this JSP exists in the correct location
    }

    /**
     * Handles goal creation and redirects the student back to their dashboard.
     */
    @PostMapping("/{username}/save-goal")
    public String saveGoal(@PathVariable("username") String username, @ModelAttribute("goal") Goal goal) {
        goalService.saveGoal(goal, username);
        return "redirect:/" + username + "/dashboard-page";
    }
}

