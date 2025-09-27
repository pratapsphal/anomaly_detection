package com.techify.anomalydetection.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.techify.anomalydetection.service.AnomalyDetectorService;

@Component
public class AnomalyDetectionListener {

    private static final Logger logger = LoggerFactory.getLogger(AnomalyDetectionListener.class);

    private final AnomalyDetectorService anomalyDetectorService;

    public AnomalyDetectionListener(AnomalyDetectorService anomalyDetectorService) {
        this.anomalyDetectorService = anomalyDetectorService;
    }

    @ServiceActivator(inputChannel = "pubSubInputChannel")
    public void receiveMessage(Message<String> message) {
        String payload = message.getPayload();
        logger.info("Received Pub/Sub message for anomaly detection: {}", payload);
        try {
            anomalyDetectorService.processAnomalyDetection(payload);
            // Acknowledge message only after successful processing
        } catch (Exception ex) {
            logger.error("Exception during anomaly detection processing: ", ex);
            // Optionally do not acknowledge to allow retry or send to dead-letter queue
            throw ex; // rethrow to nack if using auto-ack
        }
    }
}
