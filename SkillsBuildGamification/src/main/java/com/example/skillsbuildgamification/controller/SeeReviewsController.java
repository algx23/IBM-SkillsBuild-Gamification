package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.Course;
import com.example.skillsbuildgamification.model.Review;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.CourseRepository;
import com.example.skillsbuildgamification.repository.ReviewRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SeeReviewsController {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    @RequestMapping("/seeReviews")
    public String addReview(@RequestParam String courseName,@RequestParam String username ,Model model) {
        Student student = studentRepository.findByUsername(username);
        Course course = courseRepository.findBycourseName(courseName);
        List<Review> reviews = reviewRepository.findAllByCourseAssociated(course);
        model.addAttribute("student", student);
        model.addAttribute("course", course);
        model.addAttribute("reviews", reviews);
        return "review/viewReviews";
    }
}
