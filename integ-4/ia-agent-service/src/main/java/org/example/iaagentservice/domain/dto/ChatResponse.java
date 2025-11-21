package org.example.iaagentservice.domain.dto;

public class ChatResponse {
    private String response;
    private long timestamp;

    public ChatResponse() {
        this.timestamp = System.currentTimeMillis();
    }

    public ChatResponse(String response) {
        this.response = response;
        this.timestamp = System.currentTimeMillis();
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public long getTimestamp() {
        return timestamp;
    }
}

