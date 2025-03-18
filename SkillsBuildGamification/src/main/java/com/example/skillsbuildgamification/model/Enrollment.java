package com.example.skillsbuildgamification.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Enrollment {
    @Id
    @GeneratedValue
    private int enrollmentId;

    // Many enrollments can be associated with a single student
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private Student student;

    // Many enrollments can be associated with a single course
    @ManyToOne
    @JoinColumn(name = "courseID", nullable = false)
    private Course course;

    @Temporal(TemporalType.DATE)
    private Date completionDate;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    public Enrollment() {} // Required by JPA (no param constructor)

    //parameter constructor method
    public Enrollment(Student student, Course course, EnrollmentStatus status) {
        this.student = student;
        this.course = course;
        this.status = status;
    }

    // Getters and setters
    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
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

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }
}



