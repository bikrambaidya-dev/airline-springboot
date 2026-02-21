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
public class RouteResponse {
    private Long id;
    private AirportResponse sourceAirport;
    private AirportResponse destinationAirport;
    private Integer distanceKm;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Example response:
    // {
    //   "id": 1,
    //   "sourceAirport": {
    //     "id": 1,
    //     "code": "JFK",
    //     "name": "John F. Kennedy International Airport",
    //     "city": "New York",
    //     "country": "United States"
    //   },
    //   "destinationAirport": {
    //     "id": 2,
    //     "code": "LHR",
    //     "name": "London Heathrow Airport",
    //     "city": "London",
    //     "country": "United Kingdom"
    //   },
    //   "distanceKm": 5567,
    //   "createdAt": "2024-01-15T10:30:00",
    //   "updatedAt": "2024-01-15T10:30:00"
    // }
}
