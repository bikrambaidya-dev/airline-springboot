package com.airline.airline.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.airline.airline.dto.request.FlightRequest;
import com.airline.airline.entity.Aircraft;
import com.airline.airline.entity.Flight;
import com.airline.airline.entity.Route;
import com.airline.airline.repository.AircraftRepository;
import com.airline.airline.repository.FlightRepository;
import com.airline.airline.repository.RouteRepository;

@Service
public class FlightService {
    
    private final FlightRepository flightRepository;
    private final RouteRepository routeRepository;
    private final AircraftRepository aircraftRepository;
    
    public FlightService(FlightRepository flightRepository, RouteRepository routeRepository, 
            AircraftRepository aircraftRepository) {
        this.flightRepository = flightRepository;
        this.routeRepository = routeRepository;
        this.aircraftRepository = aircraftRepository;
    }
    
    public Flight createFlight(FlightRequest request) {
        if (flightRepository.existsByFlightNumber(request.getFlightNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Flight number already exists: " + request.getFlightNumber());
        }

        Route route = routeRepository.findById(request.getRouteId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Route not found"));

        Aircraft aircraft = aircraftRepository.findById(request.getAircraftId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aircraft not found"));

        Flight flight = new Flight();
        flight.setFlightNumber(request.getFlightNumber());
        flight.setRoute(route);
        flight.setAircraft(aircraft);
        flight.setDepartureTime(request.getDepartureTime());
        flight.setArrivalTime(request.getArrivalTime());
        flight.setBasePrice(request.getBasePrice());
        flight.setStatus("ACTIVE");

        return flightRepository.save(flight);
    }
    
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
    
    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Flight not found"));
    }
    
    public Flight updateFlight(Long id, FlightRequest request) {
        Flight existingFlight = getFlightById(id);
        
        if (!existingFlight.getFlightNumber().equals(request.getFlightNumber()) 
                && flightRepository.existsByFlightNumber(request.getFlightNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Flight number already exists: " + request.getFlightNumber());
        }

        Route route = routeRepository.findById(request.getRouteId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Route not found"));

        Aircraft aircraft = aircraftRepository.findById(request.getAircraftId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aircraft not found"));

        existingFlight.setFlightNumber(request.getFlightNumber());
        existingFlight.setRoute(route);
        existingFlight.setAircraft(aircraft);
        existingFlight.setDepartureTime(request.getDepartureTime());
        existingFlight.setArrivalTime(request.getArrivalTime());
        existingFlight.setBasePrice(request.getBasePrice());

        return flightRepository.save(existingFlight);
    }
    
    public void deleteFlight(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Flight not found");
        }
        flightRepository.deleteById(id);
    }
}
