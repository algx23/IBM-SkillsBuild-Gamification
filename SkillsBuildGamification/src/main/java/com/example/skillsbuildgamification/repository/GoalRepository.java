package com.example.skillsbuildgamification.repository;

import com.example.skillsbuildgamification.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Integer>
{
    // Fetch only goals that have not expired
    @Query("SELECT g FROM Goal g WHERE g.student.username = :username AND g.completionDate >= CURRENT_DATE")
    List<Goal> findActiveGoalsByUsername(String username);
}
