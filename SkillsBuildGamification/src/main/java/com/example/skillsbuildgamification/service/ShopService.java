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

    public List<ShopItem> getAvailableItems() {
        return shopItemRepository.findAll();
    }

    public boolean purchaseItem(Student student, ShopItem item) {
        if (item == null || student.getPoints() < item.getCost() || student.getLevel() < item.getRequiredLevel()) {
            return false;
        }
        student.setPoints(student.getPoints() - item.getCost());
        student.getOwnedItems().add(item.getName());

        studentRepository.save(student);
        return true;
    }
}
