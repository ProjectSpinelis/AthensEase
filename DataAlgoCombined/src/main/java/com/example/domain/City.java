package com.example.domain;

import com.example.DataRetrieval.*;

public class City {
    private String name;

    /*
    private double longitude;
    private double latitude;
    */

    public City (String name) {
        this.name = name;
        /* 
        this.latitude = latitude;
        this.longitude = longitude;
        */
    }

    public String getName() {
        return name;
    }

    /* 
    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
    */

    
    public double calculateDistanceToCity(City city) {
        /*
        return Math.sqrt(Math.pow((this.getLatitude() - city.getLatitude()), 2.0)
        + Math.pow((this.getLongitude() - city.getLongitude()), 2.0));
        */
        String destinationName = city.getName();
        double distance;
        // List<ProperListCreation.AttractionDistance> filteredDistances = ProperListCreation.getFilteredDistances();
        for (ProperListCreation.AttractionDistance ad: ProperListCreation.getFilteredDistances()) {
            if (destinationName == ad.destination) {
                if (ad.origin == this.name) {
                    distance = (double) ad.distance;
                    return distance;
                }
            }
        }
        return -1; // In this case it doesn't find the city
    }

    public double calculateDurationToCity(City city) {
        String destinationName = city.getName();
        double duration;
        for (ProperListCreation.AttractionDistance ad: ProperListCreation.getFilteredDistances()) {
            if (destinationName == ad.destination) {
                if (ad.origin == this.name) {
                    duration = (double) ad.duration;
                    return duration;
                }
            }
        }
        return -1; // In this case it doesn't find the city
    }
    
}
