package org.example.mercadopagomock.service;

import org.example.mercadopagomock.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PaymentMockService {

    private static final Logger log = LoggerFactory.getLogger(PaymentMockService.class);

    private final Map<String, PaymentResponse> payments = new ConcurrentHashMap<>();

    // Configuración del mock
    private double rejectionRate = 0.1; // 10% de pagos rechazados por defecto
    private int responseDelayMs = 200; // Simular latencia de red
    private boolean forceApproval = false;
    private boolean forceRejection = false;

    /*Crear un nuevo pago*/
    public PaymentResponse createPayment(PaymentRequest request) {

        // Simular latencia de red
        simulateDelay();

        // Generar ID único
        String paymentId = "MOCK-PAY-" + UUID.randomUUID().toString().substring(0, 8);

        PaymentResponse response = new PaymentResponse();
        response.setId(paymentId);
        response.setTransactionAmount(request.getTransactionAmount());
        response.setDescription(request.getDescription());
        response.setPaymentMethodId(request.getPaymentMethodId());
        response.setPayerEmail(request.getPayerEmail());
        response.setDateCreated(LocalDateTime.now());
        response.setDateApproved(LocalDateTime.now());

        // Determinar estado del pago
        String status = determinePaymentStatus(request);
        response.setStatus(status);

        // Detalles según el estado
        if ("approved".equals(status)) {
            response.setStatusDetail("accredited");
            response.setAuthorizationCode("AUTH-" + UUID.randomUUID().toString().substring(0, 6));
        } else if ("rejected".equals(status)) {
            response.setStatusDetail(determineRejectionReason());
        } else if ("pending".equals(status)) {
            response.setStatusDetail("pending_review");
        }

        // Guardar el pago
        payments.put(paymentId, response);

        log.info("Payment created: {} - Status: {} - Amount: {}",
                paymentId, status, request.getTransactionAmount());

        return response;
    }

    /*Obtener un pago por Id*/
    public PaymentResponse getPayment(String paymentId) {
        return payments.get(paymentId);
    }

    /*Listar todos los pagos*/
    public List<PaymentResponse> listAllPayments() {
        return new ArrayList<>(payments.values());
    }

    /*Configurar comportamiento del mock*/
    public void configureMock(MockConfigRequest config) {
        if (config.getRejectionRate() != null) {
            this.rejectionRate = config.getRejectionRate();
        }
        if (config.getResponseDelayMs() != null) {
            this.responseDelayMs = config.getResponseDelayMs();
        }
        if (config.getForceApproval() != null) {
            this.forceApproval = config.getForceApproval();
        }
        if (config.getForceRejection() != null) {
            this.forceRejection = config.getForceRejection();
        }
        log.info("Mock configuration updated: rejection={}, delay={}, forceApproval={}, forceRejection={}",
                rejectionRate, responseDelayMs, forceApproval, forceRejection);
    }

    /*Resetear el mock*/
    public void reset() {
        payments.clear();
        rejectionRate = 0.1;
        responseDelayMs = 200;
        forceApproval = false;
        forceRejection = false;
        log.info("Mock data and configuration reset");
    }

    /*Determinar el estado del pago basado en configuración y lógica de negocio*/
    private String determinePaymentStatus(PaymentRequest request) {
        // Forzar aprobación
        if (forceApproval) {
            return "approved";
        }

        // Forzar rechazo
        if (forceRejection) {
            return "rejected";
        }

        // Validaciones de negocio
        if (request.getTransactionAmount() <= 0) {
            return "rejected";
        }

        // Montos muy altos van a revisión
        if (request.getTransactionAmount() > 100000) {
            return "pending";
        }

        // Simular rechazo aleatorio basado en tasa configurada
        if (Math.random() < rejectionRate) {
            return "rejected";
        }

        // Por defecto: aprobado
        return "approved";
    }

    /*Determinar razón de rechazo*/
    private String determineRejectionReason() {
        List<String> reasons = Arrays.asList(
                "insufficient_funds",
                "cc_rejected_call_for_authorize",
                "cc_rejected_bad_filled_security_code",
                "cc_rejected_card_disabled",
                "cc_rejected_insufficient_amount"
        );
        return reasons.get(new Random().nextInt(reasons.size()));
    }

    /*Simular delay de red*/
    private void simulateDelay() {
        if (responseDelayMs > 0) {
            try {
                Thread.sleep(responseDelayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}