package com.airline.airline.dto.example;

/**
 * Comprehensive API Examples Documentation
 * This class contains real-world examples for all API endpoints
 */
public class ApiExamples {

    /**
     * Aircraft API Examples
     */
    public static final class AircraftExamples {
        public static final String CREATE_AIRCRAFT_REQUEST = """
            {
              "model": "Boeing 737-800",
              "totalSeats": 189
            }
            """;

        public static final String CREATE_AIRCRAFT_RESPONSE = """
            {
              "id": 1,
              "model": "Boeing 737-800",
              "totalSeats": 189,
              "createdAt": "2024-03-15T10:30:00",
              "updatedAt": "2024-03-15T10:30:00",
              "availableSeats": 189
            }
            """;
    }

    /**
     * Airport API Examples
     */
    public static final class AirportExamples {
        public static final String CREATE_AIRPORT_REQUEST = """
            {
              "code": "JFK",
              "name": "John F. Kennedy International Airport",
              "city": "New York",
              "country": "United States"
            }
            """;

        public static final String CREATE_AIRPORT_RESPONSE = """
            {
              "id": 1,
              "code": "JFK",
              "name": "John F. Kennedy International Airport",
              "city": "New York",
              "country": "United States",
              "createdAt": "2024-03-15T10:30:00",
              "updatedAt": "2024-03-15T10:30:00"
            }
            """;
    }

    /**
     * Route API Examples
     */
    public static final class RouteExamples {
        public static final String CREATE_ROUTE_REQUEST = """
            {
              "sourceAirportId": 1,
              "destinationAirportId": 2,
              "distanceKm": 5567
            }
            """;

        public static final String CREATE_ROUTE_RESPONSE = """
            {
              "id": 1,
              "sourceAirport": {
                "id": 1,
                "code": "JFK",
                "name": "John F. Kennedy International Airport",
                "city": "New York",
                "country": "United States"
              },
              "destinationAirport": {
                "id": 2,
                "code": "LHR",
                "name": "London Heathrow Airport",
                "city": "London",
                "country": "United Kingdom"
              },
              "distanceKm": 5567,
              "createdAt": "2024-03-15T10:30:00",
              "updatedAt": "2024-03-15T10:30:00"
            }
            """;
    }

    /**
     * Seat API Examples
     */
    public static final class SeatExamples {
        public static final String CREATE_SEATS_REQUEST = """
            {
              "aircraftId": 1,
              "seatNumbers": ["1A", "1B", "1C", "1D", "2A", "2B", "2C", "2D"]
            }
            """;

        public static final String CREATE_SEATS_RESPONSE = """
            [
              {
                "id": 1,
                "seatNumber": "1A",
                "aircraft": {
                  "id": 1,
                  "model": "Boeing 737-800",
                  "totalSeats": 189
                },
                "status": "AVAILABLE",
                "createdAt": "2024-03-15T10:30:00",
                "updatedAt": "2024-03-15T10:30:00"
              },
              {
                "id": 2,
                "seatNumber": "1B",
                "aircraft": {
                  "id": 1,
                  "model": "Boeing 737-800",
                  "totalSeats": 189
                },
                "status": "AVAILABLE",
                "createdAt": "2024-03-15T10:30:00",
                "updatedAt": "2024-03-15T10:30:00"
              }
            ]
            """;
    }

    /**
     * Flight API Examples
     */
    public static final class FlightExamples {
        public static final String CREATE_FLIGHT_REQUEST = """
            {
              "flightNumber": "AA123",
              "routeId": 1,
              "aircraftId": 1,
              "departureTime": "2024-03-15T10:30:00",
              "arrivalTime": "2024-03-15T14:45:00",
              "basePrice": 299.99
            }
            """;

        public static final String CREATE_FLIGHT_RESPONSE = """
            {
              "id": 1,
              "flightNumber": "AA123",
              "route": {
                "id": 1,
                "sourceAirport": {
                  "id": 1,
                  "code": "JFK",
                  "name": "John F. Kennedy International Airport",
                  "city": "New York",
                  "country": "United States"
                },
                "destinationAirport": {
                  "id": 2,
                  "code": "LHR",
                  "name": "London Heathrow Airport",
                  "city": "London",
                  "country": "United Kingdom"
                },
                "distanceKm": 5567
              },
              "aircraft": {
                "id": 1,
                "model": "Boeing 737-800",
                "totalSeats": 189
              },
              "departureTime": "2024-03-15T10:30:00",
              "arrivalTime": "2024-03-15T14:45:00",
              "basePrice": 299.99,
              "status": "ACTIVE",
              "createdAt": "2024-03-15T10:30:00",
              "updatedAt": "2024-03-15T10:30:00",
              "availableSeats": 189,
              "seats": []
            }
            """;
    }

    /**
     * User Authentication Examples
     */
    public static final class AuthExamples {
        public static final String REGISTER_REQUEST = """
            {
              "username": "john.doe",
              "email": "john.doe@example.com",
              "password": "SecurePass123!",
              "firstName": "John",
              "lastName": "Doe"
            }
            """;

        public static final String LOGIN_REQUEST = """
            {
              "username": "john.doe",
              "password": "SecurePass123!"
            }
            """;

        public static final String LOGIN_RESPONSE = """
            {
              "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
              "tokenType": "Bearer",
              "expiresIn": 3600,
              "user": {
                "id": 1,
                "username": "john.doe",
                "email": "john.doe@example.com",
                "firstName": "John",
                "lastName": "Doe",
                "role": "USER",
                "status": "ACTIVE"
              }
            }
            """;
    }

    /**
     * Error Response Examples
     */
    public static final class ErrorExamples {
        public static final String VALIDATION_ERROR = """
            {
              "status": 400,
              "error": "Bad Request",
              "message": "Validation failed",
              "timestamp": "2024-03-15T10:30:00",
              "path": "/api/aircraft",
              "errors": [
                {
                  "field": "model",
                  "message": "Aircraft model is required"
                },
                {
                  "field": "totalSeats",
                  "message": "Aircraft must have at least 1 seat"
                }
              ]
            }
            """;

        public static final String NOT_FOUND_ERROR = """
            {
              "status": 404,
              "error": "Not Found",
              "message": "Aircraft not found with id: 999",
              "timestamp": "2024-03-15T10:30:00",
              "path": "/api/aircraft/999"
            }
            """;

        public static final String CONFLICT_ERROR = """
            {
              "status": 409,
              "error": "Conflict",
              "message": "Flight number already exists: AA123",
              "timestamp": "2024-03-15T10:30:00",
              "path": "/api/flight"
            }
            """;
    }
}
