package com.example.skillsbuildgamification.repository;

import com.example.skillsbuildgamification.model.Review;

public interface ReviewRepository {
    Review findReviewByUserId(String userId); // find the review by the profile Id that had created it
}
