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

import com.airline.airline.dto.request.RouteRequest;
import com.airline.airline.dto.response.ApiResponse;
import com.airline.airline.entity.Route;
import com.airline.airline.service.RouteService;

@RestController
@RequestMapping("/route")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Route>> createRoute(@Valid @RequestBody RouteRequest request) {
        Route created = routeService.createRoute(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Route created successfully", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Route>>> getAllRoutes() {
        List<Route> routes = routeService.getAllRoutes();
        return ResponseEntity.ok(new ApiResponse<>(true, "Routes retrieved successfully", routes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Route>> getRouteById(@PathVariable Long id) {
        Route route = routeService.getRouteById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Route retrieved successfully", route));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Route>> updateRoute(@PathVariable Long id, @Valid @RequestBody RouteRequest request) {
        Route updated = routeService.updateRoute(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Route updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Route deleted successfully", null));
    }

}
