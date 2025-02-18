package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.model.Student;
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
import org.springframework.http.MediaType;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RegistrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentRepository studentRepository;

    @BeforeEach
    void setup() {
        assert studentRepository != null;
        when(studentRepository.findByUsername(anyString())).thenReturn(null);
    }

    @Test
    @WithMockUser(username = "test user")
    void creatingNewUserAddsItToRepository() throws Exception {
        // Verifies that creating a new user adds it to the repository
        String testUsername = "testUser";
        String testPassword = "password";

        mockMvc.perform(post("/registration-process")
                        .secure(true)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", testUsername)
                        .param("password", testPassword))
                // We expect the page to redirect us to /login-page upon successful user creation
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login-page"));

        verify(studentRepository, atLeastOnce()).save(any(Student.class));
    }

    @Test
    @WithMockUser(username = "test user")
    void cannotAddDuplicateUser() throws Exception {
        // Verify that it is not possible to create users with the same username
        String duplicateUsername = "duplicateUser";
        String testPassword = "password";

        // Simulate that a student with the given username already exists
        when(studentRepository.findByUsername(eq(duplicateUsername))).thenReturn(new Student());

        mockMvc.perform(post("/registration-process")
                        .secure(true)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", duplicateUsername)
                        .param("password", testPassword))
                .andDo(print())
                // Since the validator finds errors, the controller returns the registration page (HTTP 200 OK)
                .andExpect(status().isOk())
                // Verify that the "username" field has errors in the model
                .andExpect(model().attributeHasFieldErrors("student", "username"));
    }

    @Test
    @WithMockUser(username = "test user")
    void cannotAddUserWithoutUsername() throws Exception {
        // Verify that a user cannot be added without a username
        String testUsername = "";
        String testPassword = "password";

        mockMvc.perform(post("/registration-process")
                        .secure(true)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", testUsername)
                        .param("password", testPassword))
                .andDo(print())
                // Since the validator finds errors, the controller returns the registration page (HTTP 200 OK)
                .andExpect(status().isOk())
                // Verify that the "username" field has errors in the model
                .andExpect(model().attributeHasFieldErrors("student", "username"));
    }

    @Test
    @WithMockUser(username = "test user")
    void cannotAddUserWithoutPassword() throws Exception {
        // Verify that a user cannot be added without a password
        String testUsername = "username";
        String testPassword = "";

        mockMvc.perform(post("/registration-process")
                        .secure(true)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", testUsername)
                        .param("password", testPassword))
                .andDo(print())
                // Since the validator finds errors, the controller returns the registration page (HTTP 200 OK)
                .andExpect(status().isOk())
                // Verify that the "username" field has errors in the model
                .andExpect(model().attributeHasFieldErrors("student", "password"));
    }
}