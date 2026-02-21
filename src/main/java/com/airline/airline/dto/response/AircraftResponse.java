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
public class AircraftResponse {
    private Long id;
    private String model;
    private Integer totalSeats;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer availableSeats;
    
    // Example response:
    // {
    //   "id": 1,
    //   "model": "Boeing 737-800",
    //   "totalSeats": 189,
    //   "createdAt": "2024-01-15T10:30:00",
    //   "updatedAt": "2024-01-15T10:30:00",
    //   "availableSeats": 189
    // }
}
