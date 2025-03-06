package com.elbialy.reddit.controller;

import com.elbialy.reddit.dto.LoginRequest;
import com.elbialy.reddit.dto.LoginResponse;
import com.elbialy.reddit.dto.RegisterRequest;
import com.elbialy.reddit.model.User;
import com.elbialy.reddit.security.JwtGenerator;
import com.elbialy.reddit.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor

public class AuthController {

    private final   AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
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
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws AccessDeniedException {
        String jwt = null;
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername()
                ,loginRequest.getPassword());
       Authentication authenticationResponse = authenticationManager.authenticate(authentication);
       if (null!=authenticationResponse && authenticationResponse.isAuthenticated()){
           jwt=jwtGenerator.jwtGenerator(authenticationResponse);
           return  ResponseEntity.status(HttpStatus.OK).header("Authorization",jwt).body("sucessfully logged in");
       } else throw new AccessDeniedException("Bad credentials");


    }
}
