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

import com.airline.airline.dto.request.AircraftRequest;
import com.airline.airline.dto.response.ApiResponse;
import com.airline.airline.entity.Aircraft;
import com.airline.airline.exception.EntityNotFoundException;
import com.airline.airline.service.AircraftService;
import com.airline.airline.util.ResponseUtil;

@RestController
@RequestMapping("/aircraft")    
public class AircraftController {
    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Aircraft>> createAircraft(@Valid @RequestBody AircraftRequest request) {
        Aircraft created = aircraftService.createAircraft(request);
        return ResponseUtil.created(created, "Aircraft created successfully");
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<Aircraft>>> getAllAircrafts() {
        List<Aircraft> aircrafts = aircraftService.getAllAircrafts();
        return ResponseUtil.ok(aircrafts, "Aircrafts retrieved successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Aircraft>> getAircraft(@PathVariable Long id) {
        Aircraft aircraft = aircraftService.getAircraft(id);
        if (aircraft == null) {
            throw EntityNotFoundException.forId("Aircraft", id);
        }
        return ResponseUtil.ok(aircraft, "Aircraft retrieved successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Aircraft>> updateAircraft(@PathVariable Long id, @Valid @RequestBody AircraftRequest request) {
        Aircraft updated = aircraftService.updateAircraft(id, request);
        if (updated == null) {
            throw EntityNotFoundException.forId("Aircraft", id);
        }
        return ResponseUtil.ok(updated, "Aircraft updated successfully");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAircraft(@PathVariable Long id) {
        aircraftService.deleteAircraft(id);
        return ResponseUtil.noContent("Aircraft deleted successfully");
    }
    
}
