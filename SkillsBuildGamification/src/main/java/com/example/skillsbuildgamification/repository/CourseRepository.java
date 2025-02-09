package com.example.skillsbuildgamification.repository;

import com.example.skillsbuildgamification.model.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Integer>
{
    Course findBycourseName(String courseName);

    Course findBycourseID(Integer courseID);

    @Query("SELECT courseLink FROM Course")
    List<String> findAllCourseLinks();
}
