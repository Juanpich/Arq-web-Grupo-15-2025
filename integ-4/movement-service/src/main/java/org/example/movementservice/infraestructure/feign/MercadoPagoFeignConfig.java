package org.example.movementservice.infraestructure.feign;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoFeignConfig {

    @Value("${mercadopago.access.token}")
    private String accessToken;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            if (!requestTemplate.headers().containsKey("Authorization")) {
                requestTemplate.header("Authorization", "Bearer " + accessToken);
            }
        };
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
