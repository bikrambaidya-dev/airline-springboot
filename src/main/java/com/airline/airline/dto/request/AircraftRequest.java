package com.airline.airline.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AircraftRequest {
    
    @NotBlank(message = "Aircraft model is required")
    @Size(min = 2, max = 100, message = "Aircraft model must be between 2 and 100 characters")
    private String model;

    @NotBlank(message = "Aircraft manufacturer is required")
    @Size(min = 2, max = 100, message = "Aircraft manufacturer must be between 2 and 100 characters")
    private String manufacturer;

    @NotBlank(message = "Aircraft range is required")
    @Size(min = 2, max = 100, message = "Aircraft range must be between 2 and 100 characters")
    private String range;

    @NotNull(message = "Total seats is required")
    @Min(value = 1, message = "Aircraft must have at least 1 seat")
    @Max(value = 600, message = "Aircraft cannot have more than 600 seats")
    private Integer totalSeats;
    
    // Example data:
    // {
    //   "model": "Boeing 737-800",
    //   "totalSeats": 189
    // }
    // 
    // {
    //   "model": "Airbus A320-200",
    //   "totalSeats": 180
    // }
    // 
    // {
    //   "model": "Boeing 777-300ER",
    //   "totalSeats": 396
    // }
}
