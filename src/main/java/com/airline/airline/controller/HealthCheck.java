package com.airline.airline.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @GetMapping("/")
    public String home() {
        return "Airline Service is Running";
    }
    
    @GetMapping("/health")
    public String healthCheck() {
        return "health ok";
    }
    
}
