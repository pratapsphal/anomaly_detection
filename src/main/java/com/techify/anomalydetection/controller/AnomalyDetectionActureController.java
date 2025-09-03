package com.techify.anomalydetection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnomalyDetectionActureController {

    @Autowired
    private Environment environment;

    @GetMapping("/active-profile")
    public String getActiveProfiles() {
        String[] profiles = environment.getActiveProfiles();
        if (profiles.length > 0) {
            return "AnomalyDetection: Active profile(s,): " + String.join(", ", profiles);
        } else {
            return "AnomalyDete: No active profiles set.";
        }
    }
}