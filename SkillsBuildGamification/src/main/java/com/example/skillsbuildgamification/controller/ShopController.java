package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.ShopItem;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.ShopItemRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.repository.UserInventoryRepository;
import com.example.skillsbuildgamification.service.ShopService;
import com.example.skillsbuildgamification.service.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopItemRepository shopItemRepository;

    @Autowired
    private UserInventoryRepository userInventoryRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private AvatarService avatarService;

    /**
     * Shop page displaying avatar on left & shop items on right
     */
    @GetMapping
    public String shopPage(Model model, Principal principal) {
        Student student = studentRepository.findByUsername(principal.getName());
        List<ShopItem> shopItems = shopItemRepository.findAll();

        model.addAttribute("student", student);
        model.addAttribute("avatarUrl", student.getAvatarUrl());
        model.addAttribute("shopItems", shopItems);
        return "shop/shop";
    }

    /**
     * Preview an item before buying.
     */
    @PostMapping("/preview/{itemId}")
    public String previewItem(@PathVariable Long itemId, Principal principal, Model model) {
        Student student = studentRepository.findByUsername(principal.getName());
        ShopItem item = shopItemRepository.findById(itemId).orElse(null);

        if (item != null) {
            String previewAvatarUrl = avatarService.updateAvatarWithItems(student.getAvatarUrl(), item.getName());
            model.addAttribute("previewAvatarUrl", previewAvatarUrl);
        }

        return "shop/shop";
    }

    /**
     * Purchase an item and add it to the inventory.
     */
    @PostMapping("/purchase/{itemId}")
    public String purchaseItem(@PathVariable Long itemId, Principal principal, Model model) {
        Student student = studentRepository.findByUsername(principal.getName());
        ShopItem item = shopItemRepository.findById(itemId).orElse(null);

        if (shopService.purchaseItem(student, item)) {
            model.addAttribute("success", "Item purchased successfully!");
        } else {
            model.addAttribute("error", "Not enough points or level too low.");
        }

        return "redirect:/shop";
    }

    /**
     * Equip an item and update the avatar.
     */
    @PostMapping("/equip/{itemId}")
    public String equipItem(@PathVariable Long itemId, Principal principal) {
        Student student = studentRepository.findByUsername(principal.getName());
        ShopItem item = shopItemRepository.findById(itemId).orElse(null);

        if (shopService.equipItem(student, item)) {
            return "redirect:/profile/" + student.getUsername();
        }

        return "redirect:/shop";
    }
}
