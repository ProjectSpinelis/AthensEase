package com.athensease.ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class UI_Screen4_2 extends Application {

    private ObservableList<Integer> changeDays;
    private ObservableList<String> startingPoints;
    private ObservableList<String> finishPoints;
    private boolean isBudgetYesSelected;

    public UI_Screen4_2(ObservableList<Integer> changeDays, boolean isBudgetYesSelected) {
        this.changeDays = changeDays;
        this.isBudgetYesSelected = isBudgetYesSelected;
        this.startingPoints = FXCollections.observableArrayList();
        this.finishPoints = FXCollections.observableArrayList();
        for (int i = 0; i < changeDays.size(); i++) {
            this.startingPoints.add("");
            this.finishPoints.add("");
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Debugging: Εκτύπωση δεδομένων που μεταφέρθηκαν
        System.out.println("Change Days: " + changeDays);
        System.out.println("Is Budget Yes Selected: " + isBudgetYesSelected);

        // Πίνακας Change Days
        TableView<Integer> changeDaysTable = new TableView<>();
        changeDaysTable.setPrefWidth(120); // Ρύθμιση πλάτους

        TableColumn<Integer, Integer> changeDaysColumn = new TableColumn<>("Change Days");
        changeDaysColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue()));
        changeDaysColumn.setPrefWidth(120); // Ρύθμιση πλάτους
        changeDaysTable.getColumns().add(changeDaysColumn);
        changeDaysTable.setItems(changeDays);

        changeDaysTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Αφαίρεση κενής στήλης
        changeDaysTable.setEditable(false); // Μόνο για προβολή
        changeDaysTable.setMaxHeight(130); // Αφαίρεση περιττών γραμμών

        // Starting Points (TextFields για επεξεργασία)
        VBox startingPointsBox = new VBox(5);
        startingPointsBox.setPrefWidth(200); // Ρύθμιση πλάτους
        for (int i = 0; i < changeDays.size(); i++) {
            TextField textField = new TextField();
            int index = i;
            textField.setText(startingPoints.get(index));
            textField.textProperty().addListener((obs, oldText, newText) -> startingPoints.set(index, newText));
            startingPointsBox.getChildren().add(textField);
        }

        // Finish Points (TextFields για επεξεργασία)
        VBox finishPointsBox = new VBox(5);
        finishPointsBox.setPrefWidth(200); // Ρύθμιση πλάτους
        for (int i = 0; i < changeDays.size(); i++) {
            TextField textField = new TextField();
            int index = i;
            textField.setText(finishPoints.get(index));
            textField.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
                if (!isFocused && textField.getText().isEmpty()) {
                    // Αν είναι κενό, αντιγραφή από Starting Points
                    textField.setText(startingPoints.get(index));
                    finishPoints.set(index, startingPoints.get(index));
                }
            });
            textField.textProperty().addListener((obs, oldText, newText) -> finishPoints.set(index, newText));
            finishPointsBox.getChildren().add(textField);
        }

        // Κουμπί Continue
        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-font-weight: bold; -fx-padding: 10px;");
        continueButton.setOnAction(e -> {
            // Αντικατάσταση κενών finishPoints με startingPoints
            for (int i = 0; i < finishPoints.size(); i++) {
                if (finishPoints.get(i).isEmpty()) {
                    finishPoints.set(i, startingPoints.get(i));
                }
            }
            
            // Εμφάνιση δεδομένων για έλεγχο
            System.out.println("Starting Points: " + startingPoints);
            System.out.println("Finish Points: " + finishPoints);
            
            if (isBudgetYesSelected) {
                System.out.println("Proceeding to Screen 5...");
                BudgetScene screen5 = new BudgetScene(isBudgetYesSelected);
                try {
                    screen5.start(primaryStage); // Χρήση του ίδιου primaryStage
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                // Εμφάνιση μηνύματος επιτυχούς ολοκλήρωσης
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Completion");
                alert.setHeaderText(null);
                alert.setContentText("Your process has been successfully completed!");
                alert.showAndWait();
                primaryStage.close(); // Κλείνουμε την τρέχουσα σκηνή
            }
        });
        

        // Labels με bold για τους πίνακες
        Label startingPointsLabel = new Label("Starting Points");
        startingPointsLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label finishPointsLabel = new Label("Finish Points");
        finishPointsLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Layout για τους πίνακες
        HBox tablesBox = new HBox(20); // Ευθυγράμμιση σε μία γραμμή
        tablesBox.getChildren().addAll(
                new VBox(changeDaysTable),
                new VBox(startingPointsLabel, startingPointsBox),
                new VBox(finishPointsLabel, finishPointsBox)
        );

        // Layout για το κουμπί
        HBox buttonBox = new HBox(continueButton);
        buttonBox.setStyle("-fx-alignment: bottom-right;");

        // Κεντρικό layout
        VBox mainLayout = new VBox(20, new Label("Enter Starting and Finish Points for Change Days"), tablesBox, buttonBox);
        mainLayout.setStyle("-fx-padding: 20;");

        // Σκηνή
        Scene scene = new Scene(mainLayout, 600, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Screen 4.2 - Change Days");
        primaryStage.show();
    }

}
