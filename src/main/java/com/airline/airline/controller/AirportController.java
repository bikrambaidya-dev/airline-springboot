package com.airline.airline.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.airline.airline.dto.request.AirportRequest;
import com.airline.airline.dto.response.AirportResponse;
import com.airline.airline.dto.response.ApiResponse;
import com.airline.airline.service.AirportService;
import com.airline.airline.util.ResponseUtil;

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
        return ResponseUtil.created(response, "Airport added successfully");
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<AirportResponse>>> getAllAirports() {
        List<AirportResponse> airports = airportService.getAllAirports();
        return ResponseUtil.ok(airports, "Airports retrieved successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AirportResponse>> updateAirport(
            @PathVariable Long id,
            @Valid @RequestBody AirportRequest req) {
        req.setId(id);
        AirportResponse response = airportService.updateAirport(req);
        return ResponseUtil.ok(response, "Airport updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAirport(@PathVariable Long id) {
        airportService.deleteAirport(id);
        return ResponseUtil.ok(null, "Airport deleted successfully");
    }
}
