package com.airline.airline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
}
