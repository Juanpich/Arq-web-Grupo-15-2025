package org.example.movementservice.application.services;

import org.example.movementservice.domain.dto.payment.PaymentRequest;
import org.example.movementservice.domain.dto.payment.PaymentResponse;
import org.example.movementservice.domain.exceptions.PaymentProcessingException;
import org.example.movementservice.infraestructure.feign.MercadoPagoFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final MercadoPagoFeignClient mercadoPagoClient;

    @Value("${mercadopago.access.token}")
    private String accessToken;

    public PaymentService(MercadoPagoFeignClient mercadoPagoClient) {
        this.mercadoPagoClient = mercadoPagoClient;
    }

    public PaymentResponse processPayment(PaymentRequest request) {
        try {
            log.info("Processing payment: amount={}, email={}",
                    request.getTransactionAmount(),
                    request.getPayerEmail());

            String authHeader = "Bearer " + accessToken;
            PaymentResponse response = mercadoPagoClient.createPayment(authHeader, request);

            log.info("Payment processed: id={}, status={}, detail={}",
                    response.getId(),
                    response.getStatus(),
                    response.getStatusDetail());

            return response;

        } catch (Exception e) {
            log.error("Error processing payment: {}", e.getMessage(), e);
            throw new PaymentProcessingException("Error al procesar el pago: " + e.getMessage());
        }
    }

    public PaymentResponse getPaymentStatus(String paymentId) {
        try {
            String authHeader = "Bearer " + accessToken;
            return mercadoPagoClient.getPayment(authHeader, paymentId);
        } catch (Exception e) {
            log.error("Error getting payment status: {}", e.getMessage(), e);
            throw new PaymentProcessingException("Error al consultar el pago: " + e.getMessage());
        }
    }
}