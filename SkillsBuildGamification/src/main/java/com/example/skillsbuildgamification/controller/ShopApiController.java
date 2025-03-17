package com.example.skillsbuildgamification.controller;

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

    /**
     * Equip an owned item
     */
    @PostMapping("/equip")
    public ResponseEntity<String> equipItem(
            @RequestParam String category,
            @RequestParam String featureType,
            @RequestParam String variant,
            Principal principal) {

        String username = principal.getName();
        boolean success = shopService.equipItem(username, category, featureType, variant);
        return success ? ResponseEntity.ok("Item equipped successfully") : ResponseEntity.badRequest().body("Item not owned");
    }

    /**
     * Purchase an item if enough points
     */
    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseItem(
            @RequestParam String category,
            @RequestParam String featureType,
            @RequestParam String variant,
            Principal principal) {

        String username = principal.getName();
        boolean success = shopService.purchaseItem(username, category, featureType, variant);
        return success ? ResponseEntity.ok("Item purchased successfully") : ResponseEntity.badRequest().body("Not enough points");
    }

    /**
     * Fetch owned items for UI synchronization
     */
    @GetMapping("/owned")
    public ResponseEntity<List<String>> getOwnedItems(Principal principal) {
        List<String> ownedItems = shopService.getOwnedItems(principal.getName());
        return ResponseEntity.ok(ownedItems);
    }

    /**
     * Fetch avatar variants dynamically from JSON
     */
    @GetMapping("/avatar/variants")
    public ResponseEntity<Map<String, Map<String, Map<String, Object>>>> getAvatarVariants() {
        return ResponseEntity.ok(shopService.getAvatarVariants());
    }

}
