package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.ShopItem;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.ShopItemRepository;
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

@Controller
public class ShopController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ShopItemRepository shopItemRepository;

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
        List<ShopItem> shopItems = shopItemRepository.findAll();
        List<String> ownedItems = shopService.getOwnedItems(student.getUsername());

        // Get student's current avatar
        String avatarUrl = student.getAvatarUrl();

        // Add data to the model
        model.addAttribute("avatarUrl", avatarUrl);
        model.addAttribute("student", student);
        model.addAttribute("shopItems", shopItems);
        model.addAttribute("ownedItems", ownedItems);

        return "shop/shop";  // Render JSP page
    }
}
