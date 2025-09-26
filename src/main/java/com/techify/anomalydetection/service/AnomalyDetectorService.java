package com.techify.anomalydetection.service;


import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.techify.anomalydetection.dto.AnomalyData;

@Service
public class AnomalyDetectorService {

    private static final Logger logger = LoggerFactory.getLogger(AnomalyDetectorService.class);
    private final ChatClient chatClient;
    private final Firestore firestore;
    private final CollectionReference anomaliesCollection;

    @Autowired
    public AnomalyDetectorService(ChatClient chatClient, Firestore firestore) {
        this.chatClient = chatClient;
        this.firestore = firestore;
        this.anomaliesCollection = firestore.collection("anomalies");
    }

    /**
     * Checks for anomalies in network data using an AI model and stores the result.
     * @param networkData The network data in JSON format.
     * @return A string indicating if an anomaly was detected.
     */
    public String checkForAnomalyAndStore(String networkData) {
        String promptText = "You are an AI assistant for a telecom network. Your task is to analyze network data in JSON format for any potential anomalies, such as unusually high traffic volume or errors. Your response must be very concise. Respond with 'ANOMALY DETECTED' if an anomaly is found, or 'NORMAL' if the data appears normal. Here is the data: "
                + networkData;
        
        UserMessage userMessage = new UserMessage(promptText);
        
        String response = chatClient.prompt()
                .user(promptText)
                .call()
                .content();
        
        // If an anomaly is detected, store the data
        if ("ANOMALY DETECTED".equals(response)) {
            storeAnomaly(networkData);
        }
        
        return response;
    }
    
    /**
     * Stores the anomaly data in a Firestore collection.
     * @param payload The raw message payload from Pub/Sub.
     */
    private void storeAnomaly(String payload) {
        try {
            ApiFuture<DocumentReference> addedDocRef = anomaliesCollection.add(new AnomalyData(payload));
            logger.info("Anomaly data stored in Firestore with ID: {}", addedDocRef.get().getId());
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Failed to store anomaly data in Firestore", e);
        }
    }
}
