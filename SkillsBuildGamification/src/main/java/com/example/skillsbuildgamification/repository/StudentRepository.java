package com.example.skillsbuildgamification.repository;

import com.example.skillsbuildgamification.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, String> {
    Student findByUsername(String name);

/*  find all students, and order them by the amount of points, with the highest points first
 *  the student with the most points will be first in the list (so will appear at the top)
 */
    List<Student> findAllByOrderByPointsDesc();

//    find all students and order them with the student with the lowest number of points first
    List<Student> findAllByOrderByPointsAsc();

//    order students alphabetically A-Z and Z-A respectively
    List<Student> findAllByOrderByUsernameAsc();
    List<Student> findAllByOrderByUsernameDesc();
}
