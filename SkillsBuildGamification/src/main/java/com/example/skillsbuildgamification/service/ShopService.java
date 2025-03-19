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
        student.setCredits(student.getCredits() - itemCost);

        // Add item to owned list
        student.getOwnedItems().add(itemKey);
        studentRepository.save(student); // Save updated student

        return true;
    }

    /**
     * Equip a purchased item.
     */
    /**
     * Equip a purchased item.
     */
    public boolean equipItem(String username, String category, String featureType, String variant) {
        Student student = studentRepository.findByUsername(username);
        if (student == null) {
            return false;
        }

        String itemKey = category + "_" + featureType + "_" + variant;

        if (!student.getOwnedItems().contains(itemKey)) {
            return false; // Cannot equip if not owned
        }

        // ✅ Remove previous feature-type item
        student.getEquippedItems().removeIf(equipped -> equipped.startsWith(category + "_" + featureType));

        // ✅ Equip new item
        student.getEquippedItems().add(itemKey);

        // ✅ Generate new avatar and SAVE it to the database
        String newAvatar = avatarService.generateAvatar(student);
        student.setAvatarUrl(newAvatar);

        studentRepository.save(student); // ✅ **Force Save to DB**
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
