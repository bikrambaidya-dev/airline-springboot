package com.airline.airline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airline.airline.entity.FlightSeat;

@Repository
public interface FlightSeatRepository extends JpaRepository<FlightSeat, Long>{
    
}
