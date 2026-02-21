package com.airline.airline.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.airline.airline.dto.request.SeatRequest;
import com.airline.airline.entity.Aircraft;
import com.airline.airline.entity.Seat;
import com.airline.airline.repository.AircraftRepository;
import com.airline.airline.repository.SeatRepository;

@Service
public class SeatService {
    
    private final SeatRepository seatRepository;
    private final AircraftRepository aircraftRepository;
    
    public SeatService(SeatRepository seatRepository, AircraftRepository aircraftRepository) {
        this.seatRepository = seatRepository;
        this.aircraftRepository = aircraftRepository;
    }
    
    public List<Seat> createSeats(SeatRequest request) {
        Aircraft aircraft = aircraftRepository.findById(request.getAircraftId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aircraft not found"));

        List<Seat> seats = new ArrayList<>();

        for (String seatNo : request.getSeatNumbers()) {
            Seat seat = new Seat();
            seat.setAircraft(aircraft);
            seat.setSeatNumber(seatNo);
            seats.add(seatRepository.save(seat));
        }

        return seats;
    }
    
    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }
    
    public Seat getSeatById(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Seat not found"));
    }
    
    public List<Seat> getSeatsByAircraft(Long aircraftId) {
        aircraftRepository.findById(aircraftId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aircraft not found"));
        
        return seatRepository.findByAircraft_Id(aircraftId);
    }
    
    public void deleteSeat(Long id) {
        if (!seatRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Seat not found");
        }
        seatRepository.deleteById(id);
    }
}
