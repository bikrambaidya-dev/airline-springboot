package com.airline.airline.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.airline.airline.dto.response.ApiResponse;

public class ResponseUtil {

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        ApiResponse<T> body = new ApiResponse<>(true, message, data);
        return ResponseEntity.ok(body);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
        ApiResponse<T> body = new ApiResponse<>(true, message, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(String message) {
        ApiResponse<T> body = new ApiResponse<>(false, message, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static <T> ResponseEntity<ApiResponse<T>> serverError(String message) {
        return error(message);
    }
}
