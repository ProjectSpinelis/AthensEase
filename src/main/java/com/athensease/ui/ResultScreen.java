package com.athensease.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.athensease.optimization.TrailHeadInclusion;
import com.athensease.sights.Sight;
import com.athensease.sights.Trip;

public class ResultScreen {

    private static Trip trip;
    private List<Sight> sightsForMap = new ArrayList<>();
    public static void setTrip(Trip trip) {
        ResultScreen.trip = trip;
    }
    private Stage stage;

    // No-argument constructor (required by JavaFX)
    public ResultScreen(Stage stage) {
        this.stage = stage;
    }

    int count = 0;
    int dayCount = 1;

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
        Label addressLabel = new Label("Trailhead: " + trip.getAddress1());
        Label distancLabel = new Label("Total Distance Traveled: " + trip.calculateTotalDistance() + " km");
        Label trasportationLabel = new Label("Total Transport Time: " + trip.calculateTotalDuration() + " minutes");
        Label sightseeingLabel = new Label("Total Sightseeing Time: " + trip.getMinVisitTime() + " minutes");
        Label ticketsLabel = new Label("Total Tickets Cost: " + trip.getTotalCost() + " €");


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

        List<Sight> sortedSights = trip.getOptimizedSights().stream()
            .sorted(Comparator.comparingInt(Sight::getVisitOrder))  // Assuming getVisitOrder() returns an int
            .collect(Collectors.toList()); 
        
        
        List<Sight> hotelStopPoints = TrailHeadInclusion.findHotelStopPoints(sortedSights);
        List<List<Sight>> sightsPerDay = new ArrayList<>();
        sightsPerDay.add(new ArrayList<>());
        sightsPerDay.get(dayCount -1).add(new Sight("hotel", trip.getAddress1(), 0, 0, "hotel", false));

        for (Sight sight : sortedSights) {
            sightsPerDay.get(dayCount - 1).add(sight);
            boolean hotelStopAfter = false; // Checks if after this sight we need to return to the hotel
            for (Sight s : hotelStopPoints) {
                if (sight == s) {
                    hotelStopAfter = true;
                }
            }

            if (hotelStopAfter) {
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

                Sight hotel = new Sight("hotel", trip.getAddress1(), 0, 0, "hotel", false);
                sightsPerDay.get(dayCount -1).add(hotel);

                // Capture the current day's list in a final variable for the button action
                final List<Sight> currentDaySights = new ArrayList<>(sightsPerDay.get(dayCount - 1));

                Button mapButton2 = new Button("View Map");
                mapButton2.setOnAction(e -> {
                    goToMapScene(currentDaySights);
                });
                optimizedRouteBox.getChildren().add(mapButton2);
                

                dayCount++;
                sightsPerDay.add(new ArrayList<>());
                sightsPerDay.get(dayCount -1).add(hotel);
                Label dayChangeLabel = new Label("Day " + dayCount);
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
        Button mapButton = new Button("View Map");
        mapButton.setOnAction(e -> {
            sightsForMap.add(new Sight("hotel", trip.getAddress1(), 0, 0, "hotel", false));
            goToMapScene(sightsPerDay.get(dayCount -1));
        });
        optimizedRouteBox.getChildren().add(mapButton);

        // Add the optimized route section to the root
        root.getChildren().add(optimizedRouteBox);

        // Scrollability for the optimized route
        ScrollPane scrollPane = new ScrollPane(optimizedRouteBox);
        scrollPane.setFitToWidth(true);
        root.getChildren().add(scrollPane);
    }

    public void goToMapScene(List<Sight> sights) {
        DynamicHtmlCreator screen3 = new DynamicHtmlCreator(stage, sights);
        DynamicHtmlCreator.setTrip(trip); // Pass the trip
        Scene mapScene = screen3.createScene();
        stage.setScene(mapScene);
    }
}