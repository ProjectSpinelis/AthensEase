/*
 * This class represents the fifth screen (5) in the UI flow for the trip questionnaire.
 * It allows users to specify their budget and provides appropriate feedback for the input.
 * Key functionalities include:
 * 1. Accepting a budget input in euros and validating it as an integer.
 * 2. Providing visual feedback for valid or invalid inputs.
 * 3. Transitioning to the next screen or displaying a completion message based on user selections.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class UI_Screen5 extends Application {

    private boolean isBudgetYesSelected;
    private int budget;

    /*
     * Initializes the screen with the user's budget selection.
     * 
     * @param isBudgetYesSelected indicates whether the user has opted to define a budget.
     */

    public UI_Screen5(boolean isBudgetYesSelected) {
        this.isBudgetYesSelected = isBudgetYesSelected;
    }

    @Override
    public void start(Stage primaryStage) {
        // Set up the primary stage and UI components.

        VBox vbox = new VBox(15); // Vertical layout with 15px spacing between components.
        vbox.setStyle("-fx-padding: 20;");

        // Question label
        Label question = new Label("What is your budget going to be? \nPlease enter an integer for the amount in euros.");
        question.setStyle("-fx-font-size: 14px;");

        // Budget input field
        TextField budgetInput = new TextField();
        budgetInput.setPromptText("Enter your budget (in euros)");

        // Feedback label about the validity of their budget input.
        Label feedbackLabel = new Label();

        // Submit button
        Button submitButton = new Button("Submit");

        // Logic for budget submission
        submitButton.setOnAction(e -> {
            String input = budgetInput.getText().trim(); // Retrieve the user input.
            try {
                // Validate that the input is an integer.
                budget = Integer.parseInt(input);
                feedbackLabel.setText("Budget set to: " + budget + " euros.");
                feedbackLabel.setStyle("-fx-text-fill: green;");
                System.out.println("Budget set to: " + budget); // Log the budget for debugging or further use.
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Please enter a valid integer amount.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // Link the Enter key to the submit action
        budgetInput.setOnAction(e -> submitButton.fire());

        // Continue button to proceed to the next screen or complete the process.
        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-font-weight: bold;");

        // Logic for continue button
        continueButton.setOnAction(e -> {
            if (isBudgetYesSelected) {
                //System.out.println("Proceeding to the next screen...");
                // // Proceed to the next screen or logic
                primaryStage.close(); // Close the current stage.
            } else {
                // Show a completion message.
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Completion");
                alert.setHeaderText(null);
                alert.setContentText("Your process has been successfully completed!");
                alert.showAndWait(); // Wait for user acknowledgment.
                primaryStage.close(); // Close the current stage.
            }
        });

        // HBox for aligning the Continue button to the bottom-right
        HBox continueBox = new HBox();
        continueBox.setStyle("-fx-alignment: center-right; -fx-padding: 10;");
        continueBox.getChildren().add(continueButton);

        // Add components to the VBox layout
        vbox.getChildren().addAll(question, budgetInput, submitButton, feedbackLabel, continueBox);

        // Create and set the scene
        Scene scene = new Scene(vbox, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Budget Input (Screen 5)");
        primaryStage.show();
    }

}
