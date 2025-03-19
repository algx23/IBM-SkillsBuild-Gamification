package com.example.skillsbuildgamification.repository;

import com.example.skillsbuildgamification.model.Course;
import com.example.skillsbuildgamification.model.Enrollment;
import com.example.skillsbuildgamification.model.EnrollmentStatus;
import com.example.skillsbuildgamification.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends CrudRepository<Enrollment, Integer> {

    Optional<Enrollment> findByStudentAndCourse(Student student, Course course);

    List<Enrollment> findByStudent(Student student);

    @Query("SELECT e FROM Enrollment e WHERE e.student = :student " +
            "AND e.status = :status " +
            "AND e.completionDate >= :goalCreationDate " +
            "AND e.completionDate <= :goalCompletionDate")
    List<Enrollment> findByStudentAndStatusAndCompletionDateBetween(
            Student student, EnrollmentStatus status, Date goalCreationDate, Date goalCompletionDate);
}








