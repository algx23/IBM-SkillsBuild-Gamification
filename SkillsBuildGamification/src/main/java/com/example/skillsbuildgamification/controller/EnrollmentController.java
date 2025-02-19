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

    /**
     * Handles the update of a course enrollment status.
     * Converts the status parameter to an enum and updates the enrollment record.
     * Redirects to either the dashboard or edit profile page
     */
    @PostMapping("/{courseId}")
    public String updateCourseStatus(
            @PathVariable String username,
            @PathVariable int courseId,
            @RequestParam("status") String status,
            @RequestParam(name = "sourcePage", required = false) String sourcePage,
            RedirectAttributes redirectAttributes) {

        try {
            // Convert the provided status string to an enum
            EnrollmentStatus enrollmentStatus = EnrollmentStatus.valueOf(status.toUpperCase());
            enrollmentService.updateEnrollment(username, courseId, enrollmentStatus);

            // Add a success message for the redirected page
            redirectAttributes.addFlashAttribute("successMessage", "Course status updated successfully!");

        } catch (IllegalArgumentException e) {
            // Handle invalid status values
            redirectAttributes.addFlashAttribute("error", "Invalid status value provided.");
        }

        // Redirect based on the source page parameter
        return ("editProfile".equals(sourcePage))
                ? "redirect:/profile/" + username + "/edit"
                : "redirect:/" + username + "/dashboard-page";
    }
}






