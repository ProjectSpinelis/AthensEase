package com.athensease.ui;

import com.athensease.sights.Trip;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class BudgetScene {

    private int budget;
    private Stage stage;

    private static Trip trip;

    public BudgetScene(Stage stage) {
        this.stage = stage;
    }

    public static void setTrip(Trip trip) {
        BudgetScene.trip = trip;
    }

    public Scene createScene() {
        VBox vbox = new VBox(15); // Κάθετο layout με απόσταση 15px μεταξύ των στοιχείων
        vbox.setStyle("-fx-padding: 20;");

        // Ερώτηση για το budget
        Label question = new Label("How much do you plan to spend for sightseeing and travel? \nPlease enter an integer amount in euros.");

        // Πεδίο εισαγωγής για το budget
        TextField budgetInput = new TextField();
        budgetInput.setPromptText("Enter your budget (in euros)");

        // Ετικέτα για ανατροφοδότηση (feedback)
        Label feedbackLabel = new Label();

        // Δημιουργία κουμπιού Continue
        Button continueButton = new Button("Next");
        continueButton.getStyleClass().add("big-button");

        // Λογική για το Continue
        continueButton.setOnAction(e -> {
            String input = budgetInput.getText().trim(); // Παίρνει την είσοδο
            try {
                // Ελέγχει αν είναι ακέραιος
                budget = Integer.parseInt(input);
                if (budget >= 0) {
                    trip.setBudget(budget); // Καταχωρεί το budget στο ταξίδι
                    feedbackLabel.setText("Budget set to: " + budget + " euros.");
                    feedbackLabel.setStyle("-fx-text-fill: green;");
                    System.out.println("Budget set to: " + budget); // Για debugging ή χρήση στη λογική

                    goToCategoriesScene(); // Μετάβαση στην επόμενη οθόνη
                } else {
                    feedbackLabel.setText("Please enter a positive integer amount.");
                    feedbackLabel.setStyle("-fx-text-fill: red;");
                }
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Please enter a positive integer amount.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // Σύνδεση πλήκτρου Enter με τη μέθοδο υποβολής
        budgetInput.setOnAction(e -> continueButton.fire());

        // Δημιουργία HBox για την τοποθέτηση του κουμπιού Continue κάτω δεξιά
        HBox continueBox = new HBox();
        continueBox.getChildren().add(continueButton);
        continueBox.setAlignment(Pos.CENTER_RIGHT);

        // Προσθήκη των στοιχείων στο VBox
        vbox.getChildren().addAll(question, budgetInput, feedbackLabel, continueBox);

        // Δημιουργία και ρύθμιση σκηνής
        Scene scene = new Scene(vbox, 600, 250);

        // Add the stylesheet to the scene
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        return scene;
    }

    public void goToCategoriesScene() {
        // Μετάβαση στην επόμενη οθόνη
        CategoriesScreen categoriesScene = new CategoriesScreen(stage);
        CategoriesScreen.setTrip(trip);
        Scene scene = categoriesScene.createScene();
        stage.setScene(scene);
    }

}
