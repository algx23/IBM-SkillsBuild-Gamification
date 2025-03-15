package com.example.skillsbuildgamification.model;

import com.example.skillsbuildgamification.service.AvatarService;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {
    // Model class to store information related to a student

    @Id
    private String username;
    private String password;
    private int points = 0;
    private int level = 0;
    private String bio; // Student Bio
    // Avatar URL from API replaced profile picture)
    private String avatarUrl;

    @ElementCollection
    private List<String> equippedItems = new ArrayList<>();  // Tracks worn accessories

    @ElementCollection
    private List<String> ownedItems = new ArrayList<>();  // Tracks purchased items

    private UserRank userRank = UserRank.BRONZE;
    private int numCompletedCourses;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<>();

    @ElementCollection
    private List<String> earnedBadges = new ArrayList<>(); // badges as a collection

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        this.avatarUrl = "https://api.dicebear.com/9.x/adventurer/svg?seed=${username}";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoints() {return points;}

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public UserRank getUserRank() {
        return userRank;
    }

    public void setUserRank(UserRank userRank) {
        this.userRank = userRank;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatarUrl() {return avatarUrl;}
    public void updateAvatar(AvatarService avatarService) {
        this.avatarUrl = avatarService.updateAvatarWithItems(this.avatarUrl, this.equippedItems);
    }

    public void setAvatarUrl(String avatarUrl) {this.avatarUrl = avatarUrl;}

    public List<String> getEquippedItems() {return equippedItems;}

    public void setEquippedItems(List<String> equippedItems) {this.equippedItems = equippedItems;}

    public List<String> getOwnedItems() {return ownedItems;}

    public void setOwnedItems(List<String> ownedItems) {this.ownedItems = ownedItems;}

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public void setNumCompletedCourses(int num) {
        this.numCompletedCourses = num;
    }

    public int getNumCompletedCourses() {
        return numCompletedCourses;
    }

    public List<String> getEarnedBadges() { return earnedBadges; }
    public void setEarnedBadges(List<String> earnedBadges) { this.earnedBadges = earnedBadges; }

}
