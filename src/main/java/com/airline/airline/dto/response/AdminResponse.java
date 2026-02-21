package com.airline.airline.dto.response;

import java.time.LocalDateTime;

public class AdminResponse {

    private boolean success;
    private String message;
    private Object data;
    private LocalDateTime timestamp;

    private AdminResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static AdminResponse success(String message, Object data) {
        return new AdminResponse(true, message, data);
    }

    public static AdminResponse created(String message, Object data) {
        return new AdminResponse(true, message, data);
    }

    public static AdminResponse error(String message) {
        return new AdminResponse(false, message, null);
    }

    // ✅ ADD GETTERS
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
