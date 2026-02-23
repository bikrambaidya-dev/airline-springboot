package com.airline.airline.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airline.airline.dto.request.SeatRequest;
import com.airline.airline.dto.response.ApiResponse;
import com.airline.airline.entity.Seat;
import com.airline.airline.service.SeatService;

@RestController
@RequestMapping("/seat")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<List<Seat>>> createSeats(@Valid @RequestBody SeatRequest request) {
        List<Seat> seats = seatService.createSeats(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Seats created successfully", seats));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Seat>>> getAllSeats() {
        List<Seat> seats = seatService.getAllSeats();
        return ResponseEntity.ok(new ApiResponse<>(true, "Seats retrieved successfully", seats));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Seat>> getSeatById(@PathVariable Long id) {
        Seat seat = seatService.getSeatById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Seat retrieved successfully", seat));
    }

    @GetMapping("/aircraft/{aircraftId}")
    public ResponseEntity<ApiResponse<List<Seat>>> getSeatsByAircraft(@PathVariable Long aircraftId) {
        List<Seat> seats = seatService.getSeatsByAircraft(aircraftId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Seats retrieved successfully", seats));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Seat deleted successfully", null));
    }

}
