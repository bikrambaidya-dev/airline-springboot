package com.airline.airline.dto.response;

import java.time.Instant;
import java.util.List;

/**
 * Universal API Response wrapper for all endpoints.
 * Supports both success and error responses in a single, consistent structure.
 * 
 * @param <T> The type of data being returned
 */
public class ApiResponse<T> {
    private boolean success;
    private int status;
    private String message;
    private T data;
    private List<ErrorDetail> errors;
    private String path;
    private String traceId;
    private Instant timestamp;

    public ApiResponse() {
        this.timestamp = Instant.now();
    }

    /**
     * Backward-compatible constructor for success responses (legacy code)
     * Auto-detects status code based on success flag
     */
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.status = success ? 200 : 400;  // Default: 200 OK for success, 400 Bad Request for error
        this.message = message;
        this.data = data;
        this.timestamp = Instant.now();
    }

    /**
     * Constructor for success responses with data
     */
    public ApiResponse(int status, String message, T data) {
        this.success = true;
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = Instant.now();
    }

    /**
     * Constructor for error responses
     */
    public ApiResponse(int status, String message, List<ErrorDetail> errors) {
        this.success = false;
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.timestamp = Instant.now();
    }

    /**
     * Full constructor for complete control
     */
    public ApiResponse(boolean success, int status, String message, T data, 
                      List<ErrorDetail> errors, String path, String traceId) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.data = data;
        this.errors = errors;
        this.path = path;
        this.traceId = traceId;
        this.timestamp = Instant.now();
    }

    // Builder pattern for clean construction
    public static <T> ApiResponseBuilder<T> builder() {
        return new ApiResponseBuilder<>();
    }

    /**
     * Builder class for fluent API response construction
     */
    public static class ApiResponseBuilder<T> {
        private boolean success;
        private int status;
        private String message;
        private T data;
        private List<ErrorDetail> errors;
        private String path;
        private String traceId;

        public ApiResponseBuilder<T> success(boolean success) {
            this.success = success;
            return this;
        }

        public ApiResponseBuilder<T> status(int status) {
            this.status = status;
            return this;
        }

        public ApiResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ApiResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ApiResponseBuilder<T> errors(List<ErrorDetail> errors) {
            this.errors = errors;
            return this;
        }

        public ApiResponseBuilder<T> path(String path) {
            this.path = path;
            return this;
        }

        public ApiResponseBuilder<T> traceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        public ApiResponse<T> build() {
            ApiResponse<T> response = new ApiResponse<>();
            response.success = this.success;
            response.status = this.status;
            response.message = this.message;
            response.data = this.data;
            response.errors = this.errors;
            response.path = this.path;
            response.traceId = this.traceId;
            response.timestamp = Instant.now();
            return response;
        }
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
