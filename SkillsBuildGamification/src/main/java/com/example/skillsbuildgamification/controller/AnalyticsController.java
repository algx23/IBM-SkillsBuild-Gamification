package com.example.skillsbuildgamification.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AnalyticsController {
    // Handles all analytics-related endpoints

    @RequestMapping("/analytics")
    public String analyticsPage(Model model) {
        // Show the analytics page
        return "admin/analytics";
    }

}

