package com.airline.airline.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RouteRequest {
    
    @NotNull(message = "Source airport ID is required")
    private Long sourceAirportId;
    
    @NotNull(message = "Destination airport ID is required")
    private Long destinationAirportId;
    
    @NotNull(message = "Distance is required")
    @Min(value = 1, message = "Distance must be at least 1 km")
    private Integer distanceKm;
    
    // Example data:
    // {
    //   "sourceAirportId": 1,
    //   "destinationAirportId": 2,
    //   "distanceKm": 5567
    // }
    // 
    // {
    //   "sourceAirportId": 3,
    //   "destinationAirportId": 4,
    //   "distanceKm": 8756
    // }
    // 
    // {
    //   "sourceAirportId": 5,
    //   "destinationAirportId": 6,
    //   "distanceKm": 12450
    // }
}
