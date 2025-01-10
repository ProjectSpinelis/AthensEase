package com.athensease.ui;

import com.athensease.sights.Trip;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CategoriesScreen {
    private static Trip trip;
    private Stage stage;

    public CategoriesScreen(Stage stage) {
        this.stage = stage;
    }

    public static void setTrip(Trip trip) {
        CategoriesScreen.trip = trip;
    }

    public Scene createScene() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15));
    
        Label title = new Label("Select your categories of interest");
        title.getStyleClass().add("heading");
        title.setStyle("-fx-font-size: 28px;");
        vbox.getChildren().add(title);
    
        // Add checkboxes to VBox
        final CheckBox checkbox1 = new CheckBox("MUST-SEE");
        checkbox1.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            trip.getChosenCategories()[0] = new_val;
        });
        vbox.getChildren().add(checkbox1);
    
        final CheckBox checkbox2 = new CheckBox("HISTORY");
        checkbox2.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            trip.getChosenCategories()[1] = new_val;
        });
        vbox.getChildren().add(checkbox2);
    
        final CheckBox checkbox3 = new CheckBox("ART & CULTURE");
        checkbox3.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            trip.getChosenCategories()[2] = new_val;
        });
        vbox.getChildren().add(checkbox3);
    
        final CheckBox checkbox4 = new CheckBox("NATURE & OUTDOORS");
        checkbox4.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            trip.getChosenCategories()[3] = new_val;
        });
        vbox.getChildren().add(checkbox4);
    
        // Clear All and Select All buttons
        Button clearAll = new Button("Clear All");
        clearAll.setOnAction(e -> {
            vbox.getChildren().forEach(node -> {
                if (node instanceof CheckBox) {
                    ((CheckBox) node).setSelected(false);
                }
            });
        });
    
        Button selectAll = new Button("Select All");
        selectAll.setOnAction(e -> {
            vbox.getChildren().forEach(node -> {
                if (node instanceof CheckBox) {
                    ((CheckBox) node).setSelected(true);
                }
            });
        });
    
        // Add Clear All and Select All buttons into an HBox and add it to the VBox
        HBox lastHbox = new HBox(20);
        lastHbox.getChildren().addAll(clearAll, selectAll);
        vbox.getChildren().add(lastHbox);
    
        // Next Button
        Button nextButton = new Button("Next");
        nextButton.getStyleClass().add("big-button");
        nextButton.setOnAction(e -> {
            goToSightsScene();
        });
    
        // Use BorderPane to position the Next button
        BorderPane root = new BorderPane();
        root.setCenter(vbox); // Set VBox with checkboxes and buttons at the center
    
        // Create a HBox to align the Next button to the bottom-right
        HBox hbox = new HBox(nextButton);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);  // Align button to the bottom right
        hbox.setPadding(new Insets(10));  // Add padding around the button (you can adjust this value)
    
        // Set the Next button HBox at the bottom of the BorderPane
        root.setBottom(hbox);
    
        // Create the scene with the BorderPane as the root
        Scene scene = new Scene(root, 500, 450);
    
        // Add the stylesheet to the scene
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    
        return scene;
    }
    
    

    public void goToSightsScene() {
        // Μετάβαση στην οθόνη για τα αξιοθέατα
        SightScene screen3 = new SightScene(stage);
        SightScene.setTrip(trip); // Μεταφορά του ταξιδιού
        Scene sightsScene = screen3.createScene();
        stage.setScene(sightsScene);
    }

    
}
