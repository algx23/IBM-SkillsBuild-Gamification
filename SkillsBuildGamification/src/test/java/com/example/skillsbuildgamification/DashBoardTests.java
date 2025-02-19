package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.CourseRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class DashBoardTests {

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
    @WithMockUser(username = "test_user")
    void authenticatedUserCanAccessDashboard() throws Exception {
        // Verify that an authenticated user can access the dashboard endpoint

        // Mock the repository responding with a student
        String testUsername = "test";
        String testPassword = "test";
        Student mockStudent = new Student();
        mockStudent.setUsername(testUsername);
        mockStudent.setPassword(testPassword);
        when(studentRepository.findByUsername(anyString())).thenReturn(mockStudent);

        mockMvc.perform(get("/"+testUsername+"/dashboard-page").secure(true).with(user("test_user")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test_user")
    void userCanViewCourseStatus() throws Exception {
        // Verify that an authenticated user can access the dashboard endpoint

        // Mock the course repository
        when(courseRepository.findBycourseID(anyInt())).thenReturn(null);

        mockMvc.perform(get("/test/courseStatus/123").secure(true).with(user("test_user")))
                .andExpect(status().isOk());
    }


}