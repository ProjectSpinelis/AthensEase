package com.example.domain;

public class City {
    private String name;
    private double longitude;
    private double latitude;

    public City (String name, double longitude, double latitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double calculateDistanceToCity(City city) {
        return Math.sqrt(Math.pow((this.getLatitude() - city.getLatitude()), 2.0)
        + Math.pow((this.getLongitude() - city.getLongitude()), 2.0));
    }
}

