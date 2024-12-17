package com.athensease.ui;

import com.athensease.optimization.Optimizer;
import com.athensease.sights.Sight;
import com.athensease.sights.Trip;


import java.util.List;

public class TripPlanner {

    public static void main(String[] args) {
        // Gather user input
        UserInputHandler inputHandler = new UserInputHandler();

        int duration = inputHandler.gatherDuration();
        String address = inputHandler.gatherTrailhead();
        double budget = inputHandler.gatherBudget();
        List<Integer> chosenCategories = inputHandler.chooseCategories();
        List<Sight> chosenSights = inputHandler.chooseSights(chosenCategories);

        // Create Trip object
        Trip trip = new Trip(duration, budget, address, chosenCategories, chosenSights);

        // Print trip details to the console
        System.out.println("You're all set! Let's recap.");
        System.out.println("Duration of trip: " + trip.getDuration());
        System.out.println("Trailhead: " + trip.getAddress());
        System.out.println("Your budget: " + trip.getBudget());
        System.out.println("Your categories of interest: " + trip.getChosenCategories());
        System.out.println("Your chosen sights: ");
        for (Sight sight : trip.getChosenSights()) {
            System.out.println(sight);
        }


        // Optimize the trip (if needed)
        Optimizer.optimizeTrip(trip);
        trip.printTrip();

        // Launch the UI to display the trip details
        launchUI(trip);
    }

    // Launch the JavaFX UI with the trip data
    private static void launchUI(Trip trip) {
        // Pass the trip object to the UI class when launching the application
        TripDisplayUI.launchUI(trip);
    }
}

