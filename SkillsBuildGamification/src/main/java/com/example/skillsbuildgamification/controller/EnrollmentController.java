package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.Course;
import com.example.skillsbuildgamification.model.Enrollment;
import com.example.skillsbuildgamification.model.EnrollmentStatus;
import com.example.skillsbuildgamification.repository.CourseRepository;
import com.example.skillsbuildgamification.repository.EnrollmentRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.service.EnrollmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
@RequestMapping("/{username}/courseStatus")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/{courseId}")
    public String showCourseStatus(
            @PathVariable String username,
            @PathVariable int courseId,
            Model model) {

        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) {
            return "redirect:/" + username + "/dashboard-page";
        }

        Course course = courseOpt.get();
        Optional<Enrollment> enrollmentOpt = enrollmentRepository.findByStudentAndCourse(
                studentRepository.findByUsername(username), course);

        String courseStatus = enrollmentOpt.map(enrollment -> enrollment.getStatus().toString()).orElse("NOT_STARTED");

        model.addAttribute("username", username);
        model.addAttribute("course", course);
        model.addAttribute("courseStatus", courseStatus);

        return "dashboard/courseStatus";
    }

    @PostMapping("/{courseId}/update")
    public String updateCourseStatus(
            @PathVariable String username,
            @PathVariable int courseId,
            @RequestParam("status") String status,
            @RequestParam(name = "sourcePage", required = false) String sourcePage,
            RedirectAttributes redirectAttributes) {

        try {
            EnrollmentStatus enrollmentStatus = EnrollmentStatus.valueOf(status.toUpperCase());
            enrollmentService.updateEnrollment(username, courseId, enrollmentStatus);
            redirectAttributes.addFlashAttribute("successMessage", "Course status updated successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Invalid status value provided.");
        }

        return "redirect:/" + username + "/dashboard-page";
    }
}










