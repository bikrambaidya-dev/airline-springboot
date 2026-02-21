package com.airline.airline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String role; // ADMIN, USER
    private String status; // ACTIVE, INACTIVE, SUSPENDED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
    private List<String> permissions;
    
    // Example response:
    // {
    //   "id": 1,
    //   "username": "john.doe",
    //   "email": "john.doe@example.com",
    //   "firstName": "John",
    //   "lastName": "Doe",
    //   "role": "USER",
    //   "status": "ACTIVE",
    //   "createdAt": "2024-01-15T10:30:00",
    //   "updatedAt": "2024-01-15T10:30:00",
    //   "lastLoginAt": "2024-03-14T15:45:00",
    //   "permissions": ["BOOK_FLIGHT", "VIEW_BOOKINGS", "UPDATE_PROFILE"]
    // }
}
