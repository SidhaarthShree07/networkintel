package com.example.simnetwork.controller;

import com.example.simnetwork.model.SimPredictionResponse;
import com.example.simnetwork.model.SimRequest;
import com.example.simnetwork.service.SimService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "https:/", allowCredentials = "true")
@RestController
@RequestMapping("/api/sim")
public class SimController {

    private final SimService simService;

    public SimController(SimService simService) {
        this.simService = simService;
    }

    @PostMapping("/get")
    public ResponseEntity<SimPredictionResponse> getSim(@RequestBody SimRequest request) {
        System.out.println("Received request: Latitude = " + request.getLatitude() + ", Longitude = " + request.getLongitude() + ", Location = " + request.getLocation());

        SimPredictionResponse prediction = null;

        if (request.getLatitude() != null && request.getLongitude() != null) {
            prediction = simService.getBestSim(request.getLatitude(), request.getLongitude(), null);
        } else if (request.getLocation() != null && !request.getLocation().isEmpty()) {
            prediction = simService.getBestSim(null, null, request.getLocation());
        }

        if (prediction != null) {
            return ResponseEntity.ok(prediction);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}

