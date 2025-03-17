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
        Map<String, Map<String, Map<String, Object>>> avatarData = jsonLoader.loadAvatarData();

        if (!avatarData.containsKey(category)) {
            return false; // Invalid category
        }

        Map<String, Map<String, Object>> categoryData = avatarData.get(category);
        if (!categoryData.containsKey(featureType)) {
            return false; // Invalid feature type
        }

        int cost = (int) categoryData.get(featureType).get("cost"); // Get cost dynamically per feature

        if (student.getPoints() < cost) {
            return false; // Not enough points
        }

        student.setPoints(student.getPoints() - cost);  // Deduct points
        student.getOwnedItems().add(variant); // Track purchased items
        studentRepository.save(student);
        return true;
    }

    /**
     * Equip a purchased item.
     */
    public boolean equipItem(String username, String category, String featureType, String variant) {
        Student student = studentRepository.findByUsername(username);

        if (!student.getOwnedItems().contains(variant)) {
            return false; // Cannot equip an item the user doesn't own
        }

        student.getEquippedItems().clear(); // Unequip all items before equipping a new one
        student.getEquippedItems().add(variant);
        student.setAvatarUrl(avatarService.generateAvatar(student)); // Update avatar
        studentRepository.save(student);
        return true;
    }

    /**
     * Get a student's owned items.
     */
    public List<String> getOwnedItems(String username) {
        Student student = studentRepository.findByUsername(username);
        return student.getOwnedItems();
    }
}
