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
import com.airline.airline.exception.EntityNotFoundException;
import com.airline.airline.service.FlightService;
import com.airline.airline.util.ResponseUtil;

/**
 * Flight Controller with standardized response handling
 * All endpoints return ApiResponse<T> with proper HTTP status codes
 */
@RestController
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    /**
     * POST - Create a new flight
     * Returns 201 Created with the created flight data
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Flight>> createFlight(@Valid @RequestBody FlightRequest request) {
        Flight created = flightService.createFlight(request);
        return ResponseUtil.created(created, "Flight created successfully");
    }

    /**
     * GET - Retrieve all flights
     * Returns 200 OK with list of flights
     * Exceptions are handled globally by GlobalExceptionHandler
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Flight>>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return ResponseUtil.ok(flights, "Flights retrieved successfully");
    }

    /**
     * GET - Retrieve a specific flight by ID
     * Returns 200 OK if found
     * Throws EntityNotFoundException if not found (handled by GlobalExceptionHandler -> 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Flight>> getFlightById(@PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        if (flight == null) {
            throw EntityNotFoundException.forId("Flight", id);
        }
        return ResponseUtil.ok(flight, "Flight retrieved successfully");
    }

    /**
     * PUT - Update an existing flight
     * Returns 200 OK with updated flight data
     * Throws EntityNotFoundException if flight not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Flight>> updateFlight(
            @PathVariable Long id, 
            @Valid @RequestBody FlightRequest request) {
        Flight updated = flightService.updateFlight(id, request);
        if (updated == null) {
            throw EntityNotFoundException.forId("Flight", id);
        }
        return ResponseUtil.ok(updated, "Flight updated successfully");
    }

    /**
     * DELETE - Delete a flight
     * Returns 204 No Content on success
     * Throws EntityNotFoundException if flight not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseUtil.noContent("Flight deleted successfully");
    }
}
