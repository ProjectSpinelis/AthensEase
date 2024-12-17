package com.athensease.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.athensease.sights.Sight;
import com.athensease.sights.Trip;

public class TripDisplayUI extends Application {

    private static Trip trip; // Changed to static for JavaFX initialization compatibility

    public TripDisplayUI() {
        // Default constructor for JavaFX initialization
    }

    // Static method to start the UI with a Trip object
    public static void launchUI(Trip tripToDisplay) {
        trip = tripToDisplay; // Pass the Trip object to the static field
        launch();             // Start JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        if (trip == null) {
            throw new IllegalStateException("Trip data not initialized.");
        }

        // Root container for the UI
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 10;");

        // Title
        Label titleLabel = new Label("Your Trip Overview");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

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

        // Optimized route section
        Button showRouteButton = new Button("Show Optimized Route");
        showRouteButton.setOnAction(e -> displayOptimizedRoute(root));

        // Layout for trip details
        VBox tripDetailsBox = new VBox(5);
        tripDetailsBox.getChildren().addAll(titleLabel, durationLabel, budgetLabel, addressLabel, categoriesLabel);

        // Layout for the sights
        VBox sightsBox = new VBox(5);
        sightsBox.getChildren().addAll(new Label("Chosen Sights:"), sightsListView, showRouteButton);

        // Add all components to the root
        root.getChildren().addAll(tripDetailsBox, sightsBox);

        // Scene and Stage
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("Athens Trip Planner");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to display the optimized route
    private void displayOptimizedRoute(VBox root) {
        // Get optimized sights (this will need to be calculated or fetched in a real app)
        trip.setOptimizedSights(trip.getChosenSights());  // For demo, we just use the chosen sights

        // Create a section to display the optimized route
        VBox optimizedRouteBox = new VBox(10);
        optimizedRouteBox.setStyle("-fx-padding: 10;");

        Label optimizedRouteLabel = new Label("Optimized Route:");
        optimizedRouteLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Add each sight in the optimized route
        for (Sight sight : trip.getOptimizedSights()) {
            optimizedRouteBox.getChildren().add(new Label(sight.toString()));
        }

        // Add the optimized route section to the root
        root.getChildren().add(optimizedRouteBox);
    }
}
