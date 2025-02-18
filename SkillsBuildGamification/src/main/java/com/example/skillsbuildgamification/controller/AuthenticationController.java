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
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthenticationController {

    @RequestMapping("/")
    public String index(Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            return "redirect:/" + username + "/dashboard-page";
        } else {
            return "redirect:/login-page";
        }
    }

    // Handles requests to login-related endpoints

    @GetMapping("/login-page")
    public String login(Model model, @RequestParam("failure") Optional<Boolean> failure) {
        // The login page
        UserDetails user = emptyUser();
        model.addAttribute("user", user);
        // If failure is true, it means that the user has entered some incorrect login details
        if (failure.isPresent()) {
            model.addAttribute("failure", failure.get());
        }
        else {
            model.addAttribute("failure", false);
        }
        return "authentication/login";
    }

    @RequestMapping("/login-process")
    public void loginProcess(Model model) {
        // Called when a login is being processed
        System.out.println("Processing login");
    }

    @RequestMapping("/login-failure")
    public String loginFailure(Model model) {
        return "redirect:/login-page?failure=true";
    }

    @RequestMapping("/login-success")
    public String loginSuccess(Principal principal, Model model) {
        if (principal != null) {
            String username = principal.getName();
            return "redirect:" + username + "/dashboard-page";
        }
        // If the user is not logged in, redirect them to the login page
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
