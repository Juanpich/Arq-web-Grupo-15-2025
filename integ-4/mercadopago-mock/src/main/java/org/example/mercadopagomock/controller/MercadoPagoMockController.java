package org.example.mercadopagomock.controller;

import org.example.mercadopagomock.dto.*;
import org.example.mercadopagomock.service.PaymentMockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class MercadoPagoMockController {

    private final PaymentMockService paymentService;

    public MercadoPagoMockController(PaymentMockService paymentService) {
        this.paymentService = paymentService;
    }

    /* Crear un pago (simula el endpoint de MercadoPago)
     POST /v1/payments */
    @PostMapping("/payments")
    public ResponseEntity<?> createPayment(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody PaymentRequest request) {

        // Validar token de autorización
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            ErrorResponse error = new ErrorResponse(
                    "unauthorized",
                    "Missing or invalid authorization token",
                    401
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        PaymentResponse response = paymentService.createPayment(request);

        if ("rejected".equals(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /* Consultar pago por id */
    @GetMapping("/payments/{id}")
    public ResponseEntity<?> getPayment(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable String id) {

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            ErrorResponse error = new ErrorResponse(
                    "unauthorized",
                    "Missing or invalid authorization token",
                    401
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        PaymentResponse payment = paymentService.getPayment(id);

        if (payment == null) {
            ErrorResponse error = new ErrorResponse(
                    "not_found",
                    "Payment not found",
                    404
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        return ResponseEntity.ok(payment);
    }

    /*Listar todos los pagos (para testing) GET /v1/payments*/
    @GetMapping("/payments")
    public ResponseEntity<?> listPayments(
            @RequestHeader(value = "Authorization", required = false) String authorization) {

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            ErrorResponse error = new ErrorResponse(
                    "unauthorized",
                    "Missing or invalid authorization token",
                    401
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        return ResponseEntity.ok(paymentService.listAllPayments());
    }

    /*Webhook para notificaciones (simula callbacks de MercadoPago) POST /v1/webhooks*/
    @PostMapping("/webhooks")
    public ResponseEntity<WebhookResponse> receiveWebhook(@RequestBody WebhookNotification notification) {
        WebhookResponse response = new WebhookResponse();
        response.setReceived(true);
        response.setMessage("Webhook processed successfully");
        return ResponseEntity.ok(response);
    }

    /* Endpoint para configurar comportamiento del mock (solo para testing)*/
    @PostMapping("/mock/config")
    public ResponseEntity<MockConfigResponse> configureMock(@RequestBody MockConfigRequest config) {
        paymentService.configureMock(config);
        MockConfigResponse response = new MockConfigResponse();
        response.setMessage("Mock configuration updated");
        response.setConfig(config);
        return ResponseEntity.ok(response);
    }

    /*Resetear el mock (limpiar datos)*/
    @PostMapping("/mock/reset")
    public ResponseEntity<String> resetMock() {
        paymentService.reset();
        return ResponseEntity.ok("Mock data cleared");
    }
}