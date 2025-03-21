package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.config.PasswordEncoder;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.service.AvatarService;
import com.example.skillsbuildgamification.service.ShopService;
import com.example.skillsbuildgamification.validator.StudentValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RegistrationController {
    // Handles requests to registration-related endpoints

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private StudentRepository repo;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new StudentValidator(repo));
    }

    @RequestMapping("/registration-page")
    public String register(Model model) {
        // The registration page
        model.addAttribute("student", new Student());
        return "authentication/register";
    }

    @RequestMapping("/registration-process")
    public String addAgent(@Valid @ModelAttribute Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "authentication/register";
        }
        student.setPassword(passwordEncoder.encoder().encode(student.getPassword()));
        // Set default avatar features
        String defaultCategory = "bottts";
        student.setActiveCategory(defaultCategory);

        // Fetch default items from AvatarService
        List<String> defaultItems = avatarService.getDefaultItemsForCategory(defaultCategory);
        student.setOwnedItems(defaultItems);

        // Format equipped items (feature=variant)
        List<String> equipped = new ArrayList<>();
        for (String item : defaultItems) {
            String[] parts = item.split("_"); // format: category_feature_variant
            if (parts.length == 3) {
                equipped.add(parts[1] + "=" + parts[2]); // feature=variant
            }
        }
        student.setEquippedItems(equipped);

        // Generate avatar URL
        String avatarUrl = avatarService.generateAvatar(student);
        student.setAvatarUrl(avatarUrl);
        repo.save(student);
        return "redirect:/login-page";
    }
}
