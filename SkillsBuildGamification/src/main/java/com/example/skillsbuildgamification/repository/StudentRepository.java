package com.example.skillsbuildgamification.repository;

import com.example.skillsbuildgamification.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, String> {
    Student findByUsername(String name);

    /*
     * find all students, and order them by the amount of points, with the highest
     * points first
     * the student with the most points will be first in the list (so will appear at
     * the top)
     */
    List<Student> findAllByOrderByPointsDesc();

    // find all students and order them with the student with the lowest number of
    // points first
    List<Student> findAllByOrderByPointsAsc();

    // order students alphabetically A-Z and Z-A respectively
    List<Student> findAllByOrderByUsernameAsc();

    List<Student> findAllByOrderByUsernameDesc();

    @Query("SELECT COUNT(s) + 1 FROM Student s WHERE s.points > (SELECT s2.points FROM Student s2 WHERE s2.username = :username)")
    int findLeaderboardPosition(@Param("username") String username); // finds leader board position by counting users based on points

    // order by courses completed high -> low, and low -> high
    List<Student> findAllByOrderByNumCompletedCoursesDesc();
    List<Student> findAllByOrderByNumCompletedCoursesAsc();

//    find the friends of each student
   List<Student> findFriendsByUsername(String username);
}
