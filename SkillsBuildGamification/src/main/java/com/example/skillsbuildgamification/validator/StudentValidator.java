package com.example.skillsbuildgamification.validator;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.StudentRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class StudentValidator implements Validator {
    // Class to validate an instance of the Student class before it gets added to a repository

    private StudentRepository repo;

    public StudentValidator(StudentRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Student.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // Checks that the user has a valid username and password, and checks for duplicate students
        Student student = (Student) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "", "You must have a username.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "", "You must have a password.");

        if (repo.findByUsername(student.getUsername()) != null) {
            errors.rejectValue("username", "", "User of this username already exists");
        }
    }

}
