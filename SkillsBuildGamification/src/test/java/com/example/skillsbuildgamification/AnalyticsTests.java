package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.config.SecurityConfig;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.CourseRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class AnalyticsTests {
    // Tests for the analytics controller

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentRepository studentRepository;

    @MockitoBean
    private CourseRepository courseRepository;

    @BeforeEach
    void setup() {
        assert studentRepository != null;
    }

    @Test
    @WithMockUser(username = "test_user", authorities = {"ROLE_ADMIN"})
    void adminCanAccessAnalytics() throws Exception {
        // Verify that an admin can access the analytics endpoint

        // Mock the repository responding with a student
        String testUsername = "test";
        String testPassword = "test";
        Student mockStudent = new Student();
        mockStudent.setUsername(testUsername);
        mockStudent.setPassword(testPassword);
        mockStudent.setAdmin(true);
        when(studentRepository.findByUsername(anyString())).thenReturn(mockStudent);

        mockMvc.perform(get("/analytics").secure(true))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test_user", authorities = {"ROLE_USER"})
    void userCannotAccessAnalytics() throws Exception {
        // Verify that a user cannot access the analytics endpoint

        // Mock the repository responding with a student
        String testUsername = "test";
        String testPassword = "test";
        Student mockStudent = new Student();
        mockStudent.setUsername(testUsername);
        mockStudent.setPassword(testPassword);
        mockStudent.setAdmin(true);
        when(studentRepository.findByUsername(anyString())).thenReturn(mockStudent);

        mockMvc.perform(get("/analytics").secure(true))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test_user")
    void adminRedirectedToAnalyticsAfterLogin() throws Exception {
        // Verify that an admin gets redirected to the analytics endpoint after login

        // Mock the repository responding with a student
        String testUsername = "test";
        String testPassword = "test";
        Student mockStudent = new Student();
        mockStudent.setUsername(testUsername);
        mockStudent.setPassword(testPassword);
        mockStudent.setAdmin(true);
        when(studentRepository.findByUsername(anyString())).thenReturn(mockStudent);

        mockMvc.perform(get("/login-success").secure(true))
                .andExpect(redirectedUrl("/analytics"));
    }
}
