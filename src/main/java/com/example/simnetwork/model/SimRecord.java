package com.example.simnetwork.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sim_data")
public class SimRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    private Double latitude;

    private Double longitude;

    private String bestSimProvider;

    private Double score;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

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

    public String getBestSimProvider() {
        return bestSimProvider;
    }

    public void setBestSimProvider(String bestSimProvider) {
        this.bestSimProvider = bestSimProvider;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
