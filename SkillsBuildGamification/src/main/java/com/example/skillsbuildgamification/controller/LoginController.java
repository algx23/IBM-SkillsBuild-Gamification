package com.example.skillsbuildgamification.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    // Handles requests to login-related endpoints

    @RequestMapping("/")
    public String login(Model model) {
        return "login";
    }
}
