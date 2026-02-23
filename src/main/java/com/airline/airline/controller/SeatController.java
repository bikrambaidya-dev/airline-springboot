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
import com.airline.airline.exception.EntityNotFoundException;
import com.airline.airline.service.SeatService;
import com.airline.airline.util.ResponseUtil;

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
        return ResponseUtil.created(seats, "Seats created successfully");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Seat>>> getAllSeats() {
        List<Seat> seats = seatService.getAllSeats();
        return ResponseUtil.ok(seats, "Seats retrieved successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Seat>> getSeatById(@PathVariable Long id) {
        Seat seat = seatService.getSeatById(id);
        if (seat == null) {
            throw EntityNotFoundException.forId("Seat", id);
        }
        return ResponseUtil.ok(seat, "Seat retrieved successfully");
    }

    @GetMapping("/aircraft/{aircraftId}")
    public ResponseEntity<ApiResponse<List<Seat>>> getSeatsByAircraft(@PathVariable Long aircraftId) {
        List<Seat> seats = seatService.getSeatsByAircraft(aircraftId);
        return ResponseUtil.ok(seats, "Seats retrieved successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return ResponseUtil.noContent("Seat deleted successfully");
    }

}
