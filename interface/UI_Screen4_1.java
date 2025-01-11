/*
 * This class represents the fourth-one screen (4.1) in the UI flow for the trip questionnaire.
 * It allows users to specify the days during their trip when they will change their trailhead.
 * Key functionalities include:
 * 1. Displaying the total duration of the trip and instructions for input.
 * 2. Validating and updating the list of days for trailhead changes.
 * 3. Transitioning to the next screen (4.2) with the collected data.
 */

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class UI_Screen4_1 {

    private int duration; // Total duration of the trip (from Screen 2)
    private List<Integer> changeDays = new ArrayList<>(); //Days of trailhead changes
    private boolean isBudgetYesSelected; // Indicates if the user is travelling on a budget

    /*
     * Constructor to initialize the screen with trip duration and budget preference.
     * 
     * @param duration The total number of days in the trip.
     * @param isBudgetYesSelected A boolean indicating if the user is traveling on a budget.
     */

    public UI_Screen4_1(int duration, boolean isBudgetYesSelected) {
        this.duration = duration;
        this.isBudgetYesSelected = isBudgetYesSelected;
    }

    /*
     * Starts the UI for Screen 4.1, displaying the input field for trailhead change days.
     * 
     * @param primaryStage The primary stage for displaying the UI.
     */

    public void start(Stage primaryStage) {
        // Ensure day 1 is always included
        changeDays.add(1); // Initial inclusion of day 1

        // Create a VBox layout for content
        VBox contentVBox = new VBox(15);
        contentVBox.setStyle("-fx-padding: 20;");

        // Title displaying the duration of the trip
        Label durationLabel = new Label("Your holiday is going to last " + duration + " days.");
        durationLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Instructions for entering trailhead change days
        Label instructionLabel = new Label(
                "On which of those days will you change your starting point?\n" +
                        "We have already included day 1 for you, please continue with the rest.\n" +
                            "Maximum 2 integers divided by comma (,) are expected." +
                                "(Up to 3 different trailheads total.)"
        );

        // Input field for change days
        TextField changeDaysInput = new TextField();
        changeDaysInput.setPromptText("Enter days (e.g., 2, 4)");
        changeDaysInput.textProperty().addListener((observable, oldValue, newValue) -> {
        boolean hasInvalidInput = false; // Flag for invalid day
        List<Integer> userDays = new ArrayList<>();
            try {
                // Parse input and add valid days to the list
                String[] inputs = newValue.split(",");
                for (String input : inputs) {
                    int day = Integer.parseInt(input.trim());
                    if (day > 1 && day <= duration) { // Valid days only
                        userDays.add(day);
                    } else {
                        hasInvalidInput = true; // day not valid
                    }
                }

                // Clear the list and ensure day 1 is always included
                changeDays.clear();
                changeDays.add(1); // Always include day 1
                changeDays.addAll(userDays); // Add user-input days
            } catch (NumberFormatException e) {
                hasInvalidInput = true; // Invalid input
            }

            // Create valid text
            String validText = userDays.isEmpty() ? "" : userDays.toString().replaceAll("[\\[\\] ]", "");


            // Reset to default list with only day 1 on invalid input
            changeDays.clear();
            changeDays.add(1);
            changeDays.addAll(userDays);

            // Update TextField with valid days
            changeDaysInput.setText(changeDays.subList(1, changeDays.size()).toString().replaceAll("[\\[\\] ]", ""));

            if (!newValue.equals(validText)) {
                changeDaysInput.setText(validText);
            }

            // Printed when invalid input
            if (hasInvalidInput) {
                System.out.println("Invalid input. Please enter valid days (1-" + duration + ").");
            }
        });

        // Submit button to validate and display entered days
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            int totalChanges = changeDays.size(); // Include Day 1 (already added)
            System.out.println("Days entered: " + changeDays); // Print the entered days, plus day 1
            System.out.println("Total trailhead changes: " + totalChanges);
        });

        // Add elements to layout
        contentVBox.getChildren().addAll(durationLabel, instructionLabel, changeDaysInput, submitButton);

        // Continue button to move to the next screen
        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        continueButton.setOnAction(e -> {
            if (changeDays.isEmpty()) {
                changeDays.add(1); // Ensure day 1 is always included
            }
        
            // Debugging: Transition to Screen 4.2 with collected data
            System.out.println("Transitioning to Screen 4.2 with changeDays: " + changeDays);
        
            // Transition to Screen 4.2 with the changeDays list and isBudgetYesSelected
            try {
                UI_Screen4_2 screen4_2 = new UI_Screen4_2(duration, FXCollections.observableArrayList(changeDays), isBudgetYesSelected);
                screen4_2.start(new Stage());
                primaryStage.close(); // Close the current stage
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });        
        

        // Layout to position the Continue button at the bottom-right
        StackPane continuePane = new StackPane(continueButton);
        StackPane.setMargin(continueButton, new Insets(10));
        continuePane.setAlignment(Pos.BOTTOM_RIGHT);

        // Use a BorderPane to structure the layout
        BorderPane root = new BorderPane();
        root.setCenter(contentVBox); // Center the main content
        root.setBottom(continuePane); // Place Continue button at bottom-right

        // Create and set the scene
        Scene scene = new Scene(root, 700, 300); // Increased height
        primaryStage.setScene(scene);
        primaryStage.setTitle("Trailhead Changes (4.1)");
        primaryStage.show();
    }
}
