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
    private String link;

    @PlanningVariable
    private Integer visitOrder;
    
    private double distanceToStartingPoint;
    private double distanceFromStartingPoint;
    private double durationToStartingPoint;
    private double durationFromStartingPoint;

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
    public double getPrice() {
        return price;
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
        return visitOrder;
    }

    public void setVisitOrder(int visitOrder) {
        this.visitOrder = visitOrder;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
    return "Sight Information:\n" +
            "Name: " + name + "\n" +
            "Visit Order: " + visitOrder + "\n" +
            "Price: " + price + "\n" +
            "Visit Time: " + visitTime + "\n\n";
    }
}
