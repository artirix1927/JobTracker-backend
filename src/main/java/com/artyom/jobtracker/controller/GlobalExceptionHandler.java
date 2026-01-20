package com.artyom.jobtracker.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
          .getFieldErrors()
          .forEach(error ->
              errors.put(error.getField(), error.getDefaultMessage())
          );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity.badRequest().body("You have already applied to this job.");
    }


    @ExceptionHandler(TwoFaRequiredException.class)
    public ResponseEntity<Map<String, Object>> handle2FA(TwoFaRequiredException ex) {
        return ResponseEntity.ok(Map.of(
            "require2FA", true,
            "email", ex.getEmail()
        ));
    }

    @ExceptionHandler(InvalidTwoFaCodeException.class)
    public ResponseEntity<Map<String, String>> handleInvalid2FA() {
        return ResponseEntity.badRequest().body(
            Map.of("error", "Invalid authentication code")
        );
    }

    @ExceptionHandler(TwoFaNotInitializedException.class)
    public ResponseEntity<Map<String, String>> handleNotInitialized() {
        return ResponseEntity.badRequest().body(
            Map.of("error", "2FA setup not initialized")
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.badRequest().body(
            Map.of("error", ex.getMessage())
        );
    }


}
