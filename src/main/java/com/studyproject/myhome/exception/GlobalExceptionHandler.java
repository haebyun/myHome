package com.studyproject.myhome.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PageValidationException.class)
    public ResponseEntity<String> handlePageValidationException(PageValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
