package com.airline.airline.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airline.airline.dto.request.AirportRequest;
import com.airline.airline.dto.response.AirportResponse;
import com.airline.airline.dto.response.ApiResponse;
import com.airline.airline.service.AirportService;

@RestController
@RequestMapping("/airport")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<AirportResponse>> addAirport(
            @Valid @RequestBody AirportRequest req) {

        AirportResponse response = airportService.addAirport(req);

        ApiResponse<AirportResponse> apiResponse =
                new ApiResponse<>(true, "Airport added successfully", response);

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<AirportResponse>>> getAllAirports() {
        List<AirportResponse> airports = airportService.getAllAirports();
        ApiResponse<List<AirportResponse>> apiResponse = new ApiResponse<>(true, "Airports retrieved successfully", airports);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<AirportResponse>> updateAirport(@Valid @RequestBody AirportRequest req){
        AirportResponse response = airportService.updateAirport(req);
        ApiResponse<AirportResponse> apiResponse = new ApiResponse<>(true, "Airport updated successfully", response);
        return ResponseEntity.ok(apiResponse);
    }
}
