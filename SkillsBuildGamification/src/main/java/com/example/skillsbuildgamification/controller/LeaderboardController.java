package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class LeaderboardController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/leaderboard")
    public String leaderboard(Model model, Principal principal) {
        /*
         * if a student is logged in, they can view the leaderboard,
         * if not, they will be redirected to login-page
         */

        if (principal == null) {
            return "redirect:/login-page";
        } else {

            Student student = studentRepository.findByUsername(principal.getName());
            // add all students to the repo, so they can ALL be ordered
            model.addAttribute("students", studentRepository.findAllByOrderByPointsDesc());
//          adding current username to the model, so they can be highlighted
            model.addAttribute("loggedInStudentUsername", principal.getName());

//            TODO: Implement sorting by eg, lowest points first, rank (when implemented)...

            return "leaderboard/leaderboard";
        }
    }
}
