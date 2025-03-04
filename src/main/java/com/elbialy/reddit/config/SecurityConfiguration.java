package com.elbialy.reddit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable()) // disable csrf
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()); // make "/api/auth/**" public and other endpoints private
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){  // configure password encoder to use it
        return new BCryptPasswordEncoder();
    }
}
