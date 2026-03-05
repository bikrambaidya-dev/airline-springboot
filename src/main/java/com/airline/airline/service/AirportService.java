package com.airline.airline.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.airline.airline.dto.request.AirportRequest;
import com.airline.airline.dto.response.AirportResponse;
import com.airline.airline.entity.Airport;
import com.airline.airline.repository.AirportRepository;

@Service
public class AirportService {

    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Transactional
    public AirportResponse addAirport(AirportRequest req) {
        String normalizedCode = normalizeAirportCode(req.getCode());
        if (airportRepository.existsByCodeIgnoreCase(normalizedCode)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Airport code already exists: " + normalizedCode);
        }

        Airport airport = new Airport();
        airport.setCode(normalizedCode);
        airport.setName(req.getName());
        airport.setCity(req.getCity());
        airport.setCountry(req.getCountry());
        airport.setStatus("ACTIVE");

        Airport saved = airportRepository.save(airport);

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private AirportResponse toResponse(Airport airport) {
        return new AirportResponse(
                airport.getId(),
                airport.getCode(),
                airport.getName(),
                airport.getCity(),
                airport.getCountry(),
                airport.getStatus());
    }

    private String normalizeAirportCode(String code) {
        if (code == null) {
            return null;
        }
        return code.trim().toUpperCase();
    }

    @Transactional
    public AirportResponse updateAirport(AirportRequest req) {
        if (req.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Airport id is required for update");
        }

        Airport airport = airportRepository.findById(req.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Airport not found"));

        String normalizedCode = normalizeAirportCode(req.getCode());
        if (airportRepository.existsByCodeIgnoreCaseAndIdNot(normalizedCode, req.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Airport code already exists: " + normalizedCode);
        }

        airport.setCode(normalizedCode);
        airport.setName(req.getName());
        airport.setCity(req.getCity());
        airport.setCountry(req.getCountry());

        Airport saved = airportRepository.save(airport);
        return toResponse(saved);
    }

    @Transactional
    public void deleteAirport(Long id) {
        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Airport id is required");
        }

        boolean exists = airportRepository.existsById(id);
        if (!exists) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Airport not found");
        }

        airportRepository.deleteById(id);
    }
}
