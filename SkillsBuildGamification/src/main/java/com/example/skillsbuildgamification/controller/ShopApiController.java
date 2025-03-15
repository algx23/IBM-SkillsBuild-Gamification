package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.ShopItem;

import com.example.skillsbuildgamification.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/shop")
public class ShopApiController {

    @Autowired
    private ShopService shopService;

    @GetMapping("/items")
    public List<ShopItem> getShopItems() {
        // Fetching items from your database or pre-defined data
        List<ShopItem> items = shopService.getAllItems();

        // Add Dicebear URL dynamically for avatars
        for (ShopItem item : items) {
            // Assume that the item has an avatar or item-specific image URL
            String dicebearAvatarUrl = " " + item.getName() + ".svg";
            item.setApiParam(dicebearAvatarUrl); // Setting Dicebear image URL for shop item
        }

        return items;
    }
}
