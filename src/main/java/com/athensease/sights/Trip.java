package com.athensease.sights;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import com.athensease.dataretrieval.ApiHandler;

public class Trip {
    private int duration;
    private double budget;
    private String address;
    private List<Integer> chosenCategories;
    private List<Sight> chosenSights;

    private List<Sight> optimizedSights;
    private HardSoftScore optimizationScore;
    private double totalDistanceTraveled;
    private double ticketsCost;
    
    // Constructor
    public Trip(int duration, double budget, String address, List<Integer> chosenCategories, List<Sight> chosenSights) {
        this.duration = duration;
        this.budget = budget;
        this.address = address;
        this.chosenCategories = chosenCategories;
        this.chosenSights = chosenSights;

        int counter = 0;
        for(Sight sight : chosenSights) {
            sight.setVisitOrder(++counter);
            List<String> origin = new ArrayList<>();
            List<String> destination = new ArrayList<>();

            origin.add(sight.getLocation());
            destination.add(address);

            List<Double> results = callApiHandler(origin, destination);
            sight.setDistanceToStartingPoint(results.get(0));
            sight.setDurationToStartingPoint(results.get(1));

            results = callApiHandler(destination, origin);
            sight.setDistanceFromStartingPoint(results.get(0));
            sight.setDurationFromStartingPoint(results.get(1));
        }
        Sight.setMaxVisitOrder(counter);
    }

    // Helper method
    public static List<Double> callApiHandler(List<String> origin, List<String> destination) {
        List<Double> distanceAndDuration = new ArrayList<>();
        ApiHandler handler = new ApiHandler(origin, destination);
        String url = handler.createURL();
    
        try {
            String response = handler.getResponse(url);
            double distance = handler.extractField(response, "distance"); // Assume this returns a double
            double duration = handler.extractField(response, "duration"); // Assume this returns a double
    
            distanceAndDuration.add(distance);
            distanceAndDuration.add(duration);
        } catch (IOException e) {
            e.printStackTrace();
            // Return [-1, -1] in case of an error
            distanceAndDuration.add(-1.0);
            distanceAndDuration.add(-1.0);
        }
    
        return distanceAndDuration;
    }
    

    // Setters and Getters
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public double getBudget() {
        return budget;
    }
    public void setBudget(double budget) {
        this.budget = budget;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public List<Sight> getChosenSights() {
        return chosenSights;
    }
    public void setChosenSights(List<Sight> chosenSights) {
        this.chosenSights = chosenSights;
    }
    public List<Integer> getChosenCategories() {
        return chosenCategories;
    }

    public void setChosenCategories(List<Integer> chosenCategories) {
        this.chosenCategories = chosenCategories;
    }

    public List<Sight> getOptimizedSights() {
        return optimizedSights;
    }

    public void setOptimizedSights(List<Sight> optimizedSights) {
        this.optimizedSights = optimizedSights;
    }

    public HardSoftScore getOptimizationScore() {
        return optimizationScore;
    }

    public void setOptimizationScore(HardSoftScore optimizationScore) {
        this.optimizationScore = optimizationScore;
    }

    public double getTotalDistanceTraveled() {
        return totalDistanceTraveled;
    }

    public void setTotalDistanceTraveled(double totalDistanceTraveled) {
        this.totalDistanceTraveled = totalDistanceTraveled;
    }

    public double getTicketsCost() {
        return ticketsCost;
    }

    public void setTicketsCost(double ticketsCost) {
        this.ticketsCost = ticketsCost;
    }

    // Prints Trip Details
    public void printTrip() {
        System.out.println("The score is: " + this.getOptimizationScore());
    
        System.out.println("\nThe optimized route is the following:\n");
    
        List<Sight> sortedSights = this.getOptimizedSights().stream()
            .sorted(Comparator.comparingInt(Sight::getVisitOrder))  // Assuming getVisitOrder() returns an int
            .collect(Collectors.toList());  // Collect the sorted sights into a list
    
        double totalDistance = 0;
        double ticketsCost = 0;
        for (int i = 0; i < sortedSights.size(); i++) {
            Sight currentSight = sortedSights.get(i);
            ticketsCost += currentSight.getPrice();
            if (currentSight.getVisitOrder() == 1) {
                System.out.println("Distance from starting point: " + currentSight.getDistanceFromStartingPoint() + " km");
                totalDistance += currentSight.getDistanceFromStartingPoint();
            }
            System.out.println(currentSight);
    
            if (i < sortedSights.size() - 1) {  // If there is a next sight
                Sight nextSight = sortedSights.get(i + 1);
                double distance = currentSight.calculateDistanceToSight(nextSight);
                totalDistance += distance;
                System.out.println("  Distance to next sight: " + distance + " km");
            } else {
                System.out.println("Distance back to starting point: " + currentSight.getDistanceToStartingPoint() + " km");
                totalDistance += currentSight.getDistanceToStartingPoint();
            }

        }
        this.setTotalDistanceTraveled(totalDistance);
        this.setTicketsCost(ticketsCost);
        System.out.println("Total distance traveled: " + this.getTotalDistanceTraveled() + " km");
        System.out.println("Tickets cost: " + this.getTicketsCost() + " euro");
    }
}
