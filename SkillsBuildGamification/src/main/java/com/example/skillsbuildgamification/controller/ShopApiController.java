package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.ShopItem;

import com.example.skillsbuildgamification.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController  // Change to RestController for API responses
@RequestMapping("/api/shop")
public class ShopApiController {

    @Autowired
    private ShopService shopService;

    /**
     * Get all shop items as JSON
     */
    @GetMapping("/items")
    public List<ShopItem> getShopItems() {
        return shopService.getAllItems();
    }


}
