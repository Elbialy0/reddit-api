package com.elbialy.reddit.config;

import com.elbialy.reddit.exceptions.CustomBasicAuthenticationEntryPoint;
import com.elbialy.reddit.filter.JwtValidatorFilter;
import com.elbialy.reddit.mapper.SubRedditMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@AllArgsConstructor
public class SecurityConfiguration {
    private final JwtValidatorFilter JwtValidatorFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(csrf->csrf.disable())
                .cors(corsConfig -> corsConfig.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:5173")); // Allow frontend origin
                    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Explicit methods
                    corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Explicit allowed headers
                    corsConfiguration.setExposedHeaders(Arrays.asList("Authorization")); // Expose Authorization header
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.setMaxAge(3600L); // Cache preflight requests for 1 hour
                    return corsConfiguration;
                }))

                .addFilterBefore(JwtValidatorFilter, UsernamePasswordAuthenticationFilter.class)// disable csrf
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/auth/**",                    "/swagger-ui/**",  // Allow Swagger UI
                                "/v3/api-docs/**", // Allow OpenAPI JSON
                                "/swagger-resources/**",
                                "/webjars/**","/error").permitAll()
                .anyRequest().authenticated());// make "/api/auth/**" public and other endpoints private
        http.httpBasic(hbc->hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){

        RedditAuthenticationProvider redditAuthenticationProvider = new RedditAuthenticationProvider( userDetailsService,passwordEncoder);
        ProviderManager providerManager = new ProviderManager(redditAuthenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
