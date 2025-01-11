package com.athensease.sights;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import com.athensease.optimization.TrailHeadInclusion;
import com.athensease.dataretrieval.ApiHandler;

/**
 * Represents a trip, managing its details such as duration, budget, trailheads, and chosen sights.
 * Provides functionality for trip optimization and printing trip details.
 */
public class Trip {
    private int duration;
    private double budget;
    private String address1;
    private String address2;
    private String address3;
    private List<Integer> trailHeadDays;
    private List<Integer> chosenCategories;
    private List<Sight> chosenSights;

    private List<Sight> optimizedSights;
    private HardSoftScore optimizationScore;
    private double totalDistanceTraveled;
    private double totalTravelDuration;
    private double ticketsCost;
    
    // Constructor for one trailhead or Multiple trailheads, up to 3 Trailheads are supported

    /**
     * Constructs a Trip with the specified parameters.
     *
     * @param duration Duration of the trip in days.
     * @param budget Budget for the trip.
     * @param addresses List of addresses for up to three trailheads.
     * @param trailHeadDays Days assigned to trailheads.
     * @param chosenCategories List of chosen categories for sights.
     * @param chosenSights List of sights chosen for the trip.
     */
    public Trip(int duration, double budget, List<String> addresses, List<Integer> trailHeadDays, List<Integer> chosenCategories, List<Sight> chosenSights) {
        this.duration = duration;
        this.budget = budget;
        for (int i = 0; i < addresses.size(); i++) {
            if (i == 0) {
                this.address1 = addresses.get(i);
            } else if (i == 1) {
                this.address2 = addresses.get(i);
            } else if (i == 2) {
                this.address3 = addresses.get(i);
            }
        }
        
        this.trailHeadDays = trailHeadDays;
        this.chosenCategories = chosenCategories;
        this.chosenSights = chosenSights;

        int counter = 0;
        for(Sight sight : chosenSights) {
            sight.setVisitOrder(++counter);
            List<String> origin = new ArrayList<>();
            List<String> destination = new ArrayList<>();

            origin.add(sight.getLocation());
            destination.add(address1);

            //Distances and Durations to address 1
            List<Double> results = callApiHandler(origin, destination);
            sight.setDistanceToStartingPoint(results.get(0));
            sight.setDurationToStartingPoint(results.get(1));

            results = callApiHandler(destination, origin);
            sight.setDistanceFromStartingPoint(results.get(0));
            sight.setDurationFromStartingPoint(results.get(1));

            if (address2 != null) {
                destination.clear();
                destination.add(address2);
                results = callApiHandler(origin, destination);
                sight.setDistanceToSecondTrailHead(results.get(0));
                sight.setDurationToSecondTrailHead(results.get(1));
            }

            if (address3 != null) {
                destination.clear();
                destination.add(address3);
                results = callApiHandler(origin, destination);
                sight.setDistanceToThirdTrailHead(results.get(0));
                sight.setDurationToThirdTrailHead(results.get(1));
            }
            
        }
        Sight.setMaxVisitOrder(counter);
    }

    // Helper method

    /**
     * Helper method to interact with the API handler for calculating distances and durations.
     *
     * @param origin List of origin locations.
     * @param destination List of destination locations.
     * @return A list containing distance and duration values.
     */
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
    

