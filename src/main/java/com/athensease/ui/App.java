package com.athensease.ui;

import com.athensease.sights.SightsFileHandler;
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

public class App extends Application {
    // Πίνακας με τις κατηγορίες που μπορεί να επιλέξει ο χρήστης
    boolean[] selectedCategories = {false, false, false};
    // Αντικείμενο για την ανάγνωση των αξιοθέατων από το αρχείο
    SightsFileHandler sightsHanlder = new SightsFileHandler();
    private Stage stage;
    private static Trip trip;

    public static void setTrip(Trip trip) {
        App.trip = trip;
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        // Load the image
        Image logo = new Image(getClass().getResourceAsStream("/logo.png"));

        // Set the icon of the stage
        stage.getIcons().add(logo);

        // Δημιουργία του κουμπιού
        Button startButton = new Button("Let's START :)");
        startButton.getStyleClass().add("big-button");

        // Add padding around the button
        startButton.setPadding(new Insets(15, 30, 15, 30));  // Top, Right, Bottom, Left padding


        // Δράση όταν πατηθεί το κουμπί
        startButton.setOnAction(e -> {
            goToInputScreen1(); // Μετάβαση στην πρώτη οθόνη
        });

        // Δημιουργία των Labels
        Label welcomeLabel = new Label("Your Personlized Path Through Athens");
        welcomeLabel.getStyleClass().add("heading");
        welcomeLabel.setStyle("-fx-font-size: 28px;");

        Label descriptionLabel = new Label("Explore Athens like never before in 3 thrilling steps:\n\n" +
        "1. Unlock your adventure: Share your trip details, address, and budget.\n\n" +
        "2. Craft your journey: Select the sights you can’t miss.\n\n" +
        "3. Maximize the magic: Get the ultimate route tailored just for you.");

        // Δημιουργία VBox για να τοποθετηθούν τα Labels το ένα κάτω από το άλλο
        VBox vbox = new VBox(20, welcomeLabel, descriptionLabel); // 20px διάστημα μεταξύ των Labels
        vbox.setStyle("-fx-alignment: top-center;");

        // Δημιουργία Region για το κενό διάστημα μεταξύ του κειμένου και του κουμπιού
        Region spacer = new Region();
        VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS); // Ορίζει το spacer να αναπτυχθεί και να γεμίσει το χώρο

        // Δημιουργία VBox για την τοποθέτηση του κουμπιού πιο κοντά στο κείμενο
        VBox mainVBox = new VBox(10, vbox, spacer, startButton);  // 10px διάστημα για μικρότερη απόσταση
        mainVBox.setStyle("-fx-alignment: center;");  // Τοποθετούμε τα στοιχεία κεντρικά

        // Add some padding around the VBox to give space
        mainVBox.setPadding(new Insets(10, 10, 10, 10));  // Padding for the VBox (top, right, bottom, left)

         // Layout setup: Use StackPane to layer elements
         StackPane root = new StackPane();  // Use StackPane to stack the elements
 
         // Add the mainVBox on top of the logoPane (foreground layer)
         root.getChildren().add(mainVBox);

        // Δημιουργία της σκηνής
        Scene scene = new Scene(root, 600, 450);  // Μέγεθος της σκηνής σε 600x600

        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Ρυθμίσεις του παραθύρου
        stage.setTitle("AthensEase");
        stage.setScene(scene);
        stage.show();
    }

    // Μέθοδος για την μετάβαση στην πρώτη οθόνη
    public void goToInputScreen1() {
        // Δημιουργία αντικειμένου για την πρώτη οθόνη
        FirstInputScene inputScreen1 = new FirstInputScene(stage);
        FirstInputScene.setTrip(trip);
        Scene inputScene = inputScreen1.createScene(); // Δημιουργία της σκηνής
        FirstInputScene.setTrip(trip); // Μεταφορά του ταξιδιού
        stage.setScene(inputScene); // Αλλαγή της σκηνής
    }

    public static void main(String[] args) {
        launch(args);
    }
}
