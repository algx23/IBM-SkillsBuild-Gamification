package com.example.skillsbuildgamification.repository;

import com.example.skillsbuildgamification.model.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Integer>
{
    Course findBycourseName(String courseName);
}
