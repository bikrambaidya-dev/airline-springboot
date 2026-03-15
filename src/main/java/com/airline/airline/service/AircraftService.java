package com.airline.airline.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.airline.airline.dto.request.AircraftRequest;
import com.airline.airline.entity.Aircraft;
import com.airline.airline.repository.AircraftRepository;

@Service
public class AircraftService {
    private final AircraftRepository aircraftRepository;

    public AircraftService(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    @Transactional
    public Aircraft createAircraft(AircraftRequest req) {
        Aircraft aircraft = new Aircraft();
        aircraft.setModel(req.getModel());
        aircraft.setTotalSeats(req.getTotalSeats());
        aircraft.setManufacturer(req.getManufacturer());
        aircraft.setRange(req.getRange());
        return aircraftRepository.save(aircraft);
    }

    @Transactional(readOnly = true)
    public List<Aircraft> getAllAircrafts() {
        return aircraftRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Aircraft getAircraft(Long id) {
        return aircraftRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aircraft not found"));
    }

    @Transactional
    public Aircraft updateAircraft(Long id, AircraftRequest request) {
        Aircraft aircraft = getAircraft(id);
        aircraft.setModel(request.getModel());
        aircraft.setTotalSeats(request.getTotalSeats());
        aircraft.setManufacturer(request.getManufacturer());
        aircraft.setRange(request.getRange());

        return aircraftRepository.save(aircraft);
    }

    @Transactional
    public void deleteAircraft(Long id) {
        getAircraft(id);
        aircraftRepository.deleteById(id);
    }
}