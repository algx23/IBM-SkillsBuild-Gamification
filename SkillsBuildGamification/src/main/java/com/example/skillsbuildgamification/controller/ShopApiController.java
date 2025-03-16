package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.ShopItem;

import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shop")
public class ShopApiController {

    @Autowired
    private ShopService shopService;

    @GetMapping("/items")
    public List<ShopItem> getShopItems() {
        return shopService.getAllItems();  // Return a list of shop items
    }

    @GetMapping("/avatar/variants")
    public Map<String, Map<String, List<String>>> getAvatarVariants(@RequestParam String username) {
        System.out.println("Generating categorized avatar variants for: " + username);

        // Supported avatar categories
        List<String> categories = List.of("bottts", "adventurer", "avataaars", "big-ears", "pixel-art");

        // Feature categories with variants for each avatar type (based on JSON schemas)
        Map<String, Map<String, List<String>>> featureVariants = Map.of(
                "bottts", Map.of(
                        "Eyes", List.of("eyes=bulging", "eyes=glow", "eyes=hearts"),
                        "Top", List.of("top=horns", "top=antenna"),
                        "Texture", List.of("texture=camo01", "texture=grunge01")
                ),
                "adventurer", Map.of(
                        "Eyes", List.of("eyes=variant05", "eyes=variant10"),
                        "Hair", List.of("hair=short10", "hair=long05"),
                        "Glasses", List.of("glasses=variant02", "glasses=variant05"),
                        "Mouth", List.of("mouth=variant07", "mouth=variant12")
                ),
                "avataaars", Map.of(
                        "Eyes", List.of("eyes=happy", "eyes=wink"),
                        "Clothing", List.of("clothing=hoodie", "clothing=shirtCrewNeck"),
                        "Accessories", List.of("accessories=wayfarers", "accessories=eyepatch"),
                        "Facial Hair", List.of("facialHair=beardMedium", "facialHair=moustacheFancy")
                ),
                "big-ears", Map.of(
                        "Face", List.of("face=variant04", "face=variant08"),
                        "Ears", List.of("ear=variant03", "ear=variant06"),
                        "Cheek", List.of("cheek=variant01", "cheek=variant04"),
                        "Front Hair", List.of("frontHair=variant06", "frontHair=variant10")
                ),
                "pixel-art", Map.of(
                        "Glasses", List.of("glasses=dark05", "glasses=light02"),
                        "Hat", List.of("hat=variant03", "hat=variant07"),
                        "Clothing", List.of("clothing=variant10", "clothing=variant15"),
                        "Mouth", List.of("mouth=happy06", "mouth=sad04")
                )
        );

        Map<String, Map<String, List<String>>> avatarVariants = new HashMap<>();

        // Generate dynamic categorized variants for each avatar type
        for (String category : categories) {
            Map<String, List<String>> categoryVariants = new HashMap<>();

            for (Map.Entry<String, List<String>> entry : featureVariants.get(category).entrySet()) {
                String featureCategory = entry.getKey();
                List<String> variantUrls = entry.getValue().stream()
                        .map(variant -> "https://api.dicebear.com/8.x/" + category + "/svg?seed=" + username + "&" + variant)
                        .collect(Collectors.toList());

                categoryVariants.put(featureCategory, variantUrls);
            }

            avatarVariants.put(category, categoryVariants);
        }

        return avatarVariants;
    }




}
