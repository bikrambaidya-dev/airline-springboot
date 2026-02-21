package com.airline.airline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long id;
    private String bookingReference;
    private UserResponse user;
    private FlightResponse flight;
    private List<SeatResponse> seats;
    private Double totalPrice;
    private String status; // PENDING, CONFIRMED, CANCELLED, COMPLETED
    private LocalDateTime bookingDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private PaymentResponse payment;
    
    // Example response:
    // {
    //   "id": 1,
    //   "bookingReference": "BK202403150001",
    //   "user": {
    //     "id": 1,
    //     "username": "john.doe",
    //     "email": "john.doe@example.com",
    //     "firstName": "John",
    //     "lastName": "Doe"
    //   },
    //   "flight": {
    //     "id": 1,
    //     "flightNumber": "AA123",
    //     "departureTime": "2024-03-15T10:30:00",
    //     "arrivalTime": "2024-03-15T14:45:00",
    //     "basePrice": 299.99
    //   },
    //   "seats": [
    //     {
    //       "id": 1,
    //       "seatNumber": "1A",
    //       "status": "BOOKED"
    //     },
    //     {
    //       "id": 2,
    //       "seatNumber": "1B",
    //       "status": "BOOKED"
    //     }
    //   ],
    //   "totalPrice": 599.98,
    //   "status": "CONFIRMED",
    //   "bookingDate": "2024-03-10T14:30:00",
    //   "createdAt": "2024-03-10T14:30:00",
    //   "updatedAt": "2024-03-10T14:30:00",
    //   "payment": {
    //     "id": 1,
    //     "amount": 599.98,
    //     "status": "COMPLETED",
    //     "paymentMethod": "CREDIT_CARD"
    //   }
    // }
}
