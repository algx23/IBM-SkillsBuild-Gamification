package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProfileTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentRepository studentRepository;

    @MockitoBean
    private EnrollmentService enrollmentService;

    @BeforeEach
    void setup() {
        assert studentRepository != null;
    }

    @Test
    @WithMockUser(username = "otherUser")
    void userCannotViewEditProfileFormForOtherProfile() throws Exception {
        // Verifies that a user cannot access the edit profile form if they are not the owner of the profile

        // Mock who the "real" user is
        String accountUsername = "testUser";
        String otherUsername = "otherUser";
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn(otherUsername);

        mockMvc.perform(get("/profile/"+accountUsername+"/edit")
                        .secure(true)
                        .principal(mockPrincipal)
                        .with(csrf()))
                // We expect the page to redirect the user to /denied
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/denied"));
    }

    @Test
    @WithMockUser(username = "testUser")
    void userCanUpdateProfile() throws Exception {
        // Verifies that the student repository is updated after the user updates their profile

        // Variables that will be needed for the post request
        String testUsername = "testUser";
        String testPassword = "test";
        Student testStudent = new Student();
        testStudent.setUsername(testUsername);
        testStudent.setPassword(testPassword);

        // Mock the actions of the student repository
        when(studentRepository.findByUsername(anyString())).thenReturn(testStudent);

        // Mock passing the principal parameter
        Principal mockPrincipal = Mockito.mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn(testUsername);

        mockMvc.perform(post("/profile/"+testUsername+"/update")
                        .secure(true)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("password", testPassword)
                        .param("bio", "bio")
                        .param("profilePic","")
                        .principal(mockPrincipal))
                // We expect the page to redirect the user to the profile page
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/" + testUsername));

        // Ensure that the controller attempts to save the record
        verify(studentRepository, atLeastOnce()).save(any(Student.class));
    }

}