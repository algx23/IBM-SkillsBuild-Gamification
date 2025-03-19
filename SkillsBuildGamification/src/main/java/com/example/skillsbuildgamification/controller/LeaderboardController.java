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
import java.util.Comparator;
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

        model.addAttribute("title", "Global Leaderboard");
        model.addAttribute("urlStart", "/leaderboard");
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

    @GetMapping("/leaderboard/friends")
    public String leaderboardFriendsOnly(@RequestParam(defaultValue = "") String sortBy, Principal principal, Model model) {
        Student loggedInStudent = studentRepository.findByUsername(principal.getName());
        List<Student> studentAndFriends = loggedInStudent.getFriends();
        /*
        * if the student has at least one friend add them to the list, so that they also show in the leaderboard (and
        * not just their friends).
        *
        * Then sort the list in the LeaderboardSerivce
        *
        * Then set all the model attributes so they can be accessed and displayed in the jsp
        */
        if (!studentAndFriends.contains(loggedInStudent) && !studentAndFriends.isEmpty()) {
            studentAndFriends.add(loggedInStudent);
        }

        model.addAttribute("urlStart", "/leaderboard/friends");
        model.addAttribute("title", "Friends Leaderboard");
        model.addAttribute("loggedInStudent", loggedInStudent);
        model.addAttribute("students", leaderboardService.sortFriendsInLeaderboard(studentAndFriends, sortBy));
        model.addAttribute("leaderboardPos", leaderboardService.findAndHandleJointLBPos(studentAndFriends));
        if (sortBy.isEmpty()) {
            sortBy = "HighestScoreFirst";
        }
        model.addAttribute("sortingOrder", sortBy);

        return "leaderboard/leaderboard";
    }
    //    post mapping to change the sort by param for the FRIENDS leaderboard
    @PostMapping("/leaderboard/friends")
    public String redirectToSortFriends(@RequestParam("sortBy") String sortBy) {
        return "redirect:/leaderboard/friends?sortBy=" + sortBy;
    }
}