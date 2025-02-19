package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.config.PasswordEncoder;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.service.LeaderboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
public class LeaderboardTests {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LeaderboardService leaderboardService;

    @Test
    void contextLoads() {
    }

    @BeforeEach
    public void createTestData() {
        // make sure i only use the data in the test class instead of data entered in
        // SkillsBuildApplicaton.java (main class)
        studentRepository.deleteAll();

        // test data
        Student student1 = new Student();
        student1.setUsername("Alex");
        student1.setPoints(34);
        student1.setNumCompletedCourses(34);
        student1.setPassword(passwordEncoder.encoder().encode("123456"));

        Student student2 = new Student();
        student2.setUsername("Bob");
        student2.setPoints(90);
        student2.setNumCompletedCourses(90);
        student2.setPassword(passwordEncoder.encoder().encode("123456"));

        Student student3 = new Student();
        student3.setUsername("Jack");
        student3.setNumCompletedCourses(80);
        student3.setPoints(80);
        student3.setPassword(passwordEncoder.encoder().encode("123456"));

        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);

    }

    /*
     * test the sort methods work by checking the order of the studnts based on how
     * many points they got - so check the expected order
     */

    // points ascending
    @Test
    public void testSortByPointsAscending() {

        List<Student> studentList = studentRepository.findAllByOrderByPointsAsc();
        // test that all students have been added correctly
        assert studentList.size() == 3;
        // check the students are in the same place
        assert studentList.get(0).getUsername().equals("Alex");
        assert studentList.get(1).getUsername().equals("Jack");
        assert studentList.get(2).getUsername().equals("Bob");

    }

    // points descending
    @Test
    public void testSortByPointsDescending() {
        List<Student> studentList = studentRepository.findAllByOrderByPointsDesc();
        assert studentList.size() == 3;
        assert studentList.get(0).getUsername().equals("Bob");
        assert studentList.get(1).getUsername().equals("Jack");
        assert studentList.get(2).getUsername().equals("Alex");
    }

    // testing leadeboard positions are correct
    @Test
    public void testLBPos() {
        List<Student> studentList = studentRepository.findAllByOrderByPointsDesc();
        assert studentList.size() == 3;
        List<Integer> positions = leaderboardService.findAndHandleJointLBPos(studentList);

        assert positions.size() == 3;

        assert positions.get(0) == 1;
        assert positions.get(1) == 2;
        assert positions.get(2) == 3;
    }

    // test if the lb positions are tied, that the right positions are calculated
    // and returned
    @Test
    public void testLBPointsTie() {
        Student student4 = new Student();
        student4.setUsername("Jeff");
        student4.setNumCompletedCourses(90);
        student4.setPoints(90);
        student4.setPassword(passwordEncoder.encoder().encode("123456"));
        studentRepository.save(student4);
        List<Student> studentList = studentRepository.findAllByOrderByPointsDesc();
        assert studentList.size() == 4;
        List<Integer> positions = leaderboardService.findAndHandleJointLBPos(studentList);

        assert positions.size() == 4; // after student4 added now there is 4 students
        assert positions.get(0) == 1;
        assert positions.get(1) == 1;
        assert positions.get(2) == 2;
        System.out.println("doen");
    }

    // test username A - Z
    @Test
    public void testSortByUsernameAscending() {
        List<Student> studentList = studentRepository.findAllByOrderByUsernameAsc();
        assert studentList.size() == 3;
        assert studentList.get(0).getUsername().equals("Alex");
        assert studentList.get(1).getUsername().equals("Bob");
        assert studentList.get(2).getUsername().equals("Jack");
    }

    @Test
    public void testSortByUsernameDescending() {
        List<Student> studentList = studentRepository.findAllByOrderByUsernameDesc();
        assert studentList.size() == 3;
        assert studentList.get(0).getUsername().equals("Jack");
        assert studentList.get(1).getUsername().equals("Bob");
        assert studentList.get(2).getUsername().equals("Alex");
    }

    @Test
    // testing sort by num courses ascending
    public void testSortByNumberOfCoursesCompletedAscending() {
        List<Student> numCourses = studentRepository.findAllByOrderByNumCompletedCoursesAsc();
        assert numCourses.size() == 3;
        assert numCourses.get(0).getUsername().equals("Alex");
        assert numCourses.get(1).getUsername().equals("Jack");
        assert numCourses.get(2).getUsername().equals("Bob");
    }

    @Test
    public void testSortByNumberOfCoursesDescending() {
        List<Student> numCourses = studentRepository.findAllByOrderByNumCompletedCoursesDesc();
        assert numCourses.size() == 3;
        assert numCourses.get(0).getUsername().equals("Bob");
        assert numCourses.get(1).getUsername().equals("Jack");
        assert numCourses.get(2).getUsername().equals("Alex");
    }
}
