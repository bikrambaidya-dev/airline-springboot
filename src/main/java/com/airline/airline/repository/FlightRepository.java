package com.airline.airline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airline.airline.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>{
    
    boolean existsByFlightNumber(String flightNumber);
}
