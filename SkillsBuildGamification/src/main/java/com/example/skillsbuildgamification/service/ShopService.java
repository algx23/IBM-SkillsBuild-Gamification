package com.example.skillsbuildgamification.service;

import com.example.skillsbuildgamification.model.ShopItem;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.ShopItemRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    @Autowired
    private ShopItemRepository shopItemRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AvatarService avatarService;

    public List<ShopItem> getAllItems() {
        return shopItemRepository.findAll();  // Fetch all shop items from the repository
    }

    public boolean purchaseItem(Student student, ShopItem item) {
        if (item == null || student.getPoints() < item.getCost() || student.getLevel() < item.getRequiredLevel()) {
            return false;  // Cannot purchase if insufficient points or level
        }
        student.setPoints(student.getPoints() - item.getCost());  // Deduct points
        student.getOwnedItems().add(item.getName());  // Add item to owned items
        studentRepository.save(student);
        return true;
    }

    public boolean equipItem(Student student, ShopItem item) {
        if (item == null || !student.getOwnedItems().contains(item.getName())) {
            return false; // Cannot equip if item not owned
        }

        // Get new avatar URL with selected item
        String newAvatarUrl = avatarService.updateAvatarWithItems(item.getApiParam(), student.getEquippedItems());

        // Update avatar & category
        student.updateAvatar(avatarService, newAvatarUrl);
        studentRepository.save(student);

        return true;
    }

}
