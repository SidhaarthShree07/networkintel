package com.example.simnetwork.service;

import com.example.simnetwork.model.SimPredictionResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class MLModelService {

    private final RestTemplate restTemplate;

    public MLModelService() {
        this.restTemplate = new RestTemplate();
    }

    public SimPredictionResponse getPredictionFromModel(double lat, double lon) {
        String url = "https://ml-shree007.azurewebsites.net/api/get_best_provider";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("lat", lat);
        requestBody.put("lon", lon);

        return restTemplate.postForObject(url, requestBody, SimPredictionResponse.class);
    }
}


