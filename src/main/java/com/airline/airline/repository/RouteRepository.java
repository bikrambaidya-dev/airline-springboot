package com.airline.airline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airline.airline.entity.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    
}
