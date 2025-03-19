package com.example.skillsbuildgamification.repository;

import com.example.skillsbuildgamification.model.Goal;
import com.example.skillsbuildgamification.model.GoalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Integer> {
    List<Goal> findByStudentUsernameAndStatus(String username, GoalStatus status);
}


