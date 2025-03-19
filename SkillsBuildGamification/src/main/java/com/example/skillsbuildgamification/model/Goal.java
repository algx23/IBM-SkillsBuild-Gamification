package com.example.skillsbuildgamification.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
public class Goal {
    @Id
    @GeneratedValue
    private int goalID;

    private int numberOfGoals;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date completionDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Enumerated(EnumType.STRING)
    private GoalStatus status;

    @PrePersist
    protected void onCreate() {
        if (this.creationDate == null) {
            this.creationDate = new Date();
        }
    }

    @PreUpdate
    public void updateStatus() {
        if (status == GoalStatus.COMPLETED) {
            return;
        }

        Date today = new Date();
        if (completionDate.before(today)) {
            status = GoalStatus.EXPIRED;
        } else {
            status = GoalStatus.ACTIVE;
        }
    }

    // Getters and setters...
    public GoalStatus getStatus() {
        return status;
    }

    public void setStatus(GoalStatus status) {
        this.status = status;
    }

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

    public Date getCreationDate() {
        return creationDate;
    }
}

