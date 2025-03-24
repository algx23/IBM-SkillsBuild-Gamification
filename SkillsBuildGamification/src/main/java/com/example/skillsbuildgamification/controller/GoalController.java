package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.Goal;
import com.example.skillsbuildgamification.model.GoalStatus;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.GoalRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.service.GoalService;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class GoalController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GoalService goalService;

    @Autowired
    private GoalRepository goalRepository;

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
    public String saveGoal(@ModelAttribute Goal goal, @PathVariable String username, Model model) {
        try {
            goalService.saveGoal(goal, username);
            return "redirect:/" + username + "/dashboard-page"; // Redirect on success
        } catch (IllegalArgumentException | IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("goal", goal);
            return "dashboard/goal-creation"; // Stay on the form
        }
    }

    @GetMapping("/completedGoals/{username}")
    public String viewCompletedGoals(@PathVariable String username, Model model) {
        List<Goal> completedGoals = goalService.getCompletedGoals(username);
        model.addAttribute("completedGoals", completedGoals);
        return "dashboard/completedGoals";
    }

    @GetMapping("/{username}/delete-goal")
    public String deleteActiveGoal(@PathVariable String username) {
        goalService.deleteActiveGoal(username);
        return "redirect:/" + username + "/dashboard-page"; // Redirect back to dashboard
    }
}

