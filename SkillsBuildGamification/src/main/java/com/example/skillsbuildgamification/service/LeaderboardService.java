package com.example.skillsbuildgamification.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.example.skillsbuildgamification.model.Enrollment;
import com.example.skillsbuildgamification.model.EnrollmentStatus;
import com.example.skillsbuildgamification.model.Student;

@Service
@Transactional
public class LeaderboardService {

  public int findPrincipalPosInSortedLB(Student student, List<Student> listToSearch) {
    int position = 0;
    for (int i = 0; i < listToSearch.size(); ++i) {
      // find the index (position) of the student in the list
//      search for the student in the list (listToSearch is a list of student objects)
      if (listToSearch.get(i) == student) {
        position = i;
        break;
      }
    }
    return position + 1; // return position + 1 as index starts at 0, so index 0 (first item in the list), will actually be in Place 1
  }


  public List<Integer> findNumberOfCoursesCompleted(List<Student> orderedStudents) {
    List<Integer> courseCompList = new ArrayList<>();
/*    for each student, get the enrollments, and if the enrollment is marked as completed, increment the number of
 *  completed courses. Add this number, after iterating through all increments, and the order of this, will correspond
 *   to each student
 */
    for (Student student : orderedStudents) {
      int numCompletedCourses = 0;
      for (Enrollment enrollment : student.getEnrollments()) {
        if (enrollment.getStatus() == EnrollmentStatus.COMPLETED) {
          numCompletedCourses += 1;
        }
      }
      student.setNumCompletedCourses(numCompletedCourses);
      courseCompList.add(student.getNumCompletedCourses());
    }
    return courseCompList;
  }
}