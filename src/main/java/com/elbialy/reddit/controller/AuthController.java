package com.elbialy.reddit.controller;

import com.elbialy.reddit.dto.RegisterRequest;
import com.elbialy.reddit.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    @Autowired
    private  AuthService authService;
    @PostMapping("/singnup")
    public void singup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);

    }
}
