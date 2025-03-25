package com.example.skillsbuildgamification.service;

import com.example.skillsbuildgamification.model.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AvatarService {

    private static final String DICEBEAR_API_URL = "https://api.dicebear.com/8.x/";

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
        String baseUrl = DICEBEAR_API_URL + student.getActiveCategory() + "/svg?seed=" + student.getUsername();

        List<String> equippedItems = student.getEquippedItems();
        Map<String, Map<String, Object>> categoryFeatures = avatarData.get(student.getActiveCategory());

        for (String item : equippedItems) {
            String[] parts = item.split("=");
            if (parts.length == 3) {
                String featureName = parts[0];
                String featureType = parts[1];
                String variant = parts[2];

                // Only append features that exist in the current category
                if (categoryFeatures.containsKey(featureName)) {
                    baseUrl += "&" + featureType+ "=" + variant;
                }
            }
        }
        return baseUrl;
    }


    public List<String> getDefaultItemsForCategory(String category) {
        List<String> defaults = new ArrayList<>();

        if (avatarData.containsKey(category)) {
            Map<String, Map<String, Object>> features = avatarData.get(category);
            for (Map.Entry<String, Map<String, Object>> entry : features.entrySet()) {
                String feature = entry.getKey();
                Map<String, Object> featureData = entry.getValue();
                List<String> variants = (List<String>) featureData.get("variants");
                if (!variants.isEmpty()) {
                    // Format: category_feature_variant
                    defaults.add(category + "_" + feature + "_" + variants.get(0));
                }
            }
        }

        return defaults;
    }

}
