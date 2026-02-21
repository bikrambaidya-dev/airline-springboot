package com.airline.airline.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airline.airline.dto.request.AircraftRequest;
import com.airline.airline.entity.Aircraft;
import com.airline.airline.service.AircraftService;

@RestController
@RequestMapping("/aircraft")    
public class AircraftController {
    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @PostMapping()
    public Aircraft createAircraft(@Valid @RequestBody AircraftRequest request) {
        return aircraftService.createAircraft(request);
    }

    @GetMapping()
    public List<Aircraft> getAllAircrafts() {
        return aircraftService.getAllAircrafts();
    }

    @GetMapping("/{id}")
    public Aircraft getAircraft(@PathVariable Long id) {
        return aircraftService.getAircraft(id);
    }

    @PutMapping("/{id}")
    public Aircraft updateAircraft(@PathVariable Long id, @Valid @RequestBody AircraftRequest request) {
        return aircraftService.updateAircraft(id, request);
    }
    
    @DeleteMapping("/{id}")
    public void deleteAircraft(@PathVariable Long id) {
        aircraftService.deleteAircraft(id);
    }
    
}
