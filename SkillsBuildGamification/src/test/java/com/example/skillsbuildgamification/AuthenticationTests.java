package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.controller.AuthenticationController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class AuthenticationTests {

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void authenticationControllerExists () {
        // Check that the authentication controller exists
        assertThat(authenticationController).isNotNull();
    }

    @Test
    void loginEndpointRespondsOk () throws Exception {
        // Check that the login endpoint responds with code 200
        mockMvc.perform(get("/login-page").secure(true)).andExpect(status().isOk());
    }

    @Test
    void successEndpointRedirectsIfNotAuthenticated () throws Exception {
        // Check that the /login-success endpoint redirects to the login page if no user is signed in
        mockMvc.perform(get("/login-success").secure(true))
                .andExpect(redirectedUrl("http://localhost/login-page"));
    }

    @Test
    void authenticatedUsersCanAccessSuccessEndpoint () throws Exception {
        // Check that the /login-success endpoint allows authenticated users to sign in
        mockMvc.perform(get("/login-success").secure(true).with(user("test user")))
                .andExpect(status().isOk());
    }
}
