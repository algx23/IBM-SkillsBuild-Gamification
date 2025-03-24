package com.example.skillsbuildgamification.repository;

import com.example.skillsbuildgamification.model.Course;
import com.example.skillsbuildgamification.model.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    ArrayList<Review> findAllByCourseAssociated(Course course); // find the review by the course
}
