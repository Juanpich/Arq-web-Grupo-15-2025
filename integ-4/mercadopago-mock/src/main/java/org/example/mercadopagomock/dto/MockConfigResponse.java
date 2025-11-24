package org.example.mercadopagomock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MockConfigResponse {
    private String message;
    private MockConfigRequest config;
}