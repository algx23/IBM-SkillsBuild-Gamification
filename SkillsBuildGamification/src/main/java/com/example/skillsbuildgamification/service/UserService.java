package com.example.skillsbuildgamification.service;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    // Provides a service for loading users to be used with Spring Security

    @Autowired
    private StudentRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = repo.findByUsername(username);
        if (student != null) {
            // Construct a User from info provided by the repo
            return User.builder().username(student.getUsername()).
                    // Replace "USER" with roles if implemented later
                    password(student.getPassword()).authorities("USER").
                    build();
        }
        else {
            throw new UsernameNotFoundException("Could not find user " + username);
        }
    }

}
