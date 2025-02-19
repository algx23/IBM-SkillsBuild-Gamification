package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.service.LeaderboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LeaderboardController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LeaderboardService leaderboardService;

    @GetMapping("/leaderboard")
    public String leaderboard(@RequestParam(defaultValue = "") String sortBy, Model model, Principal principal) {
        /*
         * if a student is logged in, they can view the leaderboard,
         * if not, they will be redirected to login-page
         */
        if (principal == null) {
            return "redirect:/login-page";
        }
        Student loggedInStudent = studentRepository.findByUsername(principal.getName());
        // adding the currently logged in student to the repo so they can be highlighted
        model.addAttribute("loggedInStudent", loggedInStudent);

        String sortingOrder = "";

        List<Student> sortedList = new ArrayList<>(); // declaring list to sort

        // sorting based on the request parameter
        switch (sortBy) {
            /*
             * if the sortBy param is empty, default sorting will be used. This is sorting
             * with the Student with the
             * highest number of points first, and others following in descending order
             */
            case (""):
                // add all students to the model, ordered with the highest number of points
                // first
                sortedList = studentRepository.findAllByOrderByPointsDesc();
                sortingOrder = "HighestScoreFirst";
                model.addAttribute("loggedInStudentPos",
                        studentRepository.findLeaderboardPosition(loggedInStudent.getUsername()));
                break;

            case ("lowestScoreFirst"):
                sortedList = studentRepository.findAllByOrderByPointsAsc();
                sortingOrder = "lowest score first";
                break;

            case ("A-Z"):
                sortedList = studentRepository.findAllByOrderByUsernameAsc();
                sortingOrder = "Username (A-Z)";
                break;

            case ("Z-A"):
                sortedList = studentRepository.findAllByOrderByUsernameDesc();
                sortingOrder = "Username (Z - A)";
                break;

            case ("LowestCourseFirst"):
                sortedList = studentRepository.findAllByOrderByNumCompletedCoursesAsc();
                sortingOrder = "Courses Completed (low to high)";
                break;

            case ("HighestCourseFirst"):
                sortedList = studentRepository.findAllByOrderByNumCompletedCoursesDesc();
                sortingOrder = "Courses Completed (high to low)";
                break;

        }

        model.addAttribute("sortingOrder", sortingOrder);
        model.addAttribute("students", sortedList);
        model.addAttribute("leaderboardPos", leaderboardService.findAndHandleJointLBPos(sortedList));

        return "leaderboard/leaderboard";
    }

    // post mapping to change the sort by param based on the button pressed
    @PostMapping("/leaderboard")
    public String redirectToSort(@RequestParam("sortBy") String sortBy) {
        return "redirect:/leaderboard?sortBy=" + sortBy;
    }
}