package com.example.skillsbuildgamification.repository;

import com.example.skillsbuildgamification.model.Course;
import com.example.skillsbuildgamification.model.Enrollment;
import com.example.skillsbuildgamification.model.Student;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends CrudRepository<Enrollment, Integer> {

    Optional<Enrollment> findByStudentAndCourse(Student student, Course course); // Find a specific enrollment (Optional - A student doesn't have to be enrolled)

    List<Enrollment> findByStudent(Student student); // Get all enrollments for a student
}

