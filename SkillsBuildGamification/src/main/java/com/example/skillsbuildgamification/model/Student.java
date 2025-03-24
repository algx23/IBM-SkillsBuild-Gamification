package com.example.skillsbuildgamification.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {
    // Model class to store information related to a student
    @Id
    private String username;
    private String password;
    private boolean isAdmin;

    private int points = 0;
    private int level = 0;
    private String bio; // Student Bio

    private String profilePic; // img url as string

    private UserRank userRank = UserRank.BRONZE;
    private int numCompletedCourses;

    @OneToMany(mappedBy = "studentId", cascade = CascadeType.ALL)
    private List<Review> reviewsByStudent = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<>();

    @ElementCollection
    private List<String> earnedBadges = new ArrayList<>(); // badges as a collection

    public List<Review> getReviewsByStudent() {
        return reviewsByStudent;
    }

    public void setReviewsByStudent(List<Review> reviewsByStudent) {
        this.reviewsByStudent = reviewsByStudent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public int getPoints() {
        return points;
    }

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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

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
