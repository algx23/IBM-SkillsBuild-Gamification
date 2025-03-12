package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.ShopItem;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.model.UserInventory;
import com.example.skillsbuildgamification.repository.ShopItemRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.repository.UserInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping
    public String shopPage(Model model, Principal principal) {
        Student student = studentRepository.findByUsername(principal.getName());
        List<ShopItem> shopItems = shopItemRepository.findAll();

        model.addAttribute("student", student);
        model.addAttribute("shopItems", shopItems);
        return "shop/shop";
    }

    @PostMapping("/purchase/{itemId}")
    public String purchaseItem(@PathVariable Long itemId, Principal principal, RedirectAttributes redirectAttributes) {
        Student student = studentRepository.findByUsername(principal.getName());
        ShopItem item = shopItemRepository.findById(itemId).orElse(null);

        if (item == null || student.getPoints() < item.getCost() || student.getLevel() < item.getRequiredLevel()) {
            redirectAttributes.addFlashAttribute("error", "Purchase failed: Not enough points or level too low.");
            return "redirect:/shop";
        }

        student.setPoints(student.getPoints() - item.getCost());
        student.getOwnedItems().add(item.getName());

        UserInventory inventory = new UserInventory();
        inventory.setStudent(student);
        inventory.setShopItem(item);
        inventory.setEquipped(false);

        studentRepository.save(student);
        userInventoryRepository.save(inventory);

        redirectAttributes.addFlashAttribute("success", "Item purchased successfully!");
        return "redirect:/shop";
    }

    @PostMapping("/equip/{itemId}")
    public String equipItem(@PathVariable Long itemId, Principal principal, RedirectAttributes redirectAttributes) {
        Student student = studentRepository.findByUsername(principal.getName());
        UserInventory inventory = userInventoryRepository.findByStudentAndShopItemId(student, itemId);

        if (inventory == null) {
            redirectAttributes.addFlashAttribute("error", "Item not owned.");
            return "redirect:/profile/" + student.getUsername();
        }

        // Replace the old item (only one equipped per category)
        inventory.setEquipped(true);
        student.getEquippedItems().clear();
        student.getEquippedItems().add(inventory.getShopItem().getName());

        studentRepository.save(student);
        userInventoryRepository.save(inventory);

        redirectAttributes.addFlashAttribute("success", "Item equipped successfully!");
        return "redirect:/profile/" + student.getUsername();
    }
}
