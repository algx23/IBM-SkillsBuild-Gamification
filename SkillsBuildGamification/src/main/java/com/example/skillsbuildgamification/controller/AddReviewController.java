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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AddReviewController {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @RequestMapping("/addReviews")
    public String seeReviews( @RequestParam String courseName, @RequestParam String username, Model model) {
        Student student = studentRepository.findByUsername(username);
        Course course = courseRepository.findBycourseName(courseName);
        Review review = new Review();
        model.addAttribute("course", course);
        model.addAttribute("student", student);
        model.addAttribute("review", review);
        return "review/createReview";
    }

    @RequestMapping("/createReview")
    String createReview(@ModelAttribute("review") Review review, BindingResult result, @RequestParam String courseName, @RequestParam String username, Model model) {
        Course course = courseRepository.findBycourseName(courseName);
        Student student = studentRepository.findByUsername(username);
        if(result.hasErrors()){
            model.addAttribute("course", course);
            model.addAttribute("student", student);
            return "review/createReview";
        }


        for (int j = 0; j < student.getReviewsByStudent().size(); j++) {
            if(course.getReviews().contains(student.getReviewsByStudent().get(j))){
                reviewRepository.delete(student.getReviewsByStudent().get(j));
            }
        }
        course.getReviews().add(review);
        review.setCourseAssociated(course);
        review.setStudentId(student);
        reviewRepository.save(review);
        return "redirect:/seeReviews?courseName=" + courseName + "&username=" + username;
    }
}
