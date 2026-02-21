package com.airline.airline.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.airline.airline.dto.request.RouteRequest;
import com.airline.airline.entity.Airport;
import com.airline.airline.entity.Route;
import com.airline.airline.repository.AirportRepository;
import com.airline.airline.repository.RouteRepository;

@Service
public class RouteService {
    
    private final RouteRepository routeRepository;
    private final AirportRepository airportRepository;
    
    public RouteService(RouteRepository routeRepository, AirportRepository airportRepository) {
        this.routeRepository = routeRepository;
        this.airportRepository = airportRepository;
    }
    
    public Route createRoute(RouteRequest request) {
        Airport sourceAirport = airportRepository
                .findById(request.getSourceAirportId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Source airport not found"));

        Airport destinationAirport = airportRepository
                .findById(request.getDestinationAirportId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Destination airport not found"));

        Route route = new Route();
        route.setSourceAirport(sourceAirport);
        route.setDestinationAirport(destinationAirport);
        route.setDistanceKm(request.getDistanceKm());

        return routeRepository.save(route);
    }
    
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }
    
    public Route getRouteById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Route not found"));
    }
    
    public Route updateRoute(Long id, RouteRequest request) {
        Route existingRoute = getRouteById(id);
        
        Airport sourceAirport = airportRepository
                .findById(request.getSourceAirportId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Source airport not found"));

        Airport destinationAirport = airportRepository
                .findById(request.getDestinationAirportId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Destination airport not found"));

        existingRoute.setSourceAirport(sourceAirport);
        existingRoute.setDestinationAirport(destinationAirport);
        existingRoute.setDistanceKm(request.getDistanceKm());

        return routeRepository.save(existingRoute);
    }
    
    public void deleteRoute(Long id) {
        if (!routeRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Route not found");
        }
        routeRepository.deleteById(id);
    }
}
