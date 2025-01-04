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

    private int duration; // Διάρκεια (παίρνεται από την Screen2)
    private List<Integer> changeDays = new ArrayList<>();
    private boolean isBudgetYesSelected;

    // Constructor για τη μεταφορά του duration, isBudgetYesSelected
    public UI_Screen4_1(int duration, boolean isBudgetYesSelected) {
        this.duration = duration;
        this.isBudgetYesSelected = isBudgetYesSelected;
    }

    public void start(Stage primaryStage) {
        // Ensure day 1 is always included
        changeDays.add(1); // Αρχικοποίηση με την ημέρα 1

        // Create a VBox layout for content
        VBox contentVBox = new VBox(15);
        contentVBox.setStyle("-fx-padding: 20;");

        // Title for 4.1
        Label durationLabel = new Label("Your holiday is going to last " + duration + " days.");
        durationLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Instructions
        Label instructionLabel = new Label(
                "On which of those days will you change your starting point?\n" +
                        "We have already included day 1 for you, please continue with the rest.\n" +
                            "Integers divided by comma (,) are expected."
        );

        // Input field for changeDays
        TextField changeDaysInput = new TextField();
        changeDaysInput.setPromptText("Enter days (e.g., 2, 4)");
        changeDaysInput.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                // Parse input and add to the list
                String[] inputs = newValue.split(",");
                List<Integer> userDays = new ArrayList<>();
                for (String input : inputs) {
                    int day = Integer.parseInt(input.trim());
                    if (day > 1 && day <= duration) { // Valid days only
                        userDays.add(day);
                    }
                }

                // Clear the list and ensure day 1 is always present
                changeDays.clear();
                changeDays.add(1); // Always include day 1
                changeDays.addAll(userDays); // Add user input days
            } catch (NumberFormatException e) {
                // Handle invalid input by resetting to only day 1
                changeDays.clear();
                changeDays.add(1);
            }
        });

        // Button to submit changes
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            int totalChanges = changeDays.size(); // Include Day 1 (already added)
            System.out.println("Days entered: " + changeDays); // Print the list with Day 1 included
            System.out.println("Total trailhead changes: " + totalChanges);
        });

        // Add elements to layout
        contentVBox.getChildren().addAll(durationLabel, instructionLabel, changeDaysInput, submitButton);

        // Create Continue button
        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        continueButton.setOnAction(e -> {
            if (changeDays.isEmpty()) {
                changeDays.add(1); // Διασφάλιση ότι η λίστα περιέχει τουλάχιστον την ημέρα 1
            }
        
            // Debugging: Εκτύπωση των δεδομένων που μεταφέρονται
            System.out.println("Transitioning to Screen 4.2 with changeDays: " + changeDays);
        
            // Transition to Screen 4.2 with the changeDays list and isBudgetYesSelected
            try {
                UI_Screen4_2 screen4_2 = new UI_Screen4_2(FXCollections.observableArrayList(changeDays), isBudgetYesSelected);
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
