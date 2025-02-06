package com.example.skillsbuildgamification.config;

import com.example.skillsbuildgamification.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Class to configure the security of the project

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.requiresChannel(channel -> channel.anyRequest().requiresSecure())
                // Configuration for who can access which links
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login-success", "/logout").authenticated()
                        .anyRequest().permitAll())
                // Login configuration
                .formLogin(form -> form
                        .loginPage("/login-page").loginProcessingUrl("/login-process").successForwardUrl("/login-success").failureForwardUrl("/login-failure")
                        .permitAll());

        http.exceptionHandling(ex -> ex
                .accessDeniedPage("/denied")
        );

        return http.build();
    }

}
