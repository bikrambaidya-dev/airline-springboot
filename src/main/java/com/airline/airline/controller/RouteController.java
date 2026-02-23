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
import com.airline.airline.exception.EntityNotFoundException;
import com.airline.airline.service.RouteService;
import com.airline.airline.util.ResponseUtil;

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
        return ResponseUtil.created(created, "Route created successfully");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Route>>> getAllRoutes() {
        List<Route> routes = routeService.getAllRoutes();
        return ResponseUtil.ok(routes, "Routes retrieved successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Route>> getRouteById(@PathVariable Long id) {
        Route route = routeService.getRouteById(id);
        if (route == null) {
            throw EntityNotFoundException.forId("Route", id);
        }
        return ResponseUtil.ok(route, "Route retrieved successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Route>> updateRoute(@PathVariable Long id, @Valid @RequestBody RouteRequest request) {
        Route updated = routeService.updateRoute(id, request);
        if (updated == null) {
            throw EntityNotFoundException.forId("Route", id);
        }
        return ResponseUtil.ok(updated, "Route updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseUtil.noContent("Route deleted successfully");
    }

}
