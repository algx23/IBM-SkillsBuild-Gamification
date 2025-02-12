package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.StudentRepository;
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
    public String leaderboard(@RequestParam(defaultValue = "") String sortBy, Model model, Principal principal,
            Sort sort) {
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
                model.addAttribute("sortingOrder", "Highest Points First");
                model.addAttribute("students", studentRepository.findAllByOrderByPointsDesc());
                model.addAttribute("loggedInStudentPos",
                        findLeaderboardPosition(loggedInStudent,
                                (List<Student>) studentRepository.findAllByOrderByPointsDesc()));
                break;

            case ("lowestScoreFirst"):
                model.addAttribute("sortingOrder", "Lowest Points First");
                model.addAttribute("students", studentRepository.findAllByOrderByPointsAsc());
                model.addAttribute("loggedInStudentPos",
                        findLeaderboardPosition(loggedInStudent,
                                (List<Student>) studentRepository.findAllByOrderByPointsAsc()));
                break;

            case ("A-Z"):
                model.addAttribute("sortingOrder", "Username (A - Z)");
                model.addAttribute("students", studentRepository.findAllByOrderByUsernameAsc());
                model.addAttribute("loggedInStudentPos",
                        findLeaderboardPosition(loggedInStudent,
                                (List<Student>) studentRepository.findAllByOrderByUsernameAsc()));
                break;

            case ("Z-A"):
                model.addAttribute("sortingOrder", "Username (Z - A)");
                model.addAttribute("students", studentRepository.findAllByOrderByUsernameDesc());
                model.addAttribute("loggedInStudentPos",
                        findLeaderboardPosition(loggedInStudent,
                                (List<Student>) studentRepository.findAllByOrderByUsernameDesc()));
                break;
        }
        return "leaderboard/leaderboard";
    }

    // post mapping to change the sort by param based on the button pressed
    @PostMapping("/leaderboard")
    public String redirectToSort(@RequestParam("sortBy") String sortBy) {
        return "redirect:/leaderboard?sortBy=" + sortBy;
    }

    private int findLeaderboardPosition(Student student, List<Student> listToSearch) {
        int position = 0;
        for (int i = 0; i < listToSearch.size(); ++i) {
            // find the index (position) of the student in the list
            if (listToSearch.get(i) == student) {
                position = i;
                break;
            }
        }
        return position + 1; // position + 1 as index starts at 0
    }
}
