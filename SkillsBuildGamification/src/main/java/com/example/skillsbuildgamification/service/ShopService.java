package com.example.skillsbuildgamification.service;

import com.example.skillsbuildgamification.model.ShopItem;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.ShopItemRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.repository.UserInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ShopService {

    @Autowired
    private ShopItemRepository shopItemRepository;

    @Autowired
    private UserInventoryRepository userInventoryRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AvatarService avatarService;

    /**
     * Get all available shop items
     */
    public List<ShopItem> getAvailableItems() {
        return shopItemRepository.findAll();
    }

    /**
     * Handle item purchase
     */
    public boolean purchaseItem(Student student, ShopItem item) {
        if (item == null || student.getPoints() < item.getCost() || student.getLevel() < item.getRequiredLevel()) {
            return false;
        }
        student.setPoints(student.getPoints() - item.getCost());
        student.getOwnedItems().add(item.getName());

        studentRepository.save(student);
        return true;
    }

    /**
     * Equip an item and update the avatar
     */
    public boolean equipItem(Student student, ShopItem item) {
        if (item == null || !student.getOwnedItems().contains(item.getName())) {
            return false;
        }

        // Remove previously equipped items and equip the new one
        student.getEquippedItems().clear();
        student.getEquippedItems().add(item.getName());
        student.setAvatarUrl(avatarService.updateAvatarWithItems(student.getAvatarUrl(), item.getName()));

        studentRepository.save(student);
        return true;
    }
}
