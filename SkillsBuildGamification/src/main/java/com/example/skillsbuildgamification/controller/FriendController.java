package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.FriendRequest;
import com.example.skillsbuildgamification.model.FriendRequestStatus;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.FriendRequestRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FriendController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private FriendService friendService;

    @GetMapping("/profile/{username}/friends")
    public String showFriends(@PathVariable String username, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login-page";
        }
        Student loggedInStudent = studentRepository.findByUsername(username);
        model.addAttribute("friends", loggedInStudent.getFriends());
        model.addAttribute("username", username);
        return "profile/friends";
    }

    @GetMapping("/profile/{username}/friend-requests")
    public String showRequests(@PathVariable String username, Model model, Principal principal) {
        // if you aren't logged in, ask them to log in
        if (principal == null) {
            return "redirect:/login-page";
        }
        // students can only see their own requests, if they try to access another
        // student's requests, deny access
        if (!username.equals(principal.getName())) {
            return "redirect:/denied";
        };

        Student loggedInStudent = studentRepository.findByUsername(username);
        List<FriendRequest> pendingRequests = new ArrayList<>();

        /*
         * go through the list of friend requests of the logged-in user
         * if the user is the receiver of the request, AND the request is pending (i.e
         * they haven't responded to it)
         * add the request to a list of requests to display
         */
        for (FriendRequest friendRequest : loggedInStudent.getRequests()) {
            if (friendRequest.getStatus().equals(FriendRequestStatus.PENDING)
                    && friendRequest.getReceiver().equals(loggedInStudent)) {
                pendingRequests.add(friendRequest);
            }
        }
        model.addAttribute("requests", pendingRequests);
        model.addAttribute("username", username);
        return "profile/requests";
    }

    @PostMapping("/sendRequest/{username}")
    public String sendFriendRequest(@PathVariable String username, Principal principal) {

//        the sender of the request is the logged in student
        Student loggedInStudent = studentRepository.findByUsername(principal.getName());
        Student friendRequestReceiver = studentRepository.findByUsername(username);
        friendService.sendRequest(loggedInStudent, friendRequestReceiver);

        return "redirect:/" + loggedInStudent.getUsername() + "/dashboard-page";
    }

    @PostMapping("friendRequest/accept/{id}")
    public String acceptFriendRequest(@PathVariable int id, Principal principal) {
        FriendRequest request = friendRequestRepository.findById(id);
        friendService.acceptRequest(request);
        return "redirect:/" + principal.getName() + "/dashboard-page";
    }

    @PostMapping("friendRequest/decline/{id}")
    public String declineFriendRequest(@PathVariable int id, Principal principal) {
        FriendRequest request = friendRequestRepository.findById(id);
        friendService.declineRequest(request);
        return "redirect:/" + principal.getName() + "/dashboard-page";
    }

//    Unfriending users
    @PostMapping("/profile/{username}/unfriend/{studentToUnfriend}")
    public String unfriendStudent(@PathVariable String username, @PathVariable String studentToUnfriend) {
        Student loggedInStudent = studentRepository.findByUsername(username);
        Student getUnfriended = studentRepository.findByUsername(studentToUnfriend);
        friendService.unfriend(loggedInStudent, getUnfriended);
        return "redirect:/profile/" + username + "/friends";
    }
}
