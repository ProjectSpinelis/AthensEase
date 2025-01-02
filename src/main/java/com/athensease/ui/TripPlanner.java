package com.athensease.ui;

import java.util.List;

import com.athensease.optimization.TrailHeadInclusion;
import com.athensease.optimization.Optimizer;
import com.athensease.sights.Sight;
import com.athensease.sights.Trip;

public class TripPlanner {
    
    public static void main (String args[]) {

        UserInputHandler inputHandler = new UserInputHandler();

        int duration = inputHandler.gatherDuration();
        String address = inputHandler.gatherTrailhead();
        double budget = inputHandler.gatherBudget();
        List<Integer> chosenCategories = inputHandler.chooseCategories();
        List<Sight> chosenSights = inputHandler.chooseSights(chosenCategories);
        boolean objective = inputHandler.chooseObjective();
        
        Trip trip = new Trip(duration, budget, address, chosenCategories, chosenSights);

        if (trip.getBudget() < trip.getTotalCost()) {
            System.out.println("Your budget is too low to visit all the sights you've chosen. Please try again.");
        } else {
            if ((trip.getDuration() * 480) < trip.getMinVisitTime()) { 
                // 480 minutes = 8 hours. Assumes a full day of sightseeing is 8 hours.
                System.out.println("Your duration is too short to visit all the sights you've chosen. Please try again.");
            } else {
                System.out.println("You're all set! Let's recap.");
                System.out.println("Duration of trip: " + trip.getDuration());
                System.out.println("Trailhead: " + trip.getAddress());
                System.out.println("Your budget: "+ trip.getBudget());
                System.out.println("Your categories of interest: " + trip.getChosenCategories());
                System.out.println("Your chosen sights: ");
                for (Sight sight : trip.getChosenSights()) {
                    System.out.println(sight);
                }

                Optimizer.optimizeTrip(trip, objective);
                TrailHeadInclusion.addOneTraildHead(trip, trip.getOptimizedSights());
                trip.printTrip();
            }
        }
    }
}
