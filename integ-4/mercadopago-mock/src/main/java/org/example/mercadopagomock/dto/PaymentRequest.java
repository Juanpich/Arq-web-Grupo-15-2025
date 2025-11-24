package org.example.mercadopagomock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Double transactionAmount;
    private String description;
    private String paymentMethodId;
    private String payerEmail;
    private String token;
    private Integer installments;
}