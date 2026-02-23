package com.airline.airline.exception;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

import com.airline.airline.dto.response.ApiResponse;
import com.airline.airline.dto.response.ErrorDetail;

/**
 * Global exception handler for standardized error responses.
 * All exceptions are converted to consistent ApiResponse<T> format.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle validation errors (400 Bad Request)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(
            MethodArgumentNotValidException ex, 
            HttpServletRequest request) {
        
        List<ErrorDetail> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> ErrorDetail.ofField(
                    error.getField(), 
                    error.getCode(),
                    error.getDefaultMessage()
                ))
                .toList();

        String traceId = generateTraceId();
        ApiResponse<Void> body = new ApiResponse<>();
        body.setSuccess(false);
        body.setStatus(HttpStatus.BAD_REQUEST.value());
        body.setMessage("Validation failed");
        body.setErrors(errors);
        body.setPath(request.getRequestURI());
        body.setTraceId(traceId);

        return ResponseEntity.badRequest().body(body);
    }

    /**
     * Handle ResponseStatusException (various HTTP status codes)
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatus(
            ResponseStatusException ex, 
            HttpServletRequest request) {
        
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        String traceId = generateTraceId();

        ApiResponse<Void> body = new ApiResponse<>();
        body.setSuccess(false);
        body.setStatus(status.value());
        body.setMessage(ex.getReason() != null ? ex.getReason() : status.getReasonPhrase());
        body.setErrors(List.of(ErrorDetail.of(status.getReasonPhrase(), 
                ex.getReason() != null ? ex.getReason() : status.getReasonPhrase())));
        body.setPath(request.getRequestURI());
        body.setTraceId(traceId);

        return ResponseEntity.status(status).body(body);
    }

    /**
     * Handle IllegalArgumentException (400 Bad Request)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(
            IllegalArgumentException ex, 
            HttpServletRequest request) {
        
        String traceId = generateTraceId();
        ApiResponse<Void> body = new ApiResponse<>();
        body.setSuccess(false);
        body.setStatus(HttpStatus.BAD_REQUEST.value());
        body.setMessage("Invalid argument");
        body.setErrors(List.of(ErrorDetail.of("INVALID_ARGUMENT", 
                ex.getMessage() != null ? ex.getMessage() : "Invalid argument provided")));
        body.setPath(request.getRequestURI());
        body.setTraceId(traceId);

        return ResponseEntity.badRequest().body(body);
    }

    /**
     * Handle DataIntegrityViolationException (409 Conflict)
     * This occurs when unique constraints, foreign keys, etc. are violated.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicate(
            DataIntegrityViolationException ex, 
            HttpServletRequest request) {
        
        String traceId = generateTraceId();
        ApiResponse<Void> body = new ApiResponse<>();
        body.setSuccess(false);
        body.setStatus(HttpStatus.CONFLICT.value());
        body.setMessage("Data conflict: duplicate or constraint violation");
        body.setErrors(List.of(ErrorDetail.of("CONSTRAINT_VIOLATION", 
                "Resource with similar data already exists or constraint is violated")));
        body.setPath(request.getRequestURI());
        body.setTraceId(traceId);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    /**
     * Handle EntityNotFoundException (custom business logic exception)
     * Add this if you have a custom exception class for missing resources.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(
            EntityNotFoundException ex, 
            HttpServletRequest request) {
        
        String traceId = generateTraceId();
        ApiResponse<Void> body = new ApiResponse<>();
        body.setSuccess(false);
        body.setStatus(HttpStatus.NOT_FOUND.value());
        body.setMessage("Resource not found");
        body.setErrors(List.of(ErrorDetail.of("NOT_FOUND", ex.getMessage())));
        body.setPath(request.getRequestURI());
        body.setTraceId(traceId);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    /**
     * Handle all other uncaught exceptions (500 Internal Server Error)
     * NOTE: Stack trace is NOT exposed for security. Only a generic message is shown.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpected(
            Exception ex, 
            HttpServletRequest request) {
        
        String traceId = generateTraceId();
        
        // Log the actual exception with stack trace (configure in logback.xml)
        // logger.error("Unexpected error with traceId: {}", traceId, ex);
        
        ApiResponse<Void> body = new ApiResponse<>();
        body.setSuccess(false);
        body.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.setMessage("Internal server error");
        body.setErrors(List.of(ErrorDetail.of("INTERNAL_SERVER_ERROR", 
                "An unexpected error occurred. Please try again later.")));
        body.setPath(request.getRequestURI());
        body.setTraceId(traceId);  // Client can use this to find error in logs

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    /**
     * Generate unique trace ID for error correlation across logs
     */
    private String generateTraceId() {
        return UUID.randomUUID().toString();
    }
}
