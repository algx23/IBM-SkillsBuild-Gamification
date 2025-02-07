package com.example.skillsbuildgamification.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {
    // Model class to store information related to a student

    @Id
    private String username;
    private String password;
    private int points;
    private int level;

    private String completedCourses;
    private int leaderboardPosition;

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

    public String getCompletedCourses() {return completedCourses;}

    public void setCompletedCourses(String completedCourses) {this.completedCourses = completedCourses;}

    public int getLeaderboardPosition() {return leaderboardPosition;}

    public void setLeaderboardPosition(int leaderboardPosition) {this.leaderboardPosition = leaderboardPosition;}
}
