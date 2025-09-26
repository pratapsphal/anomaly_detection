package com.techify.anomalydetection.dto;

public class AnomalyData {
    private String payload;
    private Long timestamp;

    // Default constructor is required by Firestore
    public AnomalyData() {}

    public AnomalyData(String payload) {
        this.payload = payload;
        this.timestamp = System.currentTimeMillis();
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}