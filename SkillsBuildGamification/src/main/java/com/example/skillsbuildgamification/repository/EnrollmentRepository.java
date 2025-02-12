package com.example.skillsbuildgamification.repository;

import com.example.skillsbuildgamification.model.Course;
import com.example.skillsbuildgamification.model.Enrollment;
import com.example.skillsbuildgamification.model.Student;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends CrudRepository<Enrollment, Integer> {

    // Retrieve an enrollment entry for a specific student and course
    Optional<Enrollment> findByStudentAndCourse(Student student, Course course);

    // Retrieve all enrollments for a student
    List<Enrollment> findByStudent(Student student);
}
