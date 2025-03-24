package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.service.AvatarService;
import com.example.skillsbuildgamification.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class ShopController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private AvatarService avatarService;

    /**
     * Load Shop Page with avatar and shop items
     */
    @GetMapping("/shop")
    public String shopPage(Model model, Principal principal) {
        Student student = studentRepository.findByUsername(principal.getName());

        if (student == null) {
            return "redirect:/login"; // Redirect if student not found
        }

        // Get the latest avatar
        String avatarUrl = avatarService.generateAvatar(student);
        student.setAvatarUrl(avatarUrl);
        studentRepository.save(student);

        // Fetch items from JSON
        Map<String, Map<String, Map<String, Object>>> shopItems = shopService.getAvatarVariants();
        Map<String, List<String>> inventory = shopService.getOwnedItems(student.getUsername());

        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("student", student);
        model.addAttribute("shopItems", shopItems); // JSON-based items instead of ShopItem
        model.addAttribute("ownedItems", inventory.get("ownedItems"));
        model.addAttribute("equippedItems", inventory.get("equippedItems"));

        return "shop/shop";
    }

}
