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

import java.util.Optional;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

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
        if (enrollmentOpt.isPresent()) {
            enrollment = enrollmentOpt.get();
            enrollment.setStatus(status);
        } else {
            enrollment = new Enrollment(student, course, status);
        }

        enrollmentRepository.save(enrollment);
    }
}

