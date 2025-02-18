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
public class DashBoardController {

    @Autowired
    private StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    // Constructor-based dependency injection for CourseRepository
    public DashBoardController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Handles requests for the dashboard page.
     * Retrieves student information and available courses, then populates the model.
     * Redirects to the login page if the student does not exist.
     */
    @RequestMapping("{username}/dashboard-page")
    public String dashboardPage(@PathVariable String username, Model model) {
        Student student = studentRepository.findByUsername(username);

        // Redirect to login if the student does not exist
        if (student == null) {
            return "redirect:/login-page";
        }

        // Retrieve all available courses and their links
        List<Course> courses = (List<Course>) courseRepository.findAll();
        List<String> courseLinks = courseRepository.findAllCourseLinks();

        // Add attributes to the model for rendering in the dashboard view
        model.addAttribute("courses", courses);
        model.addAttribute("username", username);
        model.addAttribute("courseLinks", courseLinks);
        model.addAttribute("enrollments", student.getEnrollments());

        return "dashboard/dashboard";
    }

    /**
     * Handles requests for viewing the status of a specific course.
     * Retrieves course details and adds them to the model for display.
     */
    @RequestMapping("{username}/courseStatus/{courseID}")
    public String courseStatus(@PathVariable String username, @PathVariable int courseID, Model model) {
        // Find the requested course by its ID
        Course course = courseRepository.findBycourseID(courseID);

        // Add attributes to the model for course status view
        model.addAttribute("username", username);
        model.addAttribute("course", course);

        return "dashboard/courseStatus";
    }
}

