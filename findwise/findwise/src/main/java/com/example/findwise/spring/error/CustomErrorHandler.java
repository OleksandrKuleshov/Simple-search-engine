package com.example.findwise.spring.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> onValidationException(MethodArgumentNotValidException e) {

        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();

        List<Violation> violations
                = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new Violation(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        validationErrorResponse.setViolations(violations);

        return ResponseEntity.badRequest().body(validationErrorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<IllegalArgumentExceptionResponse> onIllegalArgumentException(IllegalArgumentException e) {

       IllegalArgumentExceptionResponse errorResponse = new IllegalArgumentExceptionResponse();

       errorResponse.setError(e.getMessage());

       return ResponseEntity.badRequest().body(errorResponse);
    }
}
