package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.Course;
import com.example.skillsbuildgamification.repository.CourseRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AnalyticsController {
    // Handles all analytics-related endpoints

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @RequestMapping("/analytics")
    public String analyticsPage(Model model) {
        // Show the analytics page

        // Retrieve all available courses and their links
        List<Course> courses = (List<Course>) courseRepository.findAll();
        List<String> courseLinks = courseRepository.findAllCourseLinks();

        // Add attributes to the model
        model.addAttribute("courses", courses);

        return "admin/analytics";
        }

}

