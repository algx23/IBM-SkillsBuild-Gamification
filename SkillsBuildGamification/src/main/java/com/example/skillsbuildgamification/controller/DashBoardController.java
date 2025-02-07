package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.Course;
import com.example.skillsbuildgamification.repository.CourseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class DashBoardController
{
    private final CourseRepository courseRepository;

    public DashBoardController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @RequestMapping("/dashboard-page")
    public String dashboardPage(Model model)
    {
        List<Course> courses = (List<Course>) courseRepository.findAll();
        model.addAttribute("courses", courses);
        return "dashboard";
    }
}
