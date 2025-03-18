package com.example.skillsbuildgamification.service;

import com.example.skillsbuildgamification.model.Enrollment;
import com.example.skillsbuildgamification.model.EnrollmentStatus;
import com.example.skillsbuildgamification.model.Goal;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.EnrollmentRepository;
import com.example.skillsbuildgamification.repository.GoalRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        goalRepository.save(goal);
    }

    //method to get a users' progress towards active goals
    public int getGoalProgress(Goal goal) {
        List<Enrollment> completedEnrollments = enrollmentRepository.findByStudentAndStatusAndCompletionDateBefore(
                goal.getStudent(), EnrollmentStatus.COMPLETED, goal.getCompletionDate());

        return (int) ((double) completedEnrollments.size() / goal.getNumberOfGoals() * 100);
    }
}


