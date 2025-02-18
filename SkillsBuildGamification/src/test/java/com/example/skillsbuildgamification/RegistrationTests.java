package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.controller.RegistrationController;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RegistrationTest {

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
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void creatingNewUserAddsItToRepository() throws Exception {
        String testUsername = "testUser";
        String testPassword = "password";

        mockMvc.perform(post("/registration-process")
                        .secure(true)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", testUsername)
                        .param("password", testPassword))
                .andDo(print())  // debug info
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login-page"));

        verify(studentRepository, atLeastOnce()).save(any(Student.class));
    }
}