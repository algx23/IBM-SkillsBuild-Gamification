package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.config.PasswordEncoder;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SkillsBuildGamificationApplication implements CommandLineRunner {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentRepository studentRepository;

    public static void main(String[] args) {
        SpringApplication.run(SkillsBuildGamificationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//        test data
        Student student1 = new Student();
        student1.setUsername("alex");
        student1.setPassword(passwordEncoder.encoder().encode("qwerty"));
        student1.setPoints(42);
        studentRepository.save(student1);

        Student student2 = new Student();
        student2.setUsername("bob");
        student2.setPassword(passwordEncoder.encoder().encode("qwerty"));
        student2.setPoints(69);
        studentRepository.save(student2);
    }

}
