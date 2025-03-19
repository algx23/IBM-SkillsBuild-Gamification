package com.example.skillsbuildgamification.service;

import com.example.skillsbuildgamification.model.*;
import com.example.skillsbuildgamification.repository.EnrollmentRepository;
import com.example.skillsbuildgamification.repository.GoalRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GoalService {
    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public void saveGoal(Goal goal, String username) {
        Student student = studentRepository.findByUsername(username);
        if (student == null) {
            throw new RuntimeException("Student not found for username: " + username);
        }

        goal.setStudent(student);
        goal.setStatus(GoalStatus.ACTIVE);
        goal.setCreationDate(new Date());
        goalRepository.save(goal);
    }

    public int getGoalProgress(Goal goal) {
        if (goal.getCreationDate() == null) {
            goal.setCreationDate(new Date());
            goalRepository.save(goal);
        }

        List<Enrollment> completedEnrollments = enrollmentRepository.findByStudentAndStatusAndCompletionDateBetween(
                goal.getStudent(), EnrollmentStatus.COMPLETED, goal.getCreationDate(), goal.getCompletionDate());
        double progress = ((double) completedEnrollments.size() / goal.getNumberOfGoals()) * 100;

        if (completedEnrollments.size() >= goal.getNumberOfGoals()) {
            goal.setStatus(GoalStatus.COMPLETED);
            goalRepository.save(goal);
        }

        return (int) progress;
    }

    public void updateGoalStatuses() {
        List<Goal> allGoals = goalRepository.findAll();
        Date today = new Date();

        for (Goal goal : allGoals) {
            if (goal.getStatus() == GoalStatus.COMPLETED) {
                continue;
            }

            if (goal.getCompletionDate().before(today)) {
                goal.setStatus(GoalStatus.EXPIRED);
            } else {
                goal.setStatus(GoalStatus.ACTIVE);
            }
        }
        goalRepository.saveAll(allGoals);
    }

    public List<Goal> getActiveGoals(String username) {
        return goalRepository.findByStudentUsernameAndStatus(username, GoalStatus.ACTIVE);
    }

    public boolean checkAndCompleteGoal(Goal goal) {
        List<Enrollment> completedEnrollments = enrollmentRepository.findByStudentAndStatusAndCompletionDateBetween(
                goal.getStudent(), EnrollmentStatus.COMPLETED, goal.getCreationDate(), goal.getCompletionDate());

        if (completedEnrollments.size() >= goal.getNumberOfGoals()) {
            goal.setStatus(GoalStatus.COMPLETED);
            goalRepository.save(goal);
            return true;
        }
        return false;
    }
}




