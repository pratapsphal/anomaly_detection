package com.techify.anomalydetection.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnomalyDetectionController {
    @GetMapping("/hello")
    public String hello() {
        return "Welcome AnomalyDetection Service.";
    }
}