    // Getters and Setters

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
    public String getAddress1() {
        return address1;
    }
    public void setAddress1(String address) {
        this.address1 = address;
    }
    public String getAddress2() {
        return address2;
    }
    public void setAddress2(String address) {
        this.address2 = address;
    }
    public String getAddress3() {
        return address3;
    }
    public void setAddress3(String address) {
        this.address3 = address;
    }
    public List<Integer> getTrailHeadDays() {
        return trailHeadDays;
    }
    public void setTrailHeadDays(List<Integer> trailHeadDays) {
        this.trailHeadDays = trailHeadDays;
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

    // Returns the total cost of the trip

    /**
     * Calculates the total cost of the trip based on the prices of chosen sights.
     *
     * @return Total cost of the trip.
     */
    public double getTotalCost() {
        return this.getChosenSights().stream()
            .mapToDouble(Sight::getPrice)
            .sum();
    }

    // Returns the minimum visit time required to visit all sights, only sightseeing time
    
    /**
     * Calculates the minimum visit time required for all chosen sights.
     *
     * @return Minimum visit time in minutes.
     */
    public int getMinVisitTime() {
        return this.getChosenSights().stream()
            .mapToInt(Sight::getVisitTime)
            .sum();
    }

    /**
     * Prints the details of the trip, including the optimized route, distances, durations, and costs.
     */
    public void printTrip() {
        System.out.println("The score is: " + this.getOptimizationScore());
    
        System.out.println("\nThe optimized route is the following:\n");
    
        List<Sight> sortedSights = this.getOptimizedSights().stream()
            .sorted(Comparator.comparingInt(Sight::getVisitOrder))  // Assuming getVisitOrder() returns an int
            .collect(Collectors.toList());  // Collect the sorted sights into a list
    
        List<Sight> hotelStopPoints = TrailHeadInclusion.findHotelStopPoints(sortedSights);
        double totalDistance = 0;
        double totalTravelDuration = 0;
        double ticketsCost = 0;
        int daysCounter = 1;

        System.out.println("\nDay " + daysCounter + ":\n");

        for (int i = 0; i < sortedSights.size(); i++) {
            Sight currentSight = sortedSights.get(i);
            ticketsCost += currentSight.getPrice();
            if (currentSight.getVisitOrder() == 1) {
                System.out.println("Distance from starting point: " + currentSight.getDistanceFromStartingPoint() + " km");
                totalDistance += currentSight.getDistanceFromStartingPoint();
                totalTravelDuration += currentSight.getDurationFromStartingPoint();
            }
            
            boolean hotelStopAfter = false;
            for (Sight sight : hotelStopPoints) {
                if (currentSight.getName() == sight.getName()) {
                    hotelStopAfter = true;
                    daysCounter += 1;
                    break;
                }
            }
            
            System.out.println(currentSight);
    
            if (i < (sortedSights.size() - 1)) {  // If there is a next sight
                if (hotelStopAfter) { // If after this sight we need to return to the hotel

                    int trailHeadOption = getTrailHeadDays().get(daysCounter - 2);

                    double distanceToTrailHead = 0; //Default value
                    double durationToTrailHead = 0; //Default value

                    if (trailHeadOption == 1) {
                        distanceToTrailHead = currentSight.getDistanceToStartingPoint();
                        durationToTrailHead = currentSight.getDurationToStartingPoint();
                        System.out.println("Distance back to hotel (" + address1 + "): " + distanceToTrailHead + " km");
                    } else if (trailHeadOption == 2) {
                        distanceToTrailHead = currentSight.getDistanceToSecondTrailHead();
                        durationToTrailHead = currentSight.getDurationToSecondTrailHead();
                        System.out.println("Distance back to hotel (" + address2 + "): " + distanceToTrailHead + " km");
                    } else if (trailHeadOption == 3) {
                        distanceToTrailHead = currentSight.getDistanceToThirdTrailHead();
                        durationToTrailHead = currentSight.getDurationToThirdTrailHead();
                        System.out.println("Distance back to hotel (" + address3 + "): " + distanceToTrailHead + " km");
                    }

                    System.out.println("\nDay " + daysCounter + ":\n");
                    totalDistance += distanceToTrailHead;
                    totalTravelDuration += durationToTrailHead;
                    
                    double distanceFromTrailHead = 0; //Default value
                    double durationFromTrailHead = 0; //Default value

                    Sight nextSight = sortedSights.get(i + 1);

                    if (trailHeadOption == 1) {
                        distanceFromTrailHead = nextSight.getDistanceFromStartingPoint();
                        durationFromTrailHead = nextSight.getDurationFromStartingPoint();
                    } else if (trailHeadOption == 2) {
                        distanceFromTrailHead = nextSight.getDistanceToSecondTrailHead();
                        durationFromTrailHead = nextSight.getDurationToSecondTrailHead();
                    } else if (trailHeadOption == 3) {
                        distanceFromTrailHead = nextSight.getDistanceToThirdTrailHead();
                        durationFromTrailHead = nextSight.getDurationToThirdTrailHead();
                    }

                    totalDistance += distanceFromTrailHead;
                    totalTravelDuration += durationFromTrailHead;
                    System.out.println("Distance from hotel to next sight: " + distanceFromTrailHead + " km");
                } else { // If after this sight we don't need to return to the hotel
                    Sight nextSight = sortedSights.get(i + 1);
                    double distance = currentSight.calculateDistanceToSight(nextSight);
                    totalDistance += distance;
                    totalTravelDuration += currentSight.calculateDurationToSight(nextSight);
                    System.out.println("Distance to next sight: " + distance + " km");
                }
            } else { // If the current sight is the last sight

                System.out.println("Your trip was finished.\n");

                //Fireworks for the end of the trip
                System.out.println("       *");
                System.out.println("      * *");
                System.out.println(" *   *   *   *");
                System.out.println("  *  *   *  *");
                System.out.println("   *  ***  *");
                System.out.println("    *******");
                System.out.println("     *****");
                System.out.println("      ***");
                System.out.println("       *");
                
                int trailHeadOption = getTrailHeadDays().get(daysCounter - 1);

                double distanceToTrailHead = 0; //Default value
                double durationToTrailHead = 0; //Default value

                if (trailHeadOption == 1) {
                    distanceToTrailHead = currentSight.getDistanceToStartingPoint();
                    durationToTrailHead = currentSight.getDurationToStartingPoint();
                    System.out.println("Distance back to hotel (" + address1 + "): " + distanceToTrailHead + " km");
                } else if (trailHeadOption == 2) {
                    distanceToTrailHead = currentSight.getDistanceToSecondTrailHead();
                    durationToTrailHead = currentSight.getDurationToSecondTrailHead();
                    System.out.println("Distance back to hotel (" + address2 + "): " + distanceToTrailHead + " km");
                } else if (trailHeadOption == 3) {
                    distanceToTrailHead = currentSight.getDistanceToThirdTrailHead();
                    durationToTrailHead = currentSight.getDurationToThirdTrailHead();
                    System.out.println("Distance back to hotel (" + address3 + "): " + distanceToTrailHead + " km");
                }
                totalDistance += distanceToTrailHead;
                totalTravelDuration += durationToTrailHead;
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
