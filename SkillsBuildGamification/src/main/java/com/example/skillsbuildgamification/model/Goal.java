package com.example.skillsbuildgamification.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
public class Goal
{
    @Id
    @GeneratedValue
    private int goalID;

    private int numberOfGoals;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date completionDate;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public int getNumberOfGoals() {
        return numberOfGoals;
    }

    public void setNumberOfGoals(int numberOfGoals) {
        this.numberOfGoals = numberOfGoals;
    }

    public int getGoalID() {
        return goalID;
    }

    public void setGoalID(int goalID) {
        this.goalID = goalID;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
