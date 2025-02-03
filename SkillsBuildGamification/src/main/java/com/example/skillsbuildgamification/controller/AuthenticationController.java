package com.example.skillsbuildgamification.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthenticationController {
    // Handles requests to login-related endpoints

    @RequestMapping("/login-page")
    public String login(Model model) {
        return "login";
    }
}
