package com.airline.airline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private Long id;
    private String transactionId;
    private Double amount;
    private String currency; // USD, EUR, GBP, etc.
    private String status; // PENDING, COMPLETED, FAILED, REFUNDED
    private String paymentMethod; // CREDIT_CARD, DEBIT_CARD, PAYPAL, BANK_TRANSFER
    private String paymentProvider; // VISA, MASTERCARD, AMEX, etc.
    private LocalDateTime paymentDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String lastFourDigits;
    
    // Example response:
    // {
    //   "id": 1,
    //   "transactionId": "TXN202403150001",
    //   "amount": 599.98,
    //   "currency": "USD",
    //   "status": "COMPLETED",
    //   "paymentMethod": "CREDIT_CARD",
    //   "paymentProvider": "VISA",
    //   "paymentDate": "2024-03-10T14:35:00",
    //   "createdAt": "2024-03-10T14:30:00",
    //   "updatedAt": "2024-03-10T14:35:00",
    //   "lastFourDigits": "1234"
    // }
}
