package org.example.movementservice.infraestructure.feign;

import org.example.movementservice.domain.dto.payment.PaymentRequest;
import org.example.movementservice.domain.dto.payment.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "mercadopago-service",
        url = "${mercadopago.api.url}",
        configuration = MercadoPagoFeignConfig.class
)
public interface MercadoPagoFeignClient {

    @PostMapping("/v1/payments")
    PaymentResponse createPayment(
            @RequestHeader("Authorization") String authorization,
            @RequestBody PaymentRequest request
    );

    @GetMapping("/v1/payments/{id}")
    PaymentResponse getPayment(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("id") String paymentId
    );
}