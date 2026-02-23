package com.airline.airline.util;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.airline.airline.dto.response.ApiResponse;
import com.airline.airline.dto.response.ErrorDetail;

/**
 * Utility class for creating standardized API responses.
 * Provides static factory methods for common response scenarios.
 */
public class ResponseUtil {

    /**
     * 200 OK - Standard successful response with data
     */
    public static <T> ResponseEntity<ApiResponse<T>> ok(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(message);
        response.setData(data);
        return ResponseEntity.ok(response);
    }

    /**
     * 201 Created - Resource successfully created
     */
    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage(message);
        response.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 204 No Content - Successful operation with no data
     */
    public static ResponseEntity<ApiResponse<Void>> noContent(String message) {
        ApiResponse<Void> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setStatus(HttpStatus.NO_CONTENT.value());
        response.setMessage(message);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    /**
     * 400 Bad Request - Validation errors
     */
    public static <T> ResponseEntity<ApiResponse<T>> badRequest(String message, List<ErrorDetail> errors) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(message);
        response.setErrors(errors);
        response.setTraceId(generateTraceId());
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * 400 Bad Request - Single error message
     */
    public static <T> ResponseEntity<ApiResponse<T>> badRequest(String message) {
        return badRequest(message, List.of(ErrorDetail.of("VALIDATION_ERROR", message)));
    }

    /**
     * 401 Unauthorized
     */
    public static <T> ResponseEntity<ApiResponse<T>> unauthorized(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setMessage(message);
        response.setErrors(List.of(ErrorDetail.of("UNAUTHORIZED", message)));
        response.setTraceId(generateTraceId());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * 403 Forbidden
     */
    public static <T> ResponseEntity<ApiResponse<T>> forbidden(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setMessage(message);
        response.setErrors(List.of(ErrorDetail.of("FORBIDDEN", message)));
        response.setTraceId(generateTraceId());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    /**
     * 404 Not Found
     */
    public static <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
        String msg = message != null ? message : "Resource not found";
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(msg);
        response.setErrors(List.of(ErrorDetail.of("NOT_FOUND", msg)));
        response.setTraceId(generateTraceId());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * 409 Conflict - Resource already exists or constraint violation
     */
    public static <T> ResponseEntity<ApiResponse<T>> conflict(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setMessage(message);
        response.setErrors(List.of(ErrorDetail.of("CONFLICT", message)));
        response.setTraceId(generateTraceId());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * 500 Internal Server Error
     */
    public static <T> ResponseEntity<ApiResponse<T>> internalServerError(String message) {
        String msg = message != null ? message : "Internal server error";
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage(msg);
        response.setErrors(List.of(ErrorDetail.of("INTERNAL_SERVER_ERROR", msg)));
        response.setTraceId(generateTraceId());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * 500 Internal Server Error - With custom error details
     */
    public static <T> ResponseEntity<ApiResponse<T>> internalServerError(String message, List<ErrorDetail> errors) {
        String msg = message != null ? message : "Internal server error";
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage(msg);
        response.setErrors(errors);
        response.setTraceId(generateTraceId());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Generic error response with custom HTTP status
     */
    public static <T> ResponseEntity<ApiResponse<T>> error(HttpStatus status, String message, List<ErrorDetail> errors) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setStatus(status.value());
        response.setMessage(message);
        response.setErrors(errors);
        response.setTraceId(generateTraceId());
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Generic error response with custom HTTP status - single error
     */
    public static <T> ResponseEntity<ApiResponse<T>> error(HttpStatus status, String message) {
        return error(status, message, List.of(ErrorDetail.of("ERROR", message)));
    }

    /**
     * Generate unique trace ID for error correlation
     */
    public static String generateTraceId() {
        return UUID.randomUUID().toString();
    }

    // Convenience aliases for backward compatibility
    
    /**
     * Alias for ok() to support legacy code
     */
    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        return ok(data, message);
    }

    /**
     * Convenience method for generic error (400 Bad Request)
     */
    public static ResponseEntity<ApiResponse<Void>> error(String message) {
        return badRequest(message);
    }}