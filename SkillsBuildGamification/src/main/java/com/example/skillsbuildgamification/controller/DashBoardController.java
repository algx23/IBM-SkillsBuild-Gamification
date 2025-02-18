package com.example.skillsbuildgamification.controller;
import com.example.skillsbuildgamification.model.Course;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.CourseRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class DashBoardController
{
    @Autowired
    private StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    public DashBoardController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @RequestMapping("{username}/dashboard-page")
    public String dashboardPage(@PathVariable String username, Model model)
    {
        Student student = studentRepository.findByUsername(username);
        if (student == null)
        {
            return "redirect:/login-page";
        }
        List<Course> courses = (List<Course>) courseRepository.findAll();
        List<String> courseLinks = courseRepository.findAllCourseLinks();
        model.addAttribute("courses", courses);
        model.addAttribute("username", username);
        model.addAttribute("courseLinks", courseLinks);
        return "dashboard/dashboard";
    }

    @RequestMapping("{username}/courseStatus/{courseID}")
    public String courseStatus(@PathVariable String username, @PathVariable int courseID, Model model)
    {
        
        Course course = courseRepository.findBycourseID(courseID);
        model.addAttribute("username", username);
        model.addAttribute("course", course);
        return "dashboard/courseStatus";
    }
}
