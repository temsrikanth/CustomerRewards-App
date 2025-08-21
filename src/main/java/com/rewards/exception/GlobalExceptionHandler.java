package com.rewards.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> notFound(NotFoundException ex, HttpServletRequest req) {
        ApiError err = new ApiError("Not Found", ex.getMessage(), 404, req.getRequestURI(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> badRequest(BadRequestException ex, HttpServletRequest req) {
        ApiError err = new ApiError("Bad Request", ex.getMessage(), 400, req.getRequestURI(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<ApiError.FieldError> fields = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> new ApiError.FieldError(f.getField(), f.getDefaultMessage()))
                .collect(Collectors.toList());
        ApiError err = new ApiError("Validation Failed", "One or more fields are invalid.", 400, req.getRequestURI(), fields);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> constraint(ConstraintViolationException ex, HttpServletRequest req) {
        List<ApiError.FieldError> fields = ex.getConstraintViolations().stream()
                .map(cv -> new ApiError.FieldError(cv.getPropertyPath().toString(), cv.getMessage()))
                .collect(Collectors.toList());
        ApiError err = new ApiError("Validation Failed", "One or more parameters are invalid.", 400, req.getRequestURI(), fields);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> generic(Exception ex, HttpServletRequest req) {
        ApiError err = new ApiError("Internal Server Error", ex.getMessage(), 500, req.getRequestURI(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }
}
