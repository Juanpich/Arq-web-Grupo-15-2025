package org.example.iaagentservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatResponse<T> {
    private boolean ok;
    private String mensaje;
    private T datos;
}

