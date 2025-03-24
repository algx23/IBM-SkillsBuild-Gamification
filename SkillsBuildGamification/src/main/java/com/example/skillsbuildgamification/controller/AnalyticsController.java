package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.Course;
import com.example.skillsbuildgamification.model.GoalStatus;
import com.example.skillsbuildgamification.repository.CourseRepository;
import com.example.skillsbuildgamification.repository.GoalRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;

@Controller
public class AnalyticsController {
    // Handles all analytics-related endpoints

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    GoalRepository goalRepository;

    @RequestMapping("/analytics")
    public String analyticsPage(Model model) {
        // Show the analytics page

        // Retrieve all available courses and their links
        List<Course> courses = (List<Course>) courseRepository.findAll();
        List<String> courseLinks = courseRepository.findAllCourseLinks();

        // Retrieve counts for the number of goals with each status
        int goalsActive = goalRepository.countByStatusEquals(GoalStatus.ACTIVE);
        int goalsCompleted = goalRepository.countByStatusEquals(GoalStatus.COMPLETED);
        int goalsExpired = goalRepository.countByStatusEquals(GoalStatus.EXPIRED);

        // Add attributes to the model
        model.addAttribute("courses", courses);
        model.addAttribute("goalsActive", goalsActive);
        model.addAttribute("goalsCompleted", goalsCompleted);
        model.addAttribute("goalsExpired", goalsExpired);

        return "admin/analytics";
    }

}

