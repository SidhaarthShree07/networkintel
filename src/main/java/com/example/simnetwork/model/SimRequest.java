package com.example.simnetwork.model;

public class SimRequest {
    private Double latitude;   // Changed from double to Double
    private Double longitude;  // Changed from double to Double
    private String location;

    // Getters and Setters
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean hasCoordinates() {
        return latitude != 0.0 || longitude != 0.0;
    }
    
}
