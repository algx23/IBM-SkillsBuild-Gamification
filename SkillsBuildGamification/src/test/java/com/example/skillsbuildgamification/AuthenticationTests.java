package com.example.skillsbuildgamification;

import com.example.skillsbuildgamification.controller.AuthenticationController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
public class AuthenticationTests {

    @Autowired
    private AuthenticationController authenticationController;

    @Test
    void contextLoads() {
    }

    @Test
    void authenticationControllerExists () {
        // Check that the authentication controller exists
        assertThat(authenticationController).isNotNull();
    }
}
