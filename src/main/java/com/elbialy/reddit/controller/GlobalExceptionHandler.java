package com.elbialy.reddit.controller;


import com.elbialy.reddit.exceptions.SpringRedditException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


public class GlobalExceptionHandler {

    @ExceptionHandler(SpringRedditException.class)
    public ResponseEntity<SpringRedditException> handleRunTimeExceptions(SpringRedditException ex){

        return ResponseEntity.badRequest().body(ex);

    }
}