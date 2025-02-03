package com.example.skillsbuildgamification.repository;

import com.example.skillsbuildgamification.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
    Student findByUsername(String name);
}
