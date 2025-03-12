package com.example.skillsbuildgamification.service;

import org.springframework.stereotype.Service;

@Service
public class AvatarService {

    private static final String BASE_AVATAR_URL = "https://avataaars.io/?avatarStyle=Transparent";

    public String generateDefaultAvatar(String username) {
        return "https://api.dicebear.com/7.x/open-peeps/svg?seed=" + username;
    }

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
