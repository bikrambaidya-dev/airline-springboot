package com.airline.airline.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
public class AirportRequest {

    private Long id;

    @Schema(example = "DXB")
    @NotBlank(message = "Airport code is required")
    @Size(min = 3, max = 3, message = "Airport code must be exactly 3 characters")
    private String code;

    @Schema(example = "Dubai International Airport")
    @NotBlank(message = "Airport name is required")
    @Size(max = 100, message = "Airport name cannot exceed 100 characters")
    private String name;

    @Schema(example = "Dubai")
    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City name cannot exceed 50 characters")
    private String city;

    @Schema(example = "United Arab Emirates")
    @NotBlank(message = "Country is required")
    @Size(max = 50, message = "Country name cannot exceed 50 characters")
    private String country;
}