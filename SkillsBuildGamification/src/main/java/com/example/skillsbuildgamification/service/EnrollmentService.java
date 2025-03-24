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

        // Pass course directly, not Optional.ofNullable(course)
        Optional<Enrollment> enrollmentOpt = enrollmentRepository.findByStudentAndCourse(student, course);

        Enrollment enrollment;
        Date now = new Date();

        if (enrollmentOpt.isPresent()) {
            enrollment = enrollmentOpt.get();
            enrollment.setStatus(status);

            if (status == EnrollmentStatus.COMPLETED) {
                enrollment.setCompletionDate(now);
                student.setNumCompletedCourses(student.getNumCompletedCourses() + 1);
                student.setPoints(student.getPoints() + 10);
                rankService.setRankOfStudent(username);

                int creds = student.getCredits();
                student.setCredits(creds + 15);
                awardBadges(student); // call method to award badges upon completion

                if (student.getPoints() % 2 == 0) {
                    student.setLevel(student.getLevel() + 1);
                }
            } else {
                enrollment.setCompletionDate(null);
            }

            awardBadges(student);
        } else {
            enrollment = new Enrollment(student, course, status);

            if (status == EnrollmentStatus.COMPLETED) {
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

//            check if they chose the status as Completed straight away rather than in progress
            if (status == EnrollmentStatus.COMPLETED) {
                student.setNumCompletedCourses(student.getNumCompletedCourses() + 1);
                rankService.setLevelOfStudent(username);
                rankService.setRankOfStudent(username);
                int creds = student.getCredits();
                student.setCredits(creds + 15);
                awardBadges(student);
            }

        }

        enrollmentRepository.save(enrollment);
        studentRepository.save(student);
    }

    // Award badges based on completed courses
    private void awardBadges(Student student) {
        int completedCourses = student.getNumCompletedCourses();
        List<String> earnedBadges = student.getEarnedBadges();

        // Method decides whether badge needs to be awarded based on num of courses completed
        if (completedCourses >= 1 && !earnedBadges.contains("Novice Badge"))
            earnedBadges.add("Novice Badge");
        if (completedCourses >= 3 && !earnedBadges.contains("Intermediate Badge"))
            earnedBadges.add("Intermediate Badge");
        if (completedCourses >= 5 && !earnedBadges.contains("Advanced Badge"))
            earnedBadges.add("Advanced Badge");
        if (completedCourses >= 7 && !earnedBadges.contains("Expert Badge"))
            earnedBadges.add("Expert Badge");

        student.setEarnedBadges(earnedBadges); // badge is awarded
    }
}
