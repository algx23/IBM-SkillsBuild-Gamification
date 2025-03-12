package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.ShopItem;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.ShopItemRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.service.AvatarService;
import com.example.skillsbuildgamification.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/shop")
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
    @GetMapping
    public String shopPage(Model model, Principal principal) {
        Student student = studentRepository.findByUsername(principal.getName());
        List<ShopItem> shopItems = shopItemRepository.findAll();

        model.addAttribute("student", student);
        model.addAttribute("avatarUrl", student.getAvatarUrl());
        model.addAttribute("shopItems", shopItems);
        return "shop/shop";  // JSP page to render
    }

    /**
     * Preview an item before buying
     */
    @PostMapping("/preview/{itemId}")
    public String previewItem(@PathVariable Long itemId, Principal principal, Model model) {
        Student student = studentRepository.findByUsername(principal.getName());
        ShopItem item = shopItemRepository.findById(itemId).orElse(null);

        if (item != null) {
            String previewAvatarUrl = avatarService.updateAvatarWithItems(student.getAvatarUrl(), List.of(item.getName()));
            model.addAttribute("previewAvatarUrl", previewAvatarUrl);
        }

        return "shop/shop";  // Return to shop page with updated preview
    }

    /**
     * Equip an item and update avatar
     */
    @PostMapping("/equip/{itemId}")
    public String equipItem(@PathVariable Long itemId, Principal principal) {
        Student student = studentRepository.findByUsername(principal.getName());
        ShopItem item = shopItemRepository.findById(itemId).orElse(null);

        if (shopService.equipItem(student, item)) {
            return "redirect:/profile/" + student.getUsername();  // Redirect to profile page after equipping
        }

        return "redirect:/shop";  // Return to shop page on failure
    }
}
