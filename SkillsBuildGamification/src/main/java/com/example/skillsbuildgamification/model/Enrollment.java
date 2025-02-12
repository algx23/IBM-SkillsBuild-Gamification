package com.example.skillsbuildgamification.model;

import jakarta.persistence.*;

@Entity
public class Enrollment {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "studentID", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "courseID", nullable = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    // No-arg constructor required by JPA
    public Enrollment() {}

    // Parameterized constructor
    public Enrollment(Student student, Course course, EnrollmentStatus status) {
        this.student = student;
        this.course = course;
        this.status = status;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }
}


