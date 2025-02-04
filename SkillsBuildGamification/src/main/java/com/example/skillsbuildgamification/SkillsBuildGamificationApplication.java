package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.config.PasswordEncoder;
import com.example.skillsbuildgamification.model.Student;
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

    public static void main(String[] args) {
        SpringApplication.run(SkillsBuildGamificationApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Student student1 = new Student();
        student1.setUsername("bob12");
        student1.setPassword(encoder.encoder().encode("password"));

        student1 = studentRepository.save(student1);
    }
}
