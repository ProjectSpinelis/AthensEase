package com.athensease.ui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.athensease.optimization.Optimizer;
import com.athensease.sights.Trip;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.util.Duration; 

public class ProgressBarScreen {

    private static Trip trip;
    private Stage stage;

    public static void setTrip(Trip trip) {
        ProgressBarScreen.trip = trip;
    }

    public ProgressBarScreen(Stage stage) {
        this.stage = stage;
    }

    public Scene createScene() {

        // Δημιουργία ProgressBar
        ProgressBar progressBar = new ProgressBar(0); // Ξεκινά από 0 (άδειο)
        progressBar.setPrefWidth(400); // Πλάτος της μπάρας
        progressBar.setStyle("-fx-accent: green;"); // Σταθερό πράσινο χρώμα

        // Δημιουργία Label για το "Loading..."
        Label loadingLabel = new Label("Optimizing your trip...");

        // Χρονικό διάστημα και βήμα για την πλήρωση της μπάρας
        int totalDurationInMilliseconds = 25000; // 25 δευτερόλεπτα σε milliseconds
        int frameDurationInMilliseconds = 100; // Κάθε frame διαρκεί 100ms
        double increment = 1.0 / (totalDurationInMilliseconds / frameDurationInMilliseconds);

        // Δημιουργία Task για την εκτέλεση της optimizeTrip σε background thread
        Task<Void> optimizeTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Εκτέλεση της λειτουργίας optimizeTrip
                trip.prepTrip();
                Optimizer.optimizeTrip(trip); // Εκτέλεση σε background thread
                return null;
            }
        };

        // When the optimization is finished, switch to the results screen
        optimizeTask.setOnSucceeded(event -> {
            System.out.println("Optimization finished");
            progressBar.setProgress(1.0); // Πλήρης πλήρωση της ProgressBar
            goToResultsScreen();
        });

        // Timeline για την προοδευτική πλήρωση της ProgressBar
        Timeline timeline = new Timeline(
            new KeyFrame(
                Duration.millis(frameDurationInMilliseconds), // Εκτελείται κάθε 100ms
                event -> {
                    // Αύξηση της τιμής της ProgressBar
                    if (progressBar.getProgress() < 1.0) {
                        progressBar.setProgress(progressBar.getProgress() + increment);
                    }
                    if (progressBar.getProgress() >= 1.0) {
                        goToResultsScreen();
                    }
                }
            )
        );

        timeline.setCycleCount(totalDurationInMilliseconds / frameDurationInMilliseconds);
        timeline.play(); // Ξεκινά το timeline

        // Ενεργοποίηση του Task για την εκτέλεση της optimizeTrip σε background thread
        new Thread(optimizeTask).start();

        // Ρύθμιση Layout
        VBox vbox = new VBox(10, loadingLabel, progressBar); // 10px διάστημα μεταξύ του Label και της ProgressBar
        vbox.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Δημιουργία Scene και Stage
        StackPane root = new StackPane(vbox);
        Scene scene = new Scene(root, 500, 200);

        // Add the stylesheet to the scene
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        return scene;
    }

    // Function to switch to the results screen
    public void goToResultsScreen() {
        ResultScreen screen3 = new ResultScreen(stage);
        ResultScreen.setTrip(trip); // Pass the trip
        Scene resultsScene = screen3.createScene();
        stage.setScene(resultsScene);
    }
}

