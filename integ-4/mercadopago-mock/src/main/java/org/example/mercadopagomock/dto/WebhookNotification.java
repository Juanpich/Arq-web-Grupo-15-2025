package org.example.mercadopagomock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebhookNotification {
    private String action;
    private String apiVersion;
    private WebhookData data;
}