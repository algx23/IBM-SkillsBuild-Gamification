package com.example.skillsbuildgamification.service;

import java.util.ArrayList;
import java.util.Comparator;
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
      // search for the student in the list (listToSearch is a list of student
      // objects)
      if (listToSearch.get(i) == student) {
        position = i;
        break;
      }
    }
    return position + 1; // return position + 1 as index starts at 0, so index 0 (first item in the
                         // list), will actually be in Place 1
  }

  public List<Integer> findAndHandleJointLBPos(List<Student> orderedList) {
    List<Integer> handledLeaderboardPositions = new ArrayList<>();
    int prevPos = 1;
    /*
     * Iterate through the points, if the student is first in the list,give
     * them the first position always
     */
    for (int i = 0; i < orderedList.size(); i++) {
      if (i == 0) {
        handledLeaderboardPositions.add(1);
        continue;
      }
      // if the student has the same number of points as the student before them, then
      // they get the same position as the previous student
      if ((orderedList.get(i - 1).getPoints()) == (orderedList.get(i).getPoints())) {
        handledLeaderboardPositions.add(prevPos);
      } else {
        /*
         * eg if orderedList orders students highet -> lowest pts, then if they
         * arent equal, the student has less points than the one before, so
         * they get the next pos (eg if the previous one is 2, they get position 3)
         */
        prevPos += 1;
        handledLeaderboardPositions.add(prevPos);
      }
    }
    return handledLeaderboardPositions;
  }

  public List<Student> sortFriendsInLeaderboard(List<Student> studentAndFriends, String sortBy) {
    /*
    * Based on the sortBy, use Comparator which compares the attributes of each student in the list
    *
    * If the sort needs to be from high to low, use reversed() which will sort from highest to lowest
    * as the default for Comparator is low to high
    */
    switch (sortBy) {
      case ("HighestScoreFirst"):
        studentAndFriends.sort(Comparator.comparingInt(Student::getPoints).reversed());
        break;
      case ("LowestScoreFirst"):
        studentAndFriends.sort(Comparator.comparingInt(Student::getPoints));
        break;
      case ("A-Z"):
        studentAndFriends.sort(Comparator.comparing(Student::getUsername));
        break;

      case ("Z-A"):
        studentAndFriends.sort(Comparator.comparing(Student::getUsername).reversed());
        break;

      case ("LowestCourseFirst"):
        studentAndFriends.sort(Comparator.comparingInt(Student::getNumCompletedCourses));
        break;
      case ("HighestCourseFirst"):
        studentAndFriends.sort(Comparator.comparingInt(Student::getNumCompletedCourses).reversed());
        break;

      default: // default is points high to low
        studentAndFriends.sort(Comparator.comparing(Student::getPoints).reversed());
        break;
    }
    return studentAndFriends;
  }
}