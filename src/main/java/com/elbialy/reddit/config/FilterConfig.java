package com.elbialy.reddit.config;

import com.elbialy.reddit.filter.JwtValidatorFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    private JwtValidatorFilter jwtRequestFilter;

    @Bean
    public FilterRegistrationBean<JwtValidatorFilter> jwtFilterRegistration() {
        FilterRegistrationBean<JwtValidatorFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtRequestFilter);
        registrationBean.addUrlPatterns("/*"); // Apply to all URLs
        registrationBean.setOrder(1); // Set filter order if needed
        return registrationBean;
    }
}