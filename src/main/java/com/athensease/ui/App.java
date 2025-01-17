package com.athensease.ui;

import com.athensease.sights.Trip;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

/**
 * The App class represents the main user interface for the AthensEase application.
 * It initializes the main screen and allows users to begin their journey planning by navigating through multiple scenes.
 * This class also sets up the basic window properties and handles user interactions.
 */
public class App extends Application {

    // Stage object representing the main window
    private Stage stage;
    
    // Static trip object, holds the current trip details
    private static Trip trip;

    /**
     * Sets the current trip object that is used throughout the application.
     * 
     * @param trip The Trip object to set
     */
    public static void setTrip(Trip trip) {
        App.trip = trip;
    }

    /**
     * The start method is called when the application is launched. It sets up the main window 
     * and initializes all UI components such as labels, buttons, and layout.
     *
     * @param stage The main window of the application.
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;

        // Load the application logo
        Image logo = new Image(getClass().getResourceAsStream("/logo.png"));

        // Set the icon of the stage (window)
        stage.getIcons().add(logo);

        // Create the start button
        Button startButton = new Button("Let's START :)");
        startButton.getStyleClass().add("big-button");
        startButton.setPadding(new Insets(15, 30, 15, 30));  // Padding around the button

        // Action handler when the start button is clicked
        startButton.setOnAction(e -> {
            goToInputScreen1(); // Transition to the first input screen
        });

        // Create labels for the welcome message and description
        Label welcomeLabel = new Label("Your Personalized Path Through Athens");
        welcomeLabel.getStyleClass().add("heading");
        welcomeLabel.setStyle("-fx-font-size: 28px;");

        Label descriptionLabel = new Label("Explore Athens like never before in 3 thrilling steps:\n\n" +
        "1. Unlock your adventure: Share your trip details, address, and budget.\n\n" +
        "2. Craft your journey: Select the sights you can’t miss.\n\n" +
        "3. Maximize the magic: Get the ultimate route tailored just for you.");

        // Create a VBox for organizing labels vertically
        VBox vbox = new VBox(20, welcomeLabel, descriptionLabel); // 20px gap between labels
        vbox.setStyle("-fx-alignment: top-center;");

        // Create a Region to add space between the labels and the button
        Region spacer = new Region();
        VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS); // Ensure spacer expands to fill available space

        // Create a main VBox to hold the labels and the button
        VBox mainVBox = new VBox(10, vbox, spacer, startButton);  // 10px gap for compact layout
        mainVBox.setStyle("-fx-alignment: center;");  // Center align elements

        // Add padding around the main VBox to provide spacing
        mainVBox.setPadding(new Insets(10, 10, 10, 10));

        // Set up StackPane layout to layer elements
        StackPane root = new StackPane();
        root.getChildren().add(mainVBox); // Add mainVBox to the root pane

        // Create a scene with specified dimensions
        Scene scene = new Scene(root, 600, 450);  
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());  // Apply external styles

        // Set up window properties
        stage.setTitle("AthensEase");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method handles the transition to the first input screen.
     * It creates a new FirstInputScene object and sets it as the current scene.
     */
    public void goToInputScreen1() {
        // Create and set the first input screen
        FirstInputScene inputScreen1 = new FirstInputScene(stage);
        FirstInputScene.setTrip(trip);
        Scene inputScene = inputScreen1.createScene();
        FirstInputScene.setTrip(trip);
        stage.setScene(inputScene);  // Switch to the input scene
    }

    /**
     * The main entry point for the application. It launches the JavaFX application.
     *
     * @param args Command-line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}
