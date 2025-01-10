package com.athensease.ui;

import com.athensease.sights.Trip;

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

public class FirstInputScene {

    private int duration = -1; // Default value to track invalid input
    private Boolean isOneTrailheadSelected = null; // Tracks selection for Question 2
    private Boolean isBudgetYesSelected = null;   // Tracks selection for Question 3

    private static Trip trip;
    private Stage stage;

    public static void setTrip(Trip trip) {
        FirstInputScene.trip = trip;
    }

    public FirstInputScene(Stage stage) {
        this.stage = stage;
    }

    public Scene createScene() {
        // Create a VBox layout for content
        VBox contentVBox = new VBox(15); // 15px spacing between elements
        contentVBox.setPadding(new Insets(20, 20, 20, 40)); // Add left padding to move content to the right

        // Add a title at the top center
        Label title = new Label("Let's start by getting to know more \nabout your trip!");
        title.setWrapText(true);
        title.getStyleClass().add("heading");
        title.setStyle("-fx-font-size: 28px;");

        // Question 1: Integer input
        Label question1 = new Label("1. How many days are you spending in Athens?");
        TextField durationInput = new TextField();
        durationInput.setPromptText("Enter a number");

        // Question 2: Button choices
        Label question2 = new Label("2. How many trailheads are you going to have? (tip: count your starting points).");
        Button oneTrailButton = new Button("One");
        Button manyTrailsButton = new Button("Many");

        oneTrailButton.setOnAction(e -> {
            System.out.println("Selected: One trailhead");
            isOneTrailheadSelected = true;
            oneTrailButton.setStyle("-fx-background-color: lightblue");
            manyTrailsButton.setStyle("-fx-background-color: #d4b483");
        });

        manyTrailsButton.setOnAction(e -> {
            System.out.println("Selected: Many trailheads");
            isOneTrailheadSelected = false;
            manyTrailsButton.setStyle("-fx-background-color: lightblue");
            oneTrailButton.setStyle("-fx-background-color: #d4b483");
        });

        // Question 3: Button choices
        Label question3 = new Label("3. Are you travelling on a budget?");
        Button budgetYesButton = new Button("Yes");
        Button budgetNoButton = new Button("No");

        budgetYesButton.setOnAction(e -> {
            System.out.println("Selected: Travelling on a budget");
            isBudgetYesSelected = true;
            budgetYesButton.setStyle("-fx-background-color: lightblue");
            budgetNoButton.setStyle("-fx-background-color: #d4b483");
        });

        budgetNoButton.setOnAction(e -> {
            System.out.println("Selected: Not travelling on a budget");
            isBudgetYesSelected = false;
            budgetNoButton.setStyle("-fx-background-color: lightblue");
            budgetYesButton.setStyle("-fx-background-color: #d4b483");
        });

        // Ετικέτα για ανατροφοδότηση (feedback)
        Label feedbackLabel = new Label();

        // Continue button (placed later in the layout)
        Button continueButton = new Button("Next");
        continueButton.getStyleClass().add("big-button");

        continueButton.setOnAction(e -> {
            trip = new Trip();
            // Validate αν έχουν εισαχθεί τα δεδομένα
            try {
                duration = Integer.parseInt(durationInput.getText().trim());
                if (duration > 0) {
                    trip.setDuration(duration);
                } else {
                    feedbackLabel.setText("Trip duration not valid. Please try again!");
                    feedbackLabel.setStyle("-fx-text-fill: red;");
                    return;
                }
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Trip duration not valid. Please try again!");
                feedbackLabel.setStyle("-fx-text-fill: red;");
                return;
            }
        
            if (isOneTrailheadSelected == null) {
                feedbackLabel.setText("Please select an option for trailheads.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
                return;
            }
        
            if (isBudgetYesSelected == null) {
                feedbackLabel.setText("Please select an option for budget.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
                return;
            }
        
            // Μετάβαση στην επόμενη οθόνη
            if (isOneTrailheadSelected) {
                System.out.println("Proceeding to Screen 3...");
                goToTrailheadScene();
            } else {
                /*System.out.println("Proceeding to Screen 4.1...");
                UI_Screen4_1.setTrip(trip);
                UI_Screen4_1 nextScreen = new UI_Screen4_1(duration, isBudgetYesSelected);
                goToUI_Screen4_1(); */
            }
        });
        
        

        // Add all components to the VBox
        contentVBox.getChildren().addAll(
            title,
            question1, durationInput,
            question2, oneTrailButton, manyTrailsButton,
            question3, budgetYesButton, budgetNoButton, feedbackLabel
        );

        // Use a BorderPane to place the VBox at the center and the button at the bottom right
        BorderPane root = new BorderPane();
        root.setCenter(contentVBox);

        // Create a StackPane for the continue button to position it bottom-right
        StackPane continuePane = new StackPane(continueButton);
        StackPane.setMargin(continueButton, new Insets(10)); // Add margin to the button
        continuePane.setAlignment(Pos.BOTTOM_RIGHT);

        // Add the continue button to the BorderPane
        root.setBottom(continuePane);
        Scene scene = new Scene(root, 700, 700);

        // Add the stylesheet to the scene
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        return scene;
    }

    public void goToTrailheadScene() {
        // Μετάβαση στην οθόνη για το trailhead
        TrailheadScene screen3 = new TrailheadScene(stage);
        screen3.setIsYesBudget(isBudgetYesSelected);
        TrailheadScene.setTrip(trip); // Μεταφορά του ταξιδιού
        Scene trailheadScene = screen3.createScene();
        stage.setScene(trailheadScene);
    }
}
