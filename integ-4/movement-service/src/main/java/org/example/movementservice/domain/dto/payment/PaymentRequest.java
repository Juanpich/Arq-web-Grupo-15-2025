package org.example.movementservice.domain.dto.payment;

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

    public static PaymentRequest fromMovement(Long amount, String userEmail) {
        PaymentRequest request = new PaymentRequest();
        request.setTransactionAmount(amount.doubleValue());
        request.setDescription("Carga de saldo en cuenta");
        request.setPaymentMethodId("account_money");
        request.setPayerEmail(userEmail);
        request.setInstallments(1);
        return request;
    }
}