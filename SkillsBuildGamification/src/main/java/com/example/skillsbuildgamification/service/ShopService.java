package com.example.skillsbuildgamification.service;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.utils.JsonLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ShopService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private JsonLoader jsonLoader; // Load JSON-based avatar shop
    private static final String JSON_PATH = "src/main/resources/avatar_variants.json";  // Ensure correct path
    /**
     * Fetch avatar variants from JSON.
     */
    public Map<String, Map<String, Map<String, Object>>> getAvatarVariants() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File(JSON_PATH), Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load avatar variants", e);
        }
    }

    /**
     * Purchase an avatar feature if the student has enough points.
     */
    public boolean purchaseItem(String username, String category, String featureType, String variant) {
        Student student = studentRepository.findByUsername(username);

        if (student == null) {
            return false; // Fail if student does not exist
        }

        String itemKey = category + "_" + featureType + "_" + variant;

        // Check if student already owns the item
        if (student.getOwnedItems().contains(itemKey)) {
            throw new RuntimeException("Item is already owned.");
        }

        // Get item cost from JSON
        int itemCost = avatarService.getItemCost(category, featureType);
        if (student.getCredits() < itemCost) {
            throw new RuntimeException("Insufficient credits."); // Return clear failure reason
        }

        // Deduct credits
        int updated_credits = student.getCredits() - itemCost;
        student.setCredits(updated_credits);

        // Add item to owned list
        student.getOwnedItems().add(itemKey);
        studentRepository.save(student); // Save updated student

        return true;
    }

    /**
     * Equip a purchased item.
     */
    public boolean equipItem(String username, String category, String featureType, String variant) {
        Student student = studentRepository.findByUsername(username);
        if (student == null) {
            return false;
        }

        // Correct the format of equipped item
        String diceBearParam = featureType + "=" + variant;

        // Remove old equipped item of same feature type before adding new one
        student.getEquippedItems().removeIf(equipped -> equipped.startsWith(featureType + "="));
        student.getEquippedItems().add(diceBearParam);

        // Generate new avatar URL
        String newAvatar = avatarService.generateAvatar(student);
        student.setAvatarUrl(newAvatar);  // 🔥 **Update avatar URL**

        // **Ensure the avatar URL is correctly saved in DB**
        studentRepository.save(student);

        return true;
    }



    /**
     * Get a student's owned items.
     */
    public Map<String, List<String>> getOwnedItems(String username) {
        Student student = studentRepository.findByUsername(username);

        Map<String, List<String>> inventory = new HashMap<>();
        inventory.put("ownedItems", new ArrayList<>(student.getOwnedItems()));
        inventory.put("equippedItems", new ArrayList<>(student.getEquippedItems()));

        return inventory;
    }


}
