package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.EnrollmentStatus;
import com.example.skillsbuildgamification.service.EnrollmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/{username}/courseStatus")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/{courseId}")
    public String updateCourseStatus(
            @PathVariable String username,
            @PathVariable int courseId,
            @RequestParam("status") String status,
            RedirectAttributes redirectAttributes) {

        try {
            EnrollmentStatus enrollmentStatus = EnrollmentStatus.valueOf(status.toUpperCase());
            enrollmentService.updateEnrollment(username, courseId, enrollmentStatus);

            redirectAttributes.addFlashAttribute("successMessage", "Course status updated successfully!");

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Invalid status value provided.");
        }

        return "redirect:/" + username + "/courseStatus/" + courseId;
    }
}





