package com.athensease.ui;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Desktop;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.athensease.sights.Sight;
import com.athensease.sights.SightsFileHandler;
import com.athensease.sights.Trip;

public class SightScene {

    private static Trip trip;
    private Stage stage;

    public static void setTrip(Trip trip) {
        SightScene.trip = trip;
    }

    public SightScene(Stage stage) {
        this.stage = stage;
    }

    public Scene createScene() {
        
        // Create VBox and ScrollPane
        VBox vbox = new VBox(10);
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToHeight(true);
        
        // Ensure the VBox stretches horizontally
        vbox.setMaxWidth(Double.MAX_VALUE);

        Label title = new Label("Select the sights you want to visit");
        title.getStyleClass().add("heading");
        title.setStyle("-fx-font-size: 28px;");
        vbox.getChildren().add(title);
        
        SightsFileHandler sightsHanlder = new SightsFileHandler();
        
        // For each sight
        for (Sight sight : sightsHanlder.getSights()) {
            boolean catSelected = trip.getChosenCategories()[Integer.parseInt(sight.getCategory())];

            if (catSelected || (trip.getChosenCategories()[0] && sight.isMustSee())) {
                final CheckBox checkbox = new CheckBox(sight.getName());
                
                // Checkbox listener
                checkbox.selectedProperty().addListener(
                        (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                            if (new_val) {
                                trip.getChosenSights().add(sight);
                            } else {
                                trip.getChosenSights().remove(sight);
                            }
                        });
                
                Label priceLabel = new Label("Ticket Cost: " + sight.getPrice() + "€");
                Label visitTimeLabel = new Label("Visit Duration: " + sight.getVisitTime() + " minutes");
                Label categoryLabel = new Label("Category: " + sight.getCategory());

                Hyperlink link = new Hyperlink(sight.getLink());
                link.setText("Learn More");
                link.setOnAction(e -> {
                    if (Desktop.isDesktopSupported()) {
                        try {
                            Desktop.getDesktop().browse(new URI(sight.getLink()));
                        } catch (IOException | URISyntaxException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                // Create HBox for each sight
                HBox hbox = new HBox(10, checkbox, categoryLabel, visitTimeLabel, priceLabel, link);
                hbox.setStyle("background-color: #d4b483; -fx-padding: 10; -fx-border-color: #cccccc; -fx-border-width: 1px;");
                
                if (sight.isMustSee()) {
                    Label mustSeeLabel = new Label("Must-See");
                    mustSeeLabel.setStyle("-fx-background-color: lightblue;");
                    hbox.getChildren().add(mustSeeLabel);
                }

                hbox.setSpacing(10);
                hbox.setMaxWidth(Double.MAX_VALUE); // Ensure HBox stretches horizontally
                VBox.setVgrow(hbox, Priority.ALWAYS); // Ensure VBox grows to take full width

                vbox.getChildren().add(hbox);
            }
        }

        // Clear All button functionality
        Button clearAll = new Button("Clear All");
        clearAll.setOnAction(e -> {
            vbox.getChildren().forEach(node -> {
                if (node instanceof HBox) { // Ensure the node is an HBox
                    HBox hbox = (HBox) node;
                    if (!hbox.getChildren().isEmpty() && hbox.getChildren().get(0) instanceof CheckBox) { 
                        CheckBox cb = (CheckBox) hbox.getChildren().get(0);
                        cb.setSelected(false);
                    }
                }
            });
        });

        // Select All button functionality
        Button selectAll = new Button("Select All");
        selectAll.setOnAction(e -> {
            vbox.getChildren().forEach(node -> {
                if (node instanceof HBox) { // Ensure the node is an HBox
                    HBox hbox = (HBox) node;
                    if (!hbox.getChildren().isEmpty() && hbox.getChildren().get(0) instanceof CheckBox) { 
                        CheckBox cb = (CheckBox) hbox.getChildren().get(0);
                        cb.setSelected(true);
                    }
                }
            });
        });

        //Hbox for buttons
        HBox Hbox2 = new HBox(20);
        Hbox2.getChildren().addAll(selectAll, clearAll);

        Button distanceButton = new Button("Minize travel distance");
        Button durationButton = new Button("Minize travel duration");

        durationButton.setOnAction(e -> {
            trip.setOptmizeFor(0);
            durationButton.setStyle("-fx-background-color: lightblue");
            distanceButton.setStyle("-fx-background-color: #d4b483");
        });

        distanceButton.setOnAction(e -> {
            trip.setOptmizeFor(1);
            distanceButton.setStyle("-fx-background-color: lightblue");
            durationButton.setStyle("-fx-background-color: #d4b483");
        });
        
        Hbox2.getChildren().addAll(durationButton, distanceButton);
        Hbox2.setMaxWidth(Double.MAX_VALUE);
        vbox.getChildren().add(Hbox2);

        //Optimize button functionality
        Button optimizeButton = new Button("Optimize");
        optimizeButton.getStyleClass().add("big-button");
        optimizeButton.setOnAction(e -> {
            goToProgessBarScene();
        });
        HBox hbox3 = new HBox(optimizeButton);
        hbox3.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        vbox.getChildren().add(hbox3);

        // Set up BorderPane
        BorderPane root = new BorderPane();
        root.setCenter(scrollPane);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 850, 600);

        // Add the stylesheet to the scene
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        return scene;
    }

    // Function to switch to the progress bar scene
    public void goToProgessBarScene() {
        ProgressBarScreen screen3 = new ProgressBarScreen(stage);
        ProgressBarScreen.setTrip(trip); // Pass the trip
        Scene progressBarScene = screen3.createScene();
        stage.setScene(progressBarScene);
    }
}
