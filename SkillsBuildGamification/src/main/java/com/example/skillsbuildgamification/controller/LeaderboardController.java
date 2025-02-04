package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LeaderboardController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/leaderboard")
    public String leaderboard(Model model, Principal principal) {
//        fetch currently logged in student
//        Student student = studentRepository.findByUsername(principal.getName());

/*        if a student is logged in, they can view the leaderboard,
 *        if not, they will be redirected to login-page
*/

//        NOTE: THE BELOW IF STATEMENT HAS BEEN COMMENTED OUT FOR TESTING THE LEADERBOARD WORKS
//        it will be properly implemented, once login functionality has been finalized
//  ----------------------------------------------------------------------------------------
//        if (student == null) {
//        this should work when login is fully implemented
//            return "redirect:/login-page";
//        } else {}
// -------------------------------------------------------------------------------------------

////            add all students to the repo, so they can ALL be ordered TODO: order students
            model.addAttribute("students", studentRepository.findAllByOrderByPointsDesc());
            return "leaderboard/leaderboard";
//        }
        }
}
