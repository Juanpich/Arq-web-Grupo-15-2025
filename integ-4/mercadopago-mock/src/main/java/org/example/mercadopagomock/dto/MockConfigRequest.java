package org.example.mercadopagomock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MockConfigRequest {
    private Double rejectionRate;
    private Integer responseDelayMs;
    private Boolean forceApproval;
    private Boolean forceRejection;
}