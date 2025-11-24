package org.example.mercadopagomock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String id;
    private String status;
    private String statusDetail;
    private Double transactionAmount;
    private String description;
    private String paymentMethodId;
    private String payerEmail;
    private String authorizationCode;
    private LocalDateTime dateCreated;
    private LocalDateTime dateApproved;
}