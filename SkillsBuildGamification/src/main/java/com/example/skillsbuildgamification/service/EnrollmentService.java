package com.example.skillsbuildgamification.service;

import com.example.skillsbuildgamification.model.Course;
import com.example.skillsbuildgamification.model.Enrollment;
import com.example.skillsbuildgamification.model.EnrollmentStatus;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.CourseRepository;
import com.example.skillsbuildgamification.repository.EnrollmentRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RankService rankService;

    @Transactional
    public void updateEnrollment(String username, int courseID, EnrollmentStatus status) {
        Student student = studentRepository.findByUsername(username);
        if (student == null) {
            throw new RuntimeException("Student not found with username: " + username);
        }

        Course course = courseRepository.findById(courseID)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseID));

        Optional<Enrollment> enrollmentOpt = enrollmentRepository.findByStudentAndCourse(student, course);

        Enrollment enrollment;
        if (enrollmentOpt.isPresent()) {
            enrollment = enrollmentOpt.get();
            enrollment.setStatus(status);

            //Set completion date when marked as COMPLETED
            if (status == EnrollmentStatus.COMPLETED) {
                // Stores the current date
                enrollment.setCompletionDate(new Date());
                student.setNumCompletedCourses(student.getNumCompletedCourses() + 1);
                student.setPoints(student.getPoints() + 10);
                rankService.setRankOfStudent(username);

                if (student.getPoints() % 2 == 0) {
                    student.setLevel(student.getLevel() + 1);
                }
            } else {
                //If the course is not completed, ensure completion date is null
                enrollment.setCompletionDate(null);
            }

            awardBadges(student);
        } else {
            // Creating a new enrollment record
            enrollment = new Enrollment(student, course, status);

            //If the course is completed immediately, set completion date
            if (status == EnrollmentStatus.COMPLETED) {
                enrollment.setCompletionDate(new Date());
                student.setNumCompletedCourses(student.getNumCompletedCourses() + 1);
                student.setPoints(student.getPoints() + 10);
                rankService.setRankOfStudent(username);

                if (student.getPoints() % 2 == 0) {
                    student.setLevel(student.getLevel() + 1);
                }
            }

            // Add the enrollment to the student
            List<Enrollment> currentEnrollments = student.getEnrollments();
            currentEnrollments.add(enrollment);
            student.setEnrollments(currentEnrollments);

            awardBadges(student);
        }

        enrollmentRepository.save(enrollment);
        studentRepository.save(student);
    }

    private void awardBadges(Student student) {
        int completedCourses = student.getNumCompletedCourses();
        List<String> earnedBadges = student.getEarnedBadges();

        if (completedCourses >= 1 && !earnedBadges.contains("Novice Badge"))
            earnedBadges.add("Novice Badge");
        if (completedCourses >= 3 && !earnedBadges.contains("Intermediate Badge"))
            earnedBadges.add("Intermediate Badge");
        if (completedCourses >= 5 && !earnedBadges.contains("Advanced Badge"))
            earnedBadges.add("Advanced Badge");
        if (completedCourses >= 7 && !earnedBadges.contains("Expert Badge"))
            earnedBadges.add("Expert Badge");

        student.setEarnedBadges(earnedBadges);
    }
}

