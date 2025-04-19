package org.repin.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> IllegalArgumentExceptionHandler(Exception ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> MissingServletRequestParameterExceptionHandler(Exception ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
