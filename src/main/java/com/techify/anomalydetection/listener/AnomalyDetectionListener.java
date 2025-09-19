
package com.techify.anomalydetection.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.techify.anomalydetection.service.AnomalyDetector;

// This component acts as the Pub/Sub message listener.
@Component
public class AnomalyDetectionListener {

    private static final Logger logger = LoggerFactory.getLogger(AnomalyDetectionListener.class);

    private final AnomalyDetector anomalyDetector;

    @Autowired
    public AnomalyDetectionListener(AnomalyDetector anomalyDetector) {
        this.anomalyDetector = anomalyDetector;
    }

    // This method is the Pub/Sub message listener.
    // It is activated when a new message arrives on the configured channel.
    @ServiceActivator(inputChannel = "pubsubInputChannel")
    public void receiveMessage(BasicAcknowledgeablePubsubMessage message) {
        try {
        	
        	 String payload = message.getPubsubMessage().getData().toStringUtf8();
            logger.info("Received message payload: {}", payload);

            // Pass the received data to the AnomalyDetector service for analysis
            String anomalyResult = anomalyDetector.checkForAnomaly(payload);

            logger.info("Anomaly detection result: {}", anomalyResult);

            // Acknowledge the message to remove it from the subscription
            message.ack();
            logger.info("Message acknowledged successfully.");

        } catch (Exception e) {
            logger.error("Failed to process message: {}", e.getMessage(), e);
            // Nack the message to allow for redelivery if processing fails
            message.nack();
        }
    }
}

