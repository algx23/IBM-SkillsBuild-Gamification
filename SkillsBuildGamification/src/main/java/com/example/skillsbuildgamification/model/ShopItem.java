package com.example.skillsbuildgamification.model;

import jakarta.persistence.*;

@Entity
public class ShopItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String apiParam;  // avatar api URL param
    private int cost;  // Points required
    private int requiredLevel;  // Level lock

    // Getters and Setters
    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getApiParam() {return apiParam;}

    public void setApiParam(String apiParam) {this.apiParam = apiParam;}

    public int getCost() {return cost;}

    public void setCost(int cost) {this.cost = cost;}

    public int getRequiredLevel() {return requiredLevel;}

    public void setRequiredLevel(int requiredLevel) {this.requiredLevel = requiredLevel;}

}

