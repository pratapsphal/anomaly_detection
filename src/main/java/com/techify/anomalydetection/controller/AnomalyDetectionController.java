package com.techify.anomalydetection.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.techify.anomalydetection.dto.AnomalyData;

@RestController
@RequestMapping("/api/v1")
public class AnomalyDetectionController {

    private static final Logger logger = LoggerFactory.getLogger(AnomalyDetectionController.class);
    private final Firestore firestore;
    private final CollectionReference anomaliesCollection;

    @Autowired
    public AnomalyDetectionController(Firestore firestore) {
    	 logger.info(">> Retrieved Constructure anomalies from Firestore");
        this.firestore = firestore;
        this.anomaliesCollection = firestore.collection("anomalies");
    }

    /**
     * API Endpoint to retrieve all stored anomalies.
     * @return A list of AnomalyData objects.
     */
    @GetMapping("/anomalies")
    public List<AnomalyData> getAnomalies() throws ExecutionException, InterruptedException {
    	 logger.info(">>> Retrieved anomalies from Firestore");
        ApiFuture<QuerySnapshot> future = anomaliesCollection.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        
        logger.info(">>>Retrieved {} anomalies from Firestore.", documents.size());
        
        return documents.stream()
                .map(doc -> doc.toObject(AnomalyData.class))
                .collect(Collectors.toList());
    }

    /**
     * Health check endpoint to verify the service is running.
     * @return A simple message.
     */
    @GetMapping("/health")
    public String healthCheck() {
        return "Anomaly Detection Service is up and running!";
    }
}
