package com.example.skillsbuildgamification.service;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.model.UserInventory;
import com.example.skillsbuildgamification.repository.UserInventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class AvatarService {

    private static final String DICEBEAR_API_URL = "https://api.dicebear.com/8.x/";

    @Autowired
    private UserInventoryRepository userInventoryRepository;

    private Map<String, Map<String, Map<String, Object>>> avatarData;

    @PostConstruct
    private void loadAvatarData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        avatarData = objectMapper.readValue(new ClassPathResource("avatar_variants.json").getFile(), Map.class);
    }

    // Fetch cost from JSON
    public int getItemCost(String category, String featureType) {
        if (avatarData.containsKey(category) && avatarData.get(category).containsKey(featureType)) {
            return (int) avatarData.get(category).get(featureType).get("cost");
        }
        return Integer.MAX_VALUE; // Default high value if not found
    }
    /**
     * Generate Avatar URL based on student's equipped items
     */
    public String generateAvatar(Student student) {
        // Start with base DiceBear API URL using the correct category
        String baseUrl = DICEBEAR_API_URL + student.getActiveCategory() + "/svg?seed=" + student.getUsername();

        List<String> equippedItems = student.getEquippedItems();

        // Ensure valid query parameters
        for (String item : equippedItems) {
            String[] parts = item.split("=");

            if (parts.length == 3) {
                String featureType = parts[1]; // Extract feature type
                String variant = parts[2]; // Extract the actual variant
                baseUrl += "&" + featureType + "=" + variant; // Append in correct DiceBear format
            }
        }

        return baseUrl; // Final URL is correctly formatted
    }

}
