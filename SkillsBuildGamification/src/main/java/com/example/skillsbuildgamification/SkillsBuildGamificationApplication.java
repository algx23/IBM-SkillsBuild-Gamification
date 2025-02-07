package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.config.PasswordEncoder;
import com.example.skillsbuildgamification.model.Course;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.CourseRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.beans.Transient;

@SpringBootApplication
public class SkillsBuildGamificationApplication implements CommandLineRunner {

    // Includes demo code which should be removed later

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CourseRepository courseRepository;

    public static void main(String[] args) {
        SpringApplication.run(SkillsBuildGamificationApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Student student1 = new Student();
        student1.setUsername("bob12");
        student1.setPassword(encoder.encoder().encode("password"));

        Course course1 = new Course();
        course1.setCourseName("Artifical Intelligence Fundamentals");
        course1.setCourseLink("https://students.yourlearning.ibm.com/activity/PLAN-CC702B39D429");
        course1 = courseRepository.save(course1);
        Course course2 = new Course();
        course2.setCourseName("Cloud Computing");
        course2.setCourseLink("https://students.yourlearning.ibm.com/activity/PLAN-58FA14F64C9B");
        course2 = courseRepository.save(course2);
        Course course3 = new Course();
        course3.setCourseName("Cybersecurity");
        course3.setCourseLink("https://students.yourlearning.ibm.com/activity/PLAN-805005E992EA");
        course3 = courseRepository.save(course3);
        Course course4 = new Course();
        course4.setCourseName("Data Fundamentals");
        course4.setCourseLink("https://students.yourlearning.ibm.com/activity/PLAN-0EC2BCEA3C39");
        course4 = courseRepository.save(course4);
        Course course5 = new Course();
        course5.setCourseName("Quantum Enigmas");
        course5.setCourseLink("https://students.yourlearning.ibm.com/activity/PLAN-63384B7F59EC?channelId=CNL_LCB_1596832013328");
        course5 = courseRepository.save(course5);
        Course course6 = new Course();
        course6.setCourseName("Learn Python Tutorial");
        course6.setCourseLink("https://students.yourlearning.ibm.com/activity/URL-B634D08B3DF9?channelId=CNL_LCB_1595012253103");
        course6 = courseRepository.save(course6);
        Course course7 = new Course();
        course7.setCourseName("Python Basics for Data Analytics");
        course7.setCourseLink("https://students.yourlearning.ibm.com/activity/URL-4FE5409FB67B?channelId=CNL_LCB_1595012253103");
        course7 = courseRepository.save(course7);
        Course course8 = new Course();
        course8.setCourseName("Learn Programming with Java");
        course8.setCourseLink("https://students.yourlearning.ibm.com/activity/URL-442069BB6CBD?channelId=CNL_LCB_1595012253103");
        course8 = courseRepository.save(course8);
        Course course9 = new Course();
        course9.setCourseName("Machine Learning with Python");
        course9.setCourseLink("https://students.yourlearning.ibm.com/activity/URL-64A72F641931?channelId=CNL_LCB_1595012253103");
        course9 = courseRepository.save(course9);
        Course course10 = new Course();
        course10.setCourseName("Web Development Basics");
        course10.setCourseLink("https://students.yourlearning.ibm.com/activity/PLAN-43A030B97485");
        course10 = courseRepository.save(course10);


        student1 = studentRepository.save(student1);
    }
}
