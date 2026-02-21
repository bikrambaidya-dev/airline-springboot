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
public class FlightResponse {
    private Long id;
    private String flightNumber;
    private RouteResponse route;
    private AircraftResponse aircraft;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Double basePrice;
    private String status; // ACTIVE, CANCELLED, COMPLETED, DELAYED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer availableSeats;
    private List<SeatResponse> seats;
    
    // Example response:
    // {
    //   "id": 1,
    //   "flightNumber": "AA123",
    //   "route": {
    //     "id": 1,
    //     "sourceAirport": {
    //       "id": 1,
    //       "code": "JFK",
    //       "name": "John F. Kennedy International Airport",
    //       "city": "New York",
    //       "country": "United States"
    //     },
    //     "destinationAirport": {
    //       "id": 2,
    //       "code": "LHR",
    //       "name": "London Heathrow Airport",
    //       "city": "London",
    //       "country": "United Kingdom"
    //     },
    //     "distanceKm": 5567
    //   },
    //   "aircraft": {
    //     "id": 1,
    //     "model": "Boeing 737-800",
    //     "totalSeats": 189
    //   },
    //   "departureTime": "2024-03-15T10:30:00",
    //   "arrivalTime": "2024-03-15T14:45:00",
    //   "basePrice": 299.99,
    //   "status": "ACTIVE",
    //   "createdAt": "2024-01-15T10:30:00",
    //   "updatedAt": "2024-01-15T10:30:00",
    //   "availableSeats": 189,
    //   "seats": []
    // }
}
