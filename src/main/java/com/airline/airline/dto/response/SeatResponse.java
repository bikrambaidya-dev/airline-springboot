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
public class SeatResponse {
    private Long id;
    private String seatNumber;
    private AircraftResponse aircraft;
    private String status; // AVAILABLE, BOOKED, BLOCKED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Example response:
    // {
    //   "id": 1,
    //   "seatNumber": "1A",
    //   "aircraft": {
    //     "id": 1,
    //     "model": "Boeing 737-800",
    //     "totalSeats": 189
    //   },
    //   "status": "AVAILABLE",
    //   "createdAt": "2024-01-15T10:30:00",
    //   "updatedAt": "2024-01-15T10:30:00"
    // }
}
