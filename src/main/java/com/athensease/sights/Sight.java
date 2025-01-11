package com.athensease.sights;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

/**
 * Represents a sight to be visited, including details such as location, price, visit time,
 * and various distances and durations related to trailheads and other sights.
 */
@PlanningEntity
public class Sight {

    /**
     * Unique identifier for the sight.
     */
    @PlanningId
    private String name;
    private String location;
    private double price;
    private int visitTime;
    private String category;
    private boolean mustSee;

    /**
     * Order in which the sight is visited during the trip.
     */
    @PlanningVariable
    private Integer visitOrder;
    
    //About first trailhead

    /**
     * Distance from the sight to the starting point, in kilometers.
     */
    private double distanceToStartingPoint;

    /**
     * Distance from the starting point to the sight, in kilometers.
     */
    private double distanceFromStartingPoint;

    /**
     * Duration from the sight to the starting point, in minutes.
     */
    private double durationToStartingPoint;

    /**
     * Duration from the starting point to the sight, in minutes.
     */
    private double durationFromStartingPoint;

    //About second and third trailhead

    /**
     * Distance from the sight to the second trailhead, in kilometers.
     */
    private double distanceToSecondTrailHead;

    /**
     * Duration from the sight to the second trailhead, in minutes.
     */
    private double durationToSecondTrailHead;

    /**
     * Distance from the sight to the third trailhead, in kilometers.
     */
    private double distanceToThirdTrailHead;

    /**
     * Duration from the sight to the third trailhead, in minutes.
     */
    private double durationToThirdTrailHead;

    /**
     * Maximum visit order allowed for any sight.
     */
    private static int maxVisitOrder = 0;

    /**
     * Constructs a new Sight with specified attributes.
     *
     * @param name Name of the sight.
     * @param location Location of the sight.
     * @param price Price to visit the sight.
     * @param visitTime Estimated visit time in minutes.
     * @param category Category of the sight.
     * @param mustSee Indicates if the sight is a must-see.
     */
    public Sight(String name, String location, double price, int visitTime, String category, boolean mustSee) {
        this.name = name;
        this. location = location;
        this.price = price;
        this.visitTime = visitTime;
        this.category = category;
        this.mustSee = mustSee;
    }

    /**
     * Default constructor for Sight.
     */
    public Sight() {

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public int getPrice() {
        return (int) price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public int getVisitTime() {
        return visitTime;
    }
    public void setVisitTime(int visittime) {
        this.visitTime = visittime;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public boolean isMustSee() {
        return mustSee;
    }
    public void setMustSee(boolean mustSee) {
        this.mustSee = mustSee;
    }

    public int getVisitOrder() {
        if (visitOrder == null) {
            return 0;
        } else {
            return visitOrder;
        }
    }

    public void setVisitOrder(int visitOrder) {
        this.visitOrder = visitOrder;
    }

    public void setDistanceToStartingPoint(double distanceToStartingPoint) {
        this.distanceToStartingPoint = distanceToStartingPoint;
    }

    public double getDistanceToStartingPoint() {
        return distanceToStartingPoint;
    }

    public double getDurationToStartingPoint() {
        return durationToStartingPoint;
    }

    public void setDurationToStartingPoint(double durationToStartingPoint) {
        this.durationToStartingPoint = durationToStartingPoint;
    }

    public double getDistanceFromStartingPoint() {
        return distanceFromStartingPoint;
    }

    public void setDistanceFromStartingPoint(double distanceFromStartingPoint) {
        this.distanceFromStartingPoint = distanceFromStartingPoint;
    }

    public double getDurationFromStartingPoint() {
        return durationFromStartingPoint;
    }

    public void setDurationFromStartingPoint(double durationFromStartingPoint) {
        this.durationFromStartingPoint = durationFromStartingPoint;
    }

    public double getDistanceToSecondTrailHead() {
        return distanceToSecondTrailHead;
    }

    public void setDistanceToSecondTrailHead(double distanceToSecondTrailHead) {
        this.distanceToSecondTrailHead = distanceToSecondTrailHead;
    }

    public double getDurationToSecondTrailHead() {
        return durationToSecondTrailHead;
    }

    public void setDurationToSecondTrailHead(double durationToSecondTrailHead) {
        this.durationToSecondTrailHead = durationToSecondTrailHead;
    }

    public double getDistanceToThirdTrailHead() {
        return distanceToThirdTrailHead;
    }

    public void setDistanceToThirdTrailHead(double distanceToThirdTrailHead) {
        this.distanceToThirdTrailHead = distanceToThirdTrailHead;
    }

    public double getDurationToThirdTrailHead() {
        return durationToThirdTrailHead;
    }

    public void setDurationToThirdTrailHead(double durationToThirdTrailHead) {
        this.durationToThirdTrailHead = durationToThirdTrailHead;
    }

    // Static methods
    
    public static int getMaxVisitOrder() {
        return maxVisitOrder;
    }

    public static void setMaxVisitOrder(int maxVisitOrder) {
        Sight.maxVisitOrder = maxVisitOrder;
    }

    /**
     * Calculates the distance to another sight.
     *
     * @param sight The destination sight.
     * @return Distance to the destination sight in kilometers.
     */
    public double calculateDistanceToSight(Sight sight) {
        String origin = this.getLocation();
        String destination = sight.getLocation();
        SightsFileHandler handler = new SightsFileHandler();

        return handler.getDistanceFromJson(origin, destination);
    }

    /**
     * Calculates the duration to another sight.
     *
     * @param sight The destination sight.
     * @return Duration to the destination sight in minutes.
     */
    public double calculateDurationToSight(Sight sight) {
        String origin = this.getLocation();
        String destination = sight.getLocation();
        SightsFileHandler handler = new SightsFileHandler();
        
        return handler.getDurationFromJson(origin, destination);
    }

    /**
     * Returns a string representation of the sight.
     *
     * @return A string containing the sight's details.
     */
    @Override
    public String toString() {
        if(this.name.equals("Hotel")) {
            return "\nHotel\n";
        } else {
            return "Sight Information:\n" +
                    "Name: " + name + "\n" +
                    "Visit Order: " + visitOrder + "\n" +
                    "Price: " + price + "\n" +
                    "Visit Time: " + visitTime + "\n\n";
        }
    }
}
