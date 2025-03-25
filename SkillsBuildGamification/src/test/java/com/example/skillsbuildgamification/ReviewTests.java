package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.config.SecurityConfig;
import com.example.skillsbuildgamification.model.Course;
import com.example.skillsbuildgamification.model.Review;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.CourseRepository;
import com.example.skillsbuildgamification.repository.ReviewRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class ReviewTests {
    // Tests for the review controller

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentRepository studentRepository;

    @MockitoBean
    private CourseRepository courseRepository;

    @MockitoBean
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setup() {
        assert studentRepository != null;
    }


    @Test
    @WithMockUser(username = "test_user", authorities = {"ROLE_USER"})
    void userCanSeeReviews() throws Exception {
        // Verify that a user can access the reviews endpoint

        // Create mock data
        String testUsername = "test";
        String testPassword = "test";
        Student mockStudent = new Student();
        mockStudent.setUsername(testUsername);
        mockStudent.setPassword(testPassword);
        mockStudent.setAdmin(true);

        Course mockCourse = new Course();
        mockCourse.setCourseName("test_course");

        Review mockReview = new Review();
        ArrayList<Review> mockReviews = new ArrayList<>();
        mockReviews.add(mockReview);

        // Mock repositories
        when(studentRepository.findByUsername(anyString())).thenReturn(mockStudent);
        when(courseRepository.findBycourseName(anyString())).thenReturn(mockCourse);
        when(reviewRepository.findAllByCourseAssociated(any(Course.class))).thenReturn(mockReviews);


        mockMvc.perform(get("/seeReviews?courseName=" + mockCourse.getCourseName() + "&username=" + testUsername)
                .secure(true))
                .andExpect(status().isOk());
    }
}
