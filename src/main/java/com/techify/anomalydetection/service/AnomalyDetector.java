package com.techify.anomalydetection.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// This service handles the AI-powered anomaly detection logic.
@Service
public class AnomalyDetector {

    private final ChatClient chatClient;

    @Autowired
    public AnomalyDetector(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    /**
     * Checks for anomalies in network data using an AI model.
     * @param networkData The network data in JSON format.
     * @return A string indicating if an anomaly was detected ("ANOMALY DETECTED") or not ("NORMAL").
     */
    public String checkForAnomaly(String networkData) {
        // Create a user prompt that provides the LLM with a persona and clear instructions.
        String promptText = "You are an AI assistant for a telecom network. Your task is to analyze network data in JSON format for any potential anomalies, such as unusually high traffic volume or errors. Your response must be very concise. Respond with 'ANOMALY DETECTED' if an anomaly is found, or 'NORMAL' if the data appears normal. Here is the data: " + networkData;
        
        // Wrap the prompt in a UserMessage object
        UserMessage userMessage = new UserMessage(promptText);
        
        // Send the prompt to the AI model and get the response
        return chatClient.prompt()      // start a prompt
                .user(networkData)   // add user message
                .call()        // execute
                .content();    // extract response text
    }
}
