package com.elbialy.reddit.controlAdvice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class AuthenticationExceptionHandling {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String , Object>> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request){
        Map<String ,Object> errorMap = Map.of("timestamp", LocalDateTime.now(),
                "status", HttpStatus.UNAUTHORIZED.value(),
                "error","Unauthorized",
                "message",ex.getMessage(),
                "path",request.getRequestURI() );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMap);
    }
}
