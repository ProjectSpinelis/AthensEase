package com.athensease.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.athensease.dataretrieval.ApiHandler;
import com.athensease.sights.Trip;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TrailheadScene {

    private String trailhead; // Variable to store trailhead input
    private boolean isBudgetYesSelected;
    private Stage stage;

    private static Trip trip;

    public TrailheadScene(Stage stage) {
        this.stage = stage;
    }

    public static void setTrip(Trip trip) {
        TrailheadScene.trip = trip;
    }

    public void setIsYesBudget(boolean isBudgetYesSelected) {
        this.isBudgetYesSelected = isBudgetYesSelected; // Αντικαθιστά το isYesBudget με isBudgetYesSelected
    }    


    public Scene createScene() {
        // Top layout for question and submit
        VBox topLeftVBox = new VBox(10);
        topLeftVBox.setPadding(new Insets(10, 10, 0, 20)); // Add padding for spacing
        topLeftVBox.setAlignment(Pos.TOP_LEFT);

        // Title
        Label title = new Label("Please enter your trailhead below:\nExample: Christou Lada 2, Athina 105 61, Greece");

        // Trailhead input
        TextField trailheadInput = new TextField();
        trailheadInput.setPromptText("Enter your trailhead");

        // Store user input in real time
        trailheadInput.textProperty().addListener((observable, oldValue, newValue) -> {
            trailhead = newValue;
        });

        // Ετικέτα για ανατροφοδότηση (feedback)
        Label feedbackLabel = new Label();

        // Add title, input, and submit button to top-left layout
        topLeftVBox.getChildren().addAll(title, trailheadInput, feedbackLabel);

        // Bottom layout for Continue button
        HBox bottomHBox = new HBox();
        bottomHBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomHBox.setPadding(new Insets(10, 20, 10, 20));

        // Continue button
        Button continueButton = new Button("Next");
        continueButton.getStyleClass().add("big-button");
        continueButton.setOnAction(e -> {
            if (trailhead != null && !trailhead.trim().isEmpty()) {
                // Call API handler to check address
                try {
                    List<String> origin = new ArrayList<>();
                    List<String> destination = new ArrayList<>();
                    origin.add(trailhead);
                    destination.add("Mousio Akropoleos, Dionysiou Areopagitou 15, Athina 117 42, Greece");
                    ApiHandler handler = new ApiHandler(origin, destination);
                    String url = handler.createURL();
                    String response = handler.getResponse(url);
                    double distance = handler.extractField(response, "distance"); // Assume this returns a double
                    double duration = handler.extractField(response, "duration"); // Assume this returns a double

                    trip.setAddress1(trailhead);
                    System.out.println("Trailhead accepted: " + trailhead);
                    if (isBudgetYesSelected) {
                        System.out.println("Proceeding to Screen 5...");
                        goToBudgetScene();
                    } else {
                        trip.setBudget(Integer.MAX_VALUE); // Set budget to max value
                        System.out.println("You're all set!");
                        goToCategoriesScene();
                    }
                } catch (IOException | RuntimeException ex) {
                    feedbackLabel.setText("Trailhead not found. Please enter a valid trailhead.");
                    feedbackLabel.setStyle("-fx-text-fill: red;");
                }
                
            } else {
                feedbackLabel.setText("Please enter a valid trailhead to continue.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // Add Enter key functionality to Submit
        trailheadInput.setOnAction(e -> continueButton.fire());

        // Add Continue button to bottom layout
        bottomHBox.getChildren().add(continueButton);

        // Main layout
        BorderPane root = new BorderPane();
        root.setTop(topLeftVBox); // Place top-left content at the top
        root.setBottom(bottomHBox); // Place Continue button at the bottom

        // Create and set the scene
        Scene scene = new Scene(root, 500, 300);

        // Add the stylesheet to the scene
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        return scene;
    }

    public void goToBudgetScene() {
        // Μετάβαση στην οθόνη για το budget
        BudgetScene screen5 = new BudgetScene(stage);
        BudgetScene.setTrip(trip); // Μεταφορά του ταξιδιού
        Scene budgetScene = screen5.createScene();
        stage.setScene(budgetScene);
    }

    public void goToCategoriesScene() {
        // Μετάβαση στην οθόνη κατηγοριών
        CategoriesScreen catScreen = new CategoriesScreen(stage);
        CategoriesScreen.setTrip(trip); // Μεταφορά του ταξιδιού
        Scene catScene = catScreen.createScene();
        stage.setScene(catScene);
    }

}
