package com.elbialy.reddit.controller;

import com.elbialy.reddit.dto.RegisterRequest;
import com.elbialy.reddit.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    @Autowired
    private  AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration success", HttpStatus.OK);
    }
    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> accountVerification(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Verification successfully",HttpStatus.OK);
    }
}
