package com.example.skillsbuildgamification.repository;

import com.example.skillsbuildgamification.model.ShopItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopItemRepository extends CrudRepository<ShopItem, Long> {
    List<ShopItem> findAll();  // Retrieves all shop items
    ShopItem findByName(String name);
    List<ShopItem> findAllByRequiredLevelLessThanEqual(int level);  // Retrieves items a student can purchase based on level
}
