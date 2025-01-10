package com.athensease.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.athensease.sights.Sight;
import com.athensease.sights.Trip;

public class ResultScreen {

    private static Trip trip;
    public static void setTrip(Trip trip) {
        ResultScreen.trip = trip;
    }
    

    // No-argument constructor (required by JavaFX)
    public ResultScreen() {
    }

    public Scene createScene() {
        // Ensure the trip data is initialized before proceeding
        if (trip == null) {
            throw new IllegalStateException("Trip data not initialized.");
        }

        // Root container for the UI
        VBox root = new VBox(20);  // Increased spacing between elements
        root.setStyle("-fx-padding: 20");

        // Title
        Label titleLabel = new Label("Your Trip Overview");
        titleLabel.getStyleClass().add("heading");
        titleLabel.setStyle("-fx-font-size: 28px");

        // Trip details section
        Label durationLabel = new Label("Duration: " + trip.getDuration() + " day(s)");
        Label addressLabel = new Label("Trailhead: " + trip.getAddress());
        Label distancLabel = new Label("Total Distance Traveled: " + trip.getTotalDistanceTraveled() + " km");
        Label trasportationLabel = new Label("Total Transport Time: " + trip.getTotalTravelDuration() + " minutes");
        Label sightseeingLabel = new Label("Total Sightseeing Time: " + trip.getMinVisitTime() + " minutes");
        Label ticketsLabel = new Label("Total Tickets Cost: " + trip.getTicketsCost() + " €");


        // Layout for trip details
        VBox tripDetailsBox = new VBox(10);
        tripDetailsBox.getChildren().addAll(titleLabel, durationLabel);
        //Show budget only if applicable
        if (trip.getBudget() < Integer.MAX_VALUE) {
            Label budgetLabel = new Label("Budget: €" + trip.getBudget());
            tripDetailsBox.getChildren().add(budgetLabel);
        }
        tripDetailsBox.getChildren().addAll(addressLabel, distancLabel, trasportationLabel, sightseeingLabel, ticketsLabel);
        tripDetailsBox.setStyle("-fx-padding: 10;");

        if (trip.getBudget() < trip.getTotalCost()) {
            Label budgetErrorLabel = new Label("Your budget might be too low for the sights you have chosen. Consider changing your budget or sights selection.");
            budgetErrorLabel.setStyle("-fx-text-fill: red;");
            tripDetailsBox.getChildren().add(budgetErrorLabel);
        } 
        if ((trip.getDuration() * 480) < trip.getMinVisitTime()) {
            Label durationErrorLabel = new Label("Your duration might be too short to visit all the sights you have chosen. Consider changing your sights selection");
            durationErrorLabel.setStyle("-fx-text-fill: red;");
            tripDetailsBox.getChildren().add(durationErrorLabel);
        }

        // Add all components to the root
        root.getChildren().addAll(tripDetailsBox);
        displayOptimizedRoute(root);

        Scene scene = new Scene(root, 700, 600);

        // Add the stylesheet to the scene
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        return scene;
    }

    // Method to display the optimized route
    private void displayOptimizedRoute(VBox root) {

        // Create a section to display the optimized route
        VBox optimizedRouteBox = new VBox(15);
        optimizedRouteBox.setStyle("-fx-padding: 20px 0; -fx-border-color: #d4b483; -fx-border-width: 2px;");

        Label optimizedRouteLabel = new Label("Optimized Route:");
        optimizedRouteLabel.getStyleClass().add("heading");
        optimizedRouteLabel.setStyle("-fx-font-size: 18px;");

        // Add each sight in the optimized route
        optimizedRouteBox.getChildren().add(optimizedRouteLabel);
        Label dayOneLabel = new Label("Day 1");
        optimizedRouteBox.getChildren().add(dayOneLabel);
        dayOneLabel.getStyleClass().add("heading");
        dayOneLabel.setStyle("-fx-font-size: 18px");

        int count = 0;
        for (Sight sight : trip.getOptimizedSights()) {
            if (sight.getName() == "Hotel") {
                Label dayChangeLabel = new Label("Day " + ((int) sight.getPrice() + 1));
                dayChangeLabel.getStyleClass().add("heading");
                dayChangeLabel.setStyle("-fx-font-size: 18px");
                optimizedRouteBox.getChildren().add(dayChangeLabel);
                count = 0;
            } else {
                count++; 
                VBox sightBox = new VBox(10);
                Label nLabel = new Label(sight.getName());
                nLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
                sightBox.getChildren().addAll(
                    nLabel,
                    new Label("Visit Order: " + count),
                    new Label("Ticket cost: " + sight.getPrice() + " €"),
                    new Label("Viewing Time: " + sight.getVisitTime() + " minutes")
                );
                sightBox.setStyle("-fx-background-color: #d4b483; -fx-padding: 10; -fx-border-color: #cccccc; -fx-border-width: 1px;");
                optimizedRouteBox.getChildren().add(sightBox);
            }
        }

        // Add the optimized route section to the root
        root.getChildren().add(optimizedRouteBox);

        // Scrollability for the optimized route
        ScrollPane scrollPane = new ScrollPane(optimizedRouteBox);
        scrollPane.setFitToWidth(true);
        root.getChildren().add(scrollPane);
    }
}
