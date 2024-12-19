package com.athensease.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.athensease.sights.Sight;
import com.athensease.sights.Trip;

public class TripDisplayUI extends Application {

    private static Trip trip;  // Static field to hold the trip data

    // No-argument constructor (required by JavaFX)
    public TripDisplayUI() {
    }

    // Static method to launch the UI with the Trip data
    public static void launchUI(Trip tripData) {
        trip = tripData;
        launch();  // Launch the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        // Ensure the trip data is initialized before proceeding
        if (trip == null) {
            throw new IllegalStateException("Trip data not initialized.");
        }

        // Root container for the UI
        VBox root = new VBox(20);  // Increased spacing between elements
        root.setStyle("-fx-padding: 20; -fx-background-color: #fafafa;");

        // Title
        Label titleLabel = new Label("Your Trip Overview");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #003366;");

        // Trip details section
        Label durationLabel = new Label("Duration: " + trip.getDuration() + " days");
        Label budgetLabel = new Label("Budget: €" + trip.getBudget());
        Label addressLabel = new Label("Trailhead: " + trip.getAddress());
        Label categoriesLabel = new Label("Categories of interest: " + trip.getChosenCategories());

        // Sights List
        ListView<String> sightsListView = new ListView<>();
        for (Sight sight : trip.getChosenSights()) {
            sightsListView.getItems().add(sight.toString());
        }

        // Optimized route button
        Button showRouteButton = new Button("Show Optimized Route");
        showRouteButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        showRouteButton.setOnAction(e -> {
            // Remove the button completely from the layout once clicked
            showRouteButton.setVisible(false);  // Make the button invisible instead of removing it

            // Show the optimized route
            displayOptimizedRoute(root);  // Method to add optimized route
        });

        // Layout for trip details
        VBox tripDetailsBox = new VBox(10);
        tripDetailsBox.getChildren().addAll(titleLabel, durationLabel, budgetLabel, addressLabel, categoriesLabel);
        tripDetailsBox.setStyle("-fx-padding: 10;");

        // Layout for the sights
        VBox sightsBox = new VBox(10);
        sightsBox.getChildren().addAll(new Label("Chosen Sights:"), sightsListView, showRouteButton);

        // Add all components to the root
        root.getChildren().addAll(tripDetailsBox, sightsBox);

        // Scene and Stage
        Scene scene = new Scene(root, 700, 600); // Increased scene size
        primaryStage.setTitle("Athens Trip Planner");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to display the optimized route
    private void displayOptimizedRoute(VBox root) {
        // For demo purposes, we just use the chosen sights
        trip.setOptimizedSights(trip.getChosenSights());

        // Create a section to display the optimized route
        VBox optimizedRouteBox = new VBox(15);
        optimizedRouteBox.setStyle("-fx-padding: 20; -fx-background-color: #f0f8ff; -fx-border-color: #003366; -fx-border-width: 2px;");

        Label optimizedRouteLabel = new Label("Optimized Route:");
        optimizedRouteLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #003366;");

        // Add each sight in the optimized route
        optimizedRouteBox.getChildren().add(optimizedRouteLabel);
        for (Sight sight : trip.getOptimizedSights()) {
            VBox sightBox = new VBox(10);
            sightBox.getChildren().addAll(
                new Label("Sight Information:"),
                new Label("Name: " + sight.getName()),
                new Label("Visit Order: " + sight.getVisitOrder()),
                new Label("Price: " + sight.getPrice() + " €"),
                new Label("Visit Time: " + sight.getVisitTime() + " minutes")
            );
            sightBox.setStyle("-fx-background-color: #e8e8ff; -fx-padding: 10; -fx-border-color: #cccccc; -fx-border-width: 1px;");
            optimizedRouteBox.getChildren().add(sightBox);
        }

        // Add the optimized route section to the root
        root.getChildren().add(optimizedRouteBox);

        // Scrollability for the optimized route
        ScrollPane scrollPane = new ScrollPane(optimizedRouteBox);
        scrollPane.setFitToWidth(true);
        root.getChildren().add(scrollPane);
    }
}
