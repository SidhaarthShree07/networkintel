package com.example.simnetwork.service;

import com.example.simnetwork.model.SimPredictionResponse;
import com.example.simnetwork.model.SimRecord;
import com.example.simnetwork.repository.SimRepository;
import org.springframework.stereotype.Service;

@Service
public class SimService {

    private final SimRepository simRepository;
    private final MLModelService mlModelService;

    public SimService(SimRepository simRepository, MLModelService mlModelService) {
        this.simRepository = simRepository;
        this.mlModelService = mlModelService;
    }

    // Handle both lat/long and city in the same method
    public SimPredictionResponse getBestSim(Double latitude, Double longitude, String city) {
        if (latitude != null && longitude != null) {
            // If latitude and longitude are provided, use the ML model directly
            return getSimFromLatLong(latitude, longitude);
        } else if (city != null && !city.isEmpty()) {
            // If a city name is provided, look it up in the database
            return getSimFromLocation(city);
        }

        // If no valid input is provided, return null or handle it as an error
        return null;
    }

    // This method uses the latitude and longitude directly with the ML model
    private SimPredictionResponse getSimFromLatLong(double latitude, double longitude) {
        // Use the ML model to predict the best SIM based on lat/long
        return mlModelService.getPredictionFromModel(latitude, longitude);
    }

    // This method checks if city exists in DB, and if not, uses the ML model to fetch and store the data
    private SimPredictionResponse getSimFromLocation(String city) {
        SimRecord cityData = simRepository.findByCity(city);

        if (cityData != null) {
            if (cityData.getBestSimProvider() != null && cityData.getScore() != null) {
                // Data already exists, return it
                SimPredictionResponse response = new SimPredictionResponse();
                response.setProvider(cityData.getBestSimProvider());
                response.setScore(cityData.getScore());
                response.setLocation(new double[]{cityData.getLatitude(), cityData.getLongitude()});
                return response;
            } else {
                // Use ML model to fetch prediction and update the DB
                SimPredictionResponse mlResponse = mlModelService.getPredictionFromModel(
                        cityData.getLatitude(),
                        cityData.getLongitude()
                );

                // Update SimRecord with new data
                double[] location = mlResponse.getLocation();
                cityData.setLatitude(location[0]);
                cityData.setLongitude(location[1]);
                cityData.setBestSimProvider(mlResponse.getProvider());
                cityData.setScore(mlResponse.getScore());

                simRepository.save(cityData);
                return mlResponse;
            }
        }

        // No record found for city
        return null;
    }
}


