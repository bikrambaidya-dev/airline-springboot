package com.airline.airline.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airline.airline.dto.request.FlightRequest;
import com.airline.airline.dto.response.ApiResponse;
import com.airline.airline.entity.Flight;
import com.airline.airline.service.FlightService;

@RestController
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Flight>> createFlight(@Valid @RequestBody FlightRequest request) {
        Flight created = flightService.createFlight(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Flight created successfully", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Flight>>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return ResponseEntity.ok(new ApiResponse<>(true, "Flights retrieved successfully", flights));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Flight>> getFlightById(@PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Flight retrieved successfully", flight));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Flight>> updateFlight(@PathVariable Long id, @Valid @RequestBody FlightRequest request) {
        Flight updated = flightService.updateFlight(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Flight updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Flight deleted successfully", null));
    }

}
