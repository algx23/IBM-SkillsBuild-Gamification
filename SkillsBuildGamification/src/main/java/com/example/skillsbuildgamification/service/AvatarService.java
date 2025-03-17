package com.example.skillsbuildgamification.service;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.model.UserInventory;
import com.example.skillsbuildgamification.repository.UserInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvatarService {

    private static final String DICEBEAR_API_URL = "https://api.dicebear.com/8.x/";

    @Autowired
    private UserInventoryRepository userInventoryRepository;

    /**
     * Generate Avatar URL based on student's equipped items
     */
    public String generateAvatar(Student student) {
        String baseUrl = DICEBEAR_API_URL + "bottts/svg?seed=" + student.getUsername();

        List<String> equippedItems = userInventoryRepository.findByStudent(student).stream()
                .filter(UserInventory::isEquipped)
                .map(i -> i.getShopItem().getApiParam())
                .toList();

        for (String item : equippedItems) {
            baseUrl += "&" + item;
        }

        return baseUrl;
    }
}
