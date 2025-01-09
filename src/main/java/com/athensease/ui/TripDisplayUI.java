package com.athensease.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.athensease.sights.Sight;
import com.athensease.sights.Trip;

/**
 * The {@code TripDisplayUI} class is a JavaFX application that provides a user interface (UI) to display details
 * of a trip, including trip information, chosen sights, and an optimized route.
 * This class serves as the visual representation for the trip data, displaying it in an organized layout.
 * The UI allows the user to interact with the trip data and view the optimized route after clicking a button.
 *
 * Key features of the class include:
 * <ul>
 *     <li>Displaying general trip details such as duration, budget, and trailhead.</li>
 *     <li>Displaying a list of the chosen sights for the trip.</li>
 *     <li>Allowing the user to view an optimized route by pressing a button, which removes the button from the UI and displays the route information.</li>
 * </ul>
 */
public class TripDisplayUI extends Application {

    private static Trip trip;  // Static field to hold the trip data

    /**
     * No-argument constructor (required by JavaFX).
     */
    public TripDisplayUI() {
    }

    /**
     * Static method to launch the UI with the provided trip data.
     * 
     * @param tripData the trip data to display in the UI
     */
    public static void launchUI(Trip tripData) {
        trip = tripData;
        launch();  // Launch the JavaFX application
    }

    /**
     * Initializes the JavaFX UI. This method is called by the JavaFX runtime when the application starts.
     * It builds the UI elements and sets up the layout for displaying trip details and an optimized route.
     *
     * @param primaryStage the primary stage for the JavaFX application
     * @throws IllegalStateException if the trip data has not been initialized
     */
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

    /**
     * Displays the optimized route of the trip, which includes a list of sights with detailed information such as
     * name, visit order, price, and visit time.
     * The optimized route is displayed after the user clicks the button to reveal it.
     * 
     * @param root the root container of the UI to which the optimized route is added
     */
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
