package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ProgressBarApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Δημιουργία ProgressBar
        ProgressBar progressBar = new ProgressBar(0); // Ξεκινά από 0 (άδειο)
        progressBar.setPrefWidth(400); // Πλάτος της μπάρας
        progressBar.setStyle("-fx-accent: green;"); // Σταθερό πράσινο χρώμα

        // Δημιουργία Label για το "Loading..."
        Label loadingLabel = new Label("Loading...");
        loadingLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");

        // Χρονικό διάστημα και βήμα για την πλήρωση της μπάρας
        int totalDurationInMilliseconds = 30000; // 30 δευτερόλεπτα σε milliseconds
        int frameDurationInMilliseconds = 100; // Κάθε frame διαρκεί 100ms
        double increment = 1.0 / (totalDurationInMilliseconds / frameDurationInMilliseconds);

        // Timeline για την προοδευτική πλήρωση της ProgressBar
        Timeline timeline = new Timeline(
            new KeyFrame(
                Duration.millis(frameDurationInMilliseconds), // Εκτελείται κάθε 100ms
                event -> {
                    // Αύξηση της τιμής της ProgressBar
                    if (progressBar.getProgress() < 1.0) {
                        progressBar.setProgress(progressBar.getProgress() + increment);
                    }
                }
            )
        );

        timeline.setCycleCount(totalDurationInMilliseconds / frameDurationInMilliseconds); // Ρυθμίζεται για 30 δευτερόλεπτα
        timeline.play(); // Ξεκινά το timeline

        // Ρύθμιση Layout
        VBox vbox = new VBox(10, loadingLabel, progressBar); // 10px διάστημα μεταξύ του Label και της ProgressBar
        vbox.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Δημιουργία Scene και Stage
        StackPane root = new StackPane(vbox);
        Scene scene = new Scene(root, 500, 200);
        primaryStage.setTitle("ProgressBar Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

