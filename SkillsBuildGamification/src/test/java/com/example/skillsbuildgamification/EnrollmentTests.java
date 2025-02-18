package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.model.EnrollmentStatus;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class EnrollmentTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentRepository studentRepository;

    @MockitoBean
    private EnrollmentService enrollmentService;

    @BeforeEach
    void setup() {
        assert studentRepository != null;
        when(studentRepository.findByUsername(anyString())).thenReturn(null);
    }

    @Test
    @WithMockUser(username = "test_user")
    void canUpdateCourseStatus() throws Exception {
        // Verifies that POST requests to update course status work

        // Mock the enrollment service acting on data
        Mockito.doNothing().when(enrollmentService).updateEnrollment(anyString(), anyInt(), any(EnrollmentStatus.class));
        String testUsername = "testUser";
        String status = "COMPLETED";
        int testCourseId = 1;

        mockMvc.perform(post("/"+testUsername+"/courseStatus/"+testCourseId)
                        .secure(true)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("status", status))
                // We expect the page to redirect the user
                .andExpect(status().is3xxRedirection());

        verify(enrollmentService, atLeastOnce()).updateEnrollment(anyString(), anyInt(), any(EnrollmentStatus.class));
    }

    @Test
    @WithMockUser(username = "test_user")
    void usingInvalidStatusValueGivesError() throws Exception {
        // Verifies that using an invalid status value shows the user an error

        String testUsername = "testUser";
        String invalidStatus = "invalid";
        int testCourseId = 1;

        mockMvc.perform(post("/"+testUsername+"/courseStatus/"+testCourseId)
                        .secure(true)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("status", invalidStatus))
                // We expect there to be an error attribute
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    @WithMockUser(username = "test_user")
    void userIsRedirectedBackToProfile() throws Exception {
        // Verifies that the user is redirected back to their profile

        // Mock the enrollment service acting on data
        Mockito.doNothing().when(enrollmentService).updateEnrollment(anyString(), anyInt(), any(EnrollmentStatus.class));
        String testUsername = "testUser";
        String status = "COMPLETED";
        String sourcePage = "editProfile";
        int testCourseId = 1;

        mockMvc.perform(post("/"+testUsername+"/courseStatus/"+testCourseId)
                        .secure(true)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("status", status)
                        .param("sourcePage", sourcePage))
                // We expect the page to redirect the user to the profile page
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/" + testUsername + "/edit"));
    }

    @Test
    @WithMockUser(username = "test_user")
    void userIsRedirectedToDashboard() throws Exception {
        // Verifies that the user is redirected to the dashboard

        // Mock the enrollment service acting on data
        Mockito.doNothing().when(enrollmentService).updateEnrollment(anyString(), anyInt(), any(EnrollmentStatus.class));
        String testUsername = "testUser";
        String status = "COMPLETED";
        String sourcePage = "other";
        int testCourseId = 1;

        mockMvc.perform(post("/"+testUsername+"/courseStatus/"+testCourseId)
                        .secure(true)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("status", status)
                        .param("sourcePage", sourcePage))
                // We expect the page to redirect the user to the profile page
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"+testUsername+"/dashboard-page"));
    }
    // Methods to test that users are redirected appropriately

}