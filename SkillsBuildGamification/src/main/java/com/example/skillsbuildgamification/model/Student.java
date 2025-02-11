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
    private int points;
    private int level;

    private int leaderboardPosition;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<>();

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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {this.level = level;}


    public int getLeaderboardPosition() {return leaderboardPosition;}

    public void setLeaderboardPosition(int leaderboardPosition) {this.leaderboardPosition = leaderboardPosition;}

    public List<Enrollment> getEnrollments() {return enrollments;}

    public void setEnrollments(List<Enrollment> enrollments) {this.enrollments = enrollments;}
}
