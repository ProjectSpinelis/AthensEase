/**
 * The {@code Sight} class represents a sightseeing location with various attributes like:
 * - Name, location, price, visit time, and category.
 * - Distance and duration to/from the starting point and other trailheads.
 * - A planning variable for determining the visit order when solving an optimization problem.
 */
package com.athensease.sights;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Sight {

    @PlanningId
    private String name; // Unique identifier for the sight
    private String location; // Physical location of the sight
    private double price; // Cost to visit the sight
    private int visitTime; // Time required to visit the sight (in minutes)
    private String category; // Category of the sight (e.g., museum, park)
    private boolean mustSee; // Whether this sight is a must-see for the trip
    private String link; // A URL for more information about the sight

    @PlanningVariable
    private Integer visitOrder; // The order in which the sight will be visited

    // Various distance and duration variables related to the starting point and trailheads
    private double distanceToStartingPoint;
    private double distanceFromStartingPoint;
    private double durationToStartingPoint;
    private double durationFromStartingPoint;
    private double durationToSecondTrailHead;
    private double distanceToSecondTrailHead;
    private double durationToThirdTrailHead;
    private double distanceToThirdTrailHead;

    // Static variable to keep track of the max visit order
    private static int maxVisitOrder = 0;

    /**
     * Constructor for creating a new Sight instance with essential details.
     *
     * @param name The name of the sight.
     * @param location The location of the sight.
     * @param price The price to visit the sight.
     * @param visitTime The time required to visit the sight.
     * @param category The category of the sight.
     * @param mustSee Whether this sight is a must-see.
     */
    public Sight(String name, String location, double price, int visitTime, String category, boolean mustSee) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.visitTime = visitTime;
        this.category = category;
        this.mustSee = mustSee;
    }

    // Default constructor
    public Sight() {}

    // Getters and setters for each attribute
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getVisitTime() { return visitTime; }
    public void setVisitTime(int visitTime) { this.visitTime = visitTime; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public boolean isMustSee() { return mustSee; }
    public void setMustSee(boolean mustSee) { this.mustSee = mustSee; }
    public Integer getVisitOrder() { return visitOrder; }
    public void setVisitOrder(Integer visitOrder) { this.visitOrder = visitOrder; }
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    // Distance and duration related setters and getters
    public void setDistanceToStartingPoint(double distance) { this.distanceToStartingPoint = distance; }
    public double getDistanceToStartingPoint() { return distanceToStartingPoint; }
    public void setDurationToStartingPoint(double duration) { this.durationToStartingPoint = duration; }
    public double getDurationToStartingPoint() { return durationToStartingPoint; }
    public void setDistanceFromStartingPoint(double distance) { this.distanceFromStartingPoint = distance; }
    public double getDistanceFromStartingPoint() { return distanceFromStartingPoint; }
    public void setDurationFromStartingPoint(double duration) { this.durationFromStartingPoint = duration; }
    public double getDurationFromStartingPoint() { return durationFromStartingPoint; }
    public void setDistanceToSecondTrailHead(double distance) { this.distanceToSecondTrailHead = distance; }
    public double getDistanceToSecondTrailHead() { return distanceToSecondTrailHead; }
    public void setDurationToSecondTrailHead(double duration) { this.durationToSecondTrailHead = duration; }
    public double getDurationToSecondTrailHead() { return durationToSecondTrailHead; }
    public void setDistanceToThirdTrailHead(double distance) { this.distanceToThirdTrailHead = distance; }
    public double getDistanceToThirdTrailHead() { return distanceToThirdTrailHead; }
    public void setDurationToThirdTrailHead(double duration) { this.durationToThirdTrailHead = duration; }
    public double getDurationToThirdTrailHead() { return durationToThirdTrailHead; }

    // Static methods for handling max visit order
    public static int getMaxVisitOrder() { return maxVisitOrder; }
    public static void setMaxVisitOrder(int maxVisitOrder) { Sight.maxVisitOrder = maxVisitOrder; }

    /**
     * Calculates the distance to another sight.
     *
     * @param sight The sight to calculate the distance to.
     * @return The distance between this sight and the other sight.
     */
    public double calculateDistanceToSight(Sight sight) {
        String origin = this.getLocation();
        String destination = sight.getLocation();
        SightsFileHandler handler = new SightsFileHandler();
        return handler.getDistanceFromJson(origin, destination);
    }

    /**
     * Calculates the duration to reach another sight.
     *
     * @param sight The sight to calculate the duration to.
     * @return The duration to reach the other sight.
     */
    public double calculateDurationToSight(Sight sight) {
        String origin = this.getLocation();
        String destination = sight.getLocation();
        SightsFileHandler handler = new SightsFileHandler();
        return handler.getDurationFromJson(origin, destination);
    }

    /**
     * Returns a string representation of the sight, including essential details.
     *
     * @return A string describing the sight.
     */
    @Override
    public String toString() {
        return "Sight Information:\n" +
                "Name: " + name + "\n" +
                "Visit Order: " + visitOrder + "\n" +
                "Price: " + price + "\n" +
                "Visit Time: " + visitTime + "\n\n";
    }
}
