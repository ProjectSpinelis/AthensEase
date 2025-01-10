package com.athensease.sights;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;


@PlanningEntity
public class Sight {

    @PlanningId
    private String name;
    private String location;
    private double price;
    private int visitTime;
    private String category;
    private boolean mustSee;

    @PlanningVariable
    private Integer visitOrder;
    
    //About first trailhead
    private double distanceToStartingPoint;
    private double distanceFromStartingPoint;
    private double durationToStartingPoint;
    private double durationFromStartingPoint;

    //About second and third trailhead
    private double distanceToSecondTrailHead;
    private double durationToSecondTrailHead;
    private double distanceToThirdTrailHead;
    private double durationToThirdTrailHead;

    private static int maxVisitOrder = 0;

    public Sight(String name, String location, double price, int visitTime, String category, boolean mustSee) {
        this.name = name;
        this. location = location;
        this.price = price;
        this.visitTime = visitTime;
        this.category = category;
        this.mustSee = mustSee;
    }

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

    public static int getMaxVisitOrder() {
        return maxVisitOrder;
    }

    public static void setMaxVisitOrder(int maxVisitOrder) {
        Sight.maxVisitOrder = maxVisitOrder;
    }



    public double calculateDistanceToSight(Sight sight) {
        String origin = this.getLocation();
        String destination = sight.getLocation();
        SightsFileHandler handler = new SightsFileHandler();

        return handler.getDistanceFromJson(origin, destination);
    }

    public double calculateDurationToSight(Sight sight) {
        String origin = this.getLocation();
        String destination = sight.getLocation();
        SightsFileHandler handler = new SightsFileHandler();
        
        return handler.getDurationFromJson(origin, destination);
    }

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
