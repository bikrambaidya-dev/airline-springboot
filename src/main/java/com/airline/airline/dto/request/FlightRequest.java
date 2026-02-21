package com.airline.airline.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FlightRequest {

    @NotBlank(message = "Flight number is required")
    @Size(min = 3, max = 10, message = "Flight number must be between 3 and 10 characters")
    private String flightNumber;
    
    @NotNull(message = "Route ID is required")
    private Long routeId;
    
    @NotNull(message = "Aircraft ID is required")
    private Long aircraftId;
    
    @NotNull(message = "Departure time is required")
    @Future(message = "Departure time must be in the future")
    private LocalDateTime departureTime;
    
    @NotNull(message = "Arrival time is required")
    @Future(message = "Arrival time must be in the future")
    private LocalDateTime arrivalTime;
    
    @NotNull(message = "Base price is required")
    @Min(value = 0, message = "Base price cannot be negative")
    private Double basePrice;
    
    // Example data:
    // {
    //   "flightNumber": "AA123",
    //   "routeId": 1,
    //   "aircraftId": 1,
    //   "departureTime": "2024-03-15T10:30:00",
    //   "arrivalTime": "2024-03-15T14:45:00",
    //   "basePrice": 299.99
    // }
    // 
    // {
    //   "flightNumber": "BA456",
    //   "routeId": 2,
    //   "aircraftId": 2,
    //   "departureTime": "2024-03-16T08:15:00",
    //   "arrivalTime": "2024-03-16T12:30:00",
    //   "basePrice": 459.50
    // }
}
