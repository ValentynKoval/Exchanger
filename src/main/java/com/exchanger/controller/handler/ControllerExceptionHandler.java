package com.exchanger.controller.handler;

import com.exchanger.exception.NotUniqueUserException;
import com.exchanger.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler { // контроллер для перехвата ексепшинов в контроллере
    @ExceptionHandler(value = {NotUniqueUserException.class})
    public ResponseEntity<?> handleNotUniqueUser(Exception exception) {
        return new ResponseEntity<>("Not unique user", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<?> handleUserNotFoundException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String field = exception.getBindingResult().getFieldError().getField();
        return new ResponseEntity<>("Validation error field: " + field, HttpStatus.BAD_REQUEST);
    }
}
