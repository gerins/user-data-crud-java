package com.java.server.user.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.java.server.user.model.WebResponse;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse<String>> constraintViolationException(ConstraintViolationException exception) {
        String message = exception.getMessage();
        var webResponse = WebResponse.<String>builder().errros(message).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(webResponse);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> responseStatusException(ResponseStatusException exception) {
        String reason = exception.getReason();
        var webResponse = WebResponse.<String>builder().errros(reason).build();

        return ResponseEntity.status(exception.getStatusCode()).body(webResponse);
    }
}
