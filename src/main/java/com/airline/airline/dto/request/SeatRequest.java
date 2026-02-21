package com.airline.airline.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatRequest {

    @NotNull(message = "Aircraft ID is required")
    private Long aircraftId;
    
    @NotNull(message = "Seat numbers are required")
    @Size(min = 1, message = "At least one seat number is required")
    private List<@jakarta.validation.constraints.NotBlank(message = "Seat number cannot be blank") String> seatNumbers;
    
    // Example data:
    // {
    //   "aircraftId": 1,
    //   "seatNumbers": ["1A", "1B", "1C", "1D", "2A", "2B", "2C", "2D"]
    // }
    // 
    // {
    //   "aircraftId": 2,
    //   "seatNumbers": ["1A", "1B", "1C", "2A", "2B", "2C", "3A", "3B", "3C"]
    // }
    // 
    // {
    //   "aircraftId": 3,
    //   "seatNumbers": ["1A", "1B", "1C", "1D", "1E", "1F", "2A", "2B", "2C", "2D", "2E", "2F"]
    // }
}
