package com.example.skillsbuildgamification.service;

import com.example.skillsbuildgamification.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AvatarService {

    private static final String DICEBEAR_API_URL = "https://avatars.dicebear.com/api/avataaars/";

    public String generateAvatar(Student student) {
        // Assume we get a simple avatar string based on the student's owned items.
        String avatarId = String.join("_", student.getOwnedItems());  // Concatenate owned items as avatar seed
        String url = DICEBEAR_API_URL + avatarId + ".svg";  // Generate avatar with the items' seed
        return url;
    }

    public String updateAvatarWithItems(String currentAvatarUrl, List<String> items) {
        String avatarId = String.join("_", items);  // Concatenate item names as avatar seed
        return DICEBEAR_API_URL + avatarId + ".svg";  // Return the new avatar URL
    }
}
