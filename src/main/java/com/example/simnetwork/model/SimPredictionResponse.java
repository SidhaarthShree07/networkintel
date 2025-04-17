package com.example.simnetwork.model;

public class SimPredictionResponse {
    private double[] location;
    private String provider;
    private double score;

    // Getters and Setters
    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    // Optional: toString() for debugging
    @Override
    public String toString() {
        return "SimPredictionResponse{" +
                "location='" + location + '\'' +
                ", provider='" + provider + '\'' +
                ", score=" + score +
                '}';
    }
}
