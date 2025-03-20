package com.example.skillsbuildgamification.model;

import jakarta.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue
    private int reviewId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,optional = false)
    @JoinColumn(nullable = false)
    private Course courseAssociated;
    @ManyToOne(cascade = CascadeType.ALL,optional = false)
    @JoinColumn(nullable = false)
    private Student studentId;
    @Column(nullable = false)
    private String review;
    @Column(nullable = false)
    private Integer rating;

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
