package com.example.skillsbuildgamification.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
public class AuthenticationController {
    // Handles requests to login-related endpoints

    @GetMapping("/login-page")
    public String login(Model model) {
        // The login page
        UserDetails user = emptyUser();
        model.addAttribute("user", user);
        return "authentication/login";
    }

    @RequestMapping("/login-process")
    public void loginProcess(Model model) {
        // Called when a login is being processed
        System.out.println("Processing login");
    }

    @RequestMapping("/login-failure")
    public String loginFailure(Model model) {
        return "redirect:/login-page";
    }

    @RequestMapping("/login-success")
    public String loginSuccess(Principal principal) {
        if (principal != null) {
            return "dashboard";
        }
        System.out.println("principal doesn't exist");
        return "redirect:/login-page";
    }

    @RequestMapping("/test")
    public String test(Principal principal) {
        return "dashboard";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String customLogout(HttpServletRequest request, HttpServletResponse response) {
        // Method to log the current user out
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        return "redirect:/login-page";
    }

    @RequestMapping("/denied")
    public String denied(Model model) {
        return "authentication/denied";
    }

    private UserDetails emptyUser() {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities()
            {
                return List.of();
            }
            @Override
            public String getPassword() {
                return "";
            }

            @Override
            public String getUsername() {
                return "";
            }
        };
    }
}
