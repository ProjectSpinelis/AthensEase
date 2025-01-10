package com.athensease.sights;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import com.athensease.dataretrieval.ApiHandler;
import com.athensease.optimization.TrailHeadInclusion;

public class Trip {
    private int duration;
    private double budget;
    private String address;
    private boolean[] chosenCategories;
    private List<Sight> chosenSights;
    private int optmizeFor;

    private List<Sight> optimizedSights;
    private HardSoftScore optimizationScore;
    private double totalDistanceTraveled;
    private double totalTravelDuration;
    private double ticketsCost;
    
    // Constructor
    public Trip() {
        chosenCategories = new boolean[4];
        chosenSights = new ArrayList<>();
    }
    

    public void prepTrip() {
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
    public boolean[] getChosenCategories() {
        return chosenCategories;
    }

    public void setChosenCategories(boolean[] chosenCategories) {
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

    public double getTotalTravelDuration() {
        return totalTravelDuration;
    }

    public void setTotalTravelDuration(double totalTravelDuration) {
        this.totalTravelDuration = totalTravelDuration;
    }

    public double getTicketsCost() {
        return ticketsCost;
    }

    public void setTicketsCost(double ticketsCost) {
        this.ticketsCost = ticketsCost;
    }

    
    public int getOptmizeFor() {
        return optmizeFor;
    }


    public void setOptmizeFor(int optmizeFor) {
        this.optmizeFor = optmizeFor;
    }

    // Returns the total cost of the trip
    public double getTotalCost() {
        return this.getChosenSights().stream()
            .mapToDouble(Sight::getPrice)
            .sum();
    }

    // Returns the minimum visit time required to visit all sights, only sightseeing time
    public int getMinVisitTime() {
        return this.getChosenSights().stream()
            .mapToInt(Sight::getVisitTime)
            .sum();
    }


    // Prints Trip Details
    public void tripCalculations() {
        System.out.println("The score is: " + this.getOptimizationScore());
    
        System.out.println("\nThe optimized route is the following:\n");
    
        List<Sight> sortedSights = this.getOptimizedSights().stream()
            .sorted(Comparator.comparingInt(Sight::getVisitOrder))  // Assuming getVisitOrder() returns an int
            .collect(Collectors.toList());  // Collect the sorted sights into a list
    
        double totalDistance = 0;
        double totalTravelDuration = 0;
        double ticketsCost = 0;

        for (int i = 0; i < sortedSights.size(); i++) {
            Sight currentSight = sortedSights.get(i);
            ticketsCost += currentSight.getPrice();
            if (currentSight.getVisitOrder() == 1) {
                System.out.println("Distance from starting point: " + currentSight.getDistanceFromStartingPoint() + " km");
                totalDistance += currentSight.getDistanceFromStartingPoint();
                totalTravelDuration += currentSight.getDurationFromStartingPoint();
            }
            System.out.println(currentSight);
    
            if (i < sortedSights.size() -1) {  // If there is a next sight
                if (!currentSight.getName().equals("Hotel")) { // If the current sight is not hotel
                    if (!sortedSights.get(i + 1).getName().equals("Hotel")) { // If the next sight is not hotel
                        Sight nextSight = sortedSights.get(i + 1);
                        double distance = currentSight.calculateDistanceToSight(nextSight);
                        totalDistance += distance;
                        totalTravelDuration += currentSight.calculateDurationToSight(nextSight);
                        System.out.println("  Distance to next sight: " + distance + " km");
                    } else { // If the next sight is hotel
                        System.out.println("  Distance back to hotel: " + currentSight.getDistanceToStartingPoint() + " km");
                        totalDistance += currentSight.getDistanceToStartingPoint();
                        totalTravelDuration += currentSight.getDurationToStartingPoint();
                    }
                } else { // If the current sight is hotel
                    Sight nextSight = sortedSights.get(i + 1);
                    System.out.println("  Distance to next sight: " + nextSight.getDistanceFromStartingPoint() + " km");
                    totalDistance += nextSight.getDistanceFromStartingPoint();
                    totalTravelDuration += nextSight.getDurationFromStartingPoint();
                }
            } else { // If the current sight is the last sight
                System.out.println("Distance back to starting point: " + currentSight.getDistanceToStartingPoint() + " km");
                totalDistance += currentSight.getDistanceFromStartingPoint();
                totalTravelDuration += currentSight.getDurationFromStartingPoint();
            }

        }
        this.setTotalDistanceTraveled(totalDistance);
        this.setTicketsCost(ticketsCost);
        this.setTotalTravelDuration(totalTravelDuration);
        System.out.println("\nTotal distance traveled: " + this.getTotalDistanceTraveled() + " km");
        System.out.println("Total duration spent on trasportation: " + this.getTotalTravelDuration() + " minutes");
        System.out.println("Total duration spent on sightseeing: " + this.getMinVisitTime() + " minutes");
        System.out.println("Tickets cost: " + this.getTicketsCost() + " euro");
    }
}
