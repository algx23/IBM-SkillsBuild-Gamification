package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.Course;
import com.example.skillsbuildgamification.model.Goal;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.service.GoalService;
import com.example.skillsbuildgamification.repository.CourseRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DashBoardController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GoalService goalService;

    private final CourseRepository courseRepository;

    public DashBoardController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @RequestMapping("{username}/dashboard-page")
    public String dashboardPage(@PathVariable String username, Model model) {
        Student student = studentRepository.findByUsername(username);

        if (student == null) {
            return "redirect:/login-page";
        }

        List<Course> courses = (List<Course>) courseRepository.findAll();
        List<Goal> activeGoals = goalService.getActiveGoals(username);

        Map<Integer, Integer> goalProgressMap = new HashMap<>();
        for (Goal goal : activeGoals) {
            goalProgressMap.put(goal.getGoalID(), goalService.getGoalProgress(goal));
        }

        model.addAttribute("courses", courses);
        model.addAttribute("username", username);
        model.addAttribute("enrollments", student.getEnrollments());
        model.addAttribute("activeGoals", activeGoals);
        model.addAttribute("goalProgressMap", goalProgressMap);

        return "dashboard/dashboard";
    }
}




