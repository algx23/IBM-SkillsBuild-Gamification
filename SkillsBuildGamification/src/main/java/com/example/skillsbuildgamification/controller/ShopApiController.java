package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shop")
public class ShopApiController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private StudentRepository studentRepository;




    /**
     * Equip an owned item
     */
    @PostMapping("/equip")
    public ResponseEntity<String> equipItem(@RequestBody Map<String, String> requestData, Principal principal) {
        String username = principal.getName();
        String category = requestData.get("category");
        String featureType = requestData.get("featureType");
        String variant = requestData.get("variant");

        boolean success = shopService.equipItem(username, category, featureType, variant);
        return success ? ResponseEntity.ok("Item equipped successfully") :
                ResponseEntity.badRequest().body("Item not owned or invalid");
    }


    /**
     * Purchase an item if enough points
     */
    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseItem(
            @RequestBody Map<String, String> requestData,
            Principal principal) {

        String username = principal.getName();
        String category = requestData.get("category");
        String featureType = requestData.get("featureType");
        String variant = requestData.get("variant");

        try {
            boolean success = shopService.purchaseItem(username, category, featureType, variant);
            return ResponseEntity.ok("Item purchased successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Send exact error message
        }
    }

    /**
     * Fetch owned items for UI synchronization
     */
    @GetMapping("/owned")
    public ResponseEntity<Map<String, List<String>>> getOwnedItems(Principal principal) {
        return ResponseEntity.ok(shopService.getOwnedItems(principal.getName()));
    }

    @GetMapping("/avatar")
    public ResponseEntity<String> getStudentAvatar(Principal principal) {
        Student student = studentRepository.findByUsername(principal.getName());

        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(student.getAvatarUrl());  // ✅ Return avatar URL
    }


    /**
     * Fetch avatar variants dynamically from JSON
     */
    @GetMapping("/avatar/variants")
    public ResponseEntity<Map<String, Map<String, Map<String, Object>>>> getAvatarVariants() {
        return ResponseEntity.ok(shopService.getAvatarVariants());
    }

}
