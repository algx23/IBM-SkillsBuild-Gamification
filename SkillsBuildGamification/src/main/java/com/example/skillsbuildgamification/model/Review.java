package com.example.skillsbuildgamification.model;

import jakarta.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue
    private int reviewID;

    //mapped to the Course table
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,optional = false)
    @JoinColumn(nullable = false)
    private Course courseAssociated;

    //mapped to the student table
    @ManyToOne(cascade = CascadeType.ALL,optional = false)
    @JoinColumn(nullable = false)
    private Student studentId;

    //individual value
    @Column(nullable = false)
    private String reviewText;

    //individual value
    @Column(nullable = false)
    private Integer rating;

    public int getReviewId() {
        return reviewID;
    }

    public void setReviewId(int reviewID) {
        this.reviewID = reviewID;
    }

    public Course getCourseAssociated() {
        return courseAssociated;
    }

    public void setCourseAssociated(Course courseAssociated) {
        this.courseAssociated = courseAssociated;
    }

    public Student getStudentId() {
        return studentId;
    }

    public void setStudentId(Student studentId) {
        this.studentId = studentId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String review) {
        this.reviewText = review;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
