package com.airline.airline.dto.response;

/**
 * Represents a single error detail with optional field information.
 * Used for both validation errors and general error details.
 */
public class ErrorDetail {
    private String field;          // For field validation errors (optional)
    private String code;           // Error code for client-side handling
    private String message;        // Human-readable message

    public ErrorDetail() {
    }

    /**
     * Constructor for field validation errors
     */
    public ErrorDetail(String field, String message) {
        this.field = field;
        this.message = message;
    }

    /**
     * Constructor with error code
     */
    public ErrorDetail(String field, String code, String message) {
        this.field = field;
        this.code = code;
        this.message = message;
    }

    /**
     * Static factory for general errors (no field)
     */
    public static ErrorDetail of(String code, String message) {
        ErrorDetail detail = new ErrorDetail();
        detail.code = code;
        detail.message = message;
        return detail;
    }

    /**
     * Static factory for field errors
     */
    public static ErrorDetail ofField(String field, String message) {
        return new ErrorDetail(field, message);
    }

    /**
     * Static factory for field errors with code
     */
    public static ErrorDetail ofField(String field, String code, String message) {
        return new ErrorDetail(field, code, message);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
