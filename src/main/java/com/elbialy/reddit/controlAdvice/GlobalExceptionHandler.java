package com.elbialy.reddit.controlAdvice;

import com.elbialy.reddit.exceptions.SpringRedditException;
import com.elbialy.reddit.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.AccessException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object> >handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request){
        Map<String ,Object> errorMap = Map.of("timestamp", LocalDateTime.now(),
                "status",HttpStatus.UNAUTHORIZED.value(),
                "error","Unauthorized",
                "message",ex.getMessage(),
                "path",request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMap);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object> >handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request){
        Map<String ,Object> errorMap = Map.of("timestamp", LocalDateTime.now(),
                "status",HttpStatus.NOT_FOUND.value(),
                "error","Not Found",
                "message",ex.getMessage(),
                "path",request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMap);
    }
    @ExceptionHandler(SpringRedditException.class)
    public ResponseEntity<Map<String, Object>>handleSpringRedditException(SpringRedditException ex, HttpServletRequest request){
        Map<String, Object> errorMap = Map.of("timestamp", LocalDateTime.now(),
                "status",HttpStatus.BAD_REQUEST.value(),
                "error","Bad Request",
                "message",ex.getMessage(),
                "path",request.getRequestURI() );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }
    @ExceptionHandler(AccessException.class)
    public ResponseEntity<Map<String, Object>>handleAccessDeniedException(Exception ex, HttpServletRequest request){
        Map<String ,Object> errorMap = Map.of("timestamp", LocalDateTime.now(),
                "status",HttpStatus.UNAUTHORIZED.value(),
                "error","Unauthorized",
                "message",ex.getMessage(),
                "path",request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMap);
    }
}
