package com.example.skillsbuildgamification.service;

import org.springframework.stereotype.Service;

@Service
public class AvatarService {

    private static final String BASE_AVATAR_URL = "";

    /**
     * Generate a default avatar
     */
    public String generateDefaultAvatar(String username) {
        return BASE_AVATAR_URL + username;
    }

    /**
     * Update avatar based on equipped items
     */
    public String updateAvatarWithItems(String baseAvatarUrl, String equippedItem) {
        switch (equippedItem) {
            case "Hat":
                return baseAvatarUrl + "&topType=WinterHat1";
            case "Glasses":
                return baseAvatarUrl + "&accessoriesType=Prescription02";
            case "Shirt":
                return baseAvatarUrl + "&clotheType=BlazerShirt";
            default:
                return baseAvatarUrl;
        }
    }
}
