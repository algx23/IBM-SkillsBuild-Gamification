package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class LeaderboardController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/leaderboard")
    public String leaderboard(@RequestParam(defaultValue = "") String sortBy, Model model, Principal principal, Sort sort) {
        /*
         * if a student is logged in, they can view the leaderboard,
         * if not, they will be redirected to login-page
         */
        if (principal == null) {
            return "redirect:/login-page";
        }

        //          adding current username to the model, so they can be highlighted
        model.addAttribute("loggedInStudentUsername", principal.getName());

//        sorting based on the request parameter
        switch (sortBy) {
            /*
            * if the sortBy param is empty, default sorting will be used. This is sorting with the Student with the
            * highest number of points first, and others following in descending order
            * */
            case (""):
            // add all students to the model, ordered with the highest number of points first
            model.addAttribute("students", studentRepository.findAllByOrderByPointsDesc());
            break;

            case("lowestScoreFirst"):
                model.addAttribute("students", studentRepository.findAllByOrderByPointsAsc());
            break;

            case ("A-Z"):
                model.addAttribute("students", studentRepository.findAllByOrderByUsernameAsc());
            break;

            case ("Z-A"):
                model.addAttribute("students", studentRepository.findAllByOrderByUsernameDesc());
            break;
        }
            return "leaderboard/leaderboard";
    }

//  post mapping to change the sort by param based on the button pressed
    @PostMapping("/leaderboard")
    public String redirectToSort(@RequestParam("sortBy") String sortBy) {
        return "redirect:/leaderboard?sortBy=" + sortBy;
    }
}
