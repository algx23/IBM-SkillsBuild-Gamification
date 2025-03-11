package com.example.skillsbuildgamification.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue
    private Integer courseID;

    private String courseName;
    private String courseLink;

    //One course can have many enrollments
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<>();

    // Getters and setters
    public String getCourseLink() {
        return courseLink;
    }

    public void setCourseLink(String courseLink) {
        this.courseLink = courseLink;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    private int getNumStudentsWithStatus(EnrollmentStatus status) {
        // Returns the number of students who are in a certain stage of completing a course
        int count = 0;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStatus() == status) {
                count++;
            }
        }
        return count;
    }

    public int getNumStudentsInProgress() {
        return getNumStudentsWithStatus(EnrollmentStatus.IN_PROGRESS);
    }

    public int getNumStudentsCompleted() {
        return getNumStudentsWithStatus(EnrollmentStatus.COMPLETED);
    }
}

