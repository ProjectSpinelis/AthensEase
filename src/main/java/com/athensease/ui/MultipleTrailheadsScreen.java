/*
 * This class represents the fourth-second screen (4.2) in the UI flow for the trip questionnaire.
 * It enables users to specify the starting and finish points for selected change days.
 * Key functionalities include:
 * 1. Displaying a table of the selected change days from the previous screen (4.1).
 * 2. Allowing users to input and edit starting and finish points for each change day.
 * 3. Automatically copying starting points to finish points if the latter is left empty.
 * 4. Transitioning to the next screen (5) or finalizing the process based on user selections.
 */

 import java.util.ArrayList;
 import java.util.List;
 
 import javafx.application.Application;
 import javafx.collections.FXCollections;
 import javafx.collections.ObservableList;
 import javafx.scene.Scene;
 import javafx.scene.control.*;
 import javafx.scene.layout.*;
 import javafx.stage.Stage;
 
 public class MultipleTrailheadsScreen {

    private int duration; // Total duration of the trip (from Screen 2)
    private ObservableList<Integer> changeDays;
    private ObservableList<String> startingPoints;
    private ObservableList<String> finishPoints;
    private boolean isBudgetYesSelected;
    private Trip trip; // Instance of the Trip class to store trailheads

    // Constructor for initializing the UI_Screen4_2 instance
    public UI_Screen4_2(int duration, ObservableList<Integer> changeDays, boolean isBudgetYesSelected) {
        this.duration = duration;
        this.changeDays = changeDays;
        this.isBudgetYesSelected = isBudgetYesSelected;
        this.startingPoints = FXCollections.observableArrayList();
        this.finishPoints = FXCollections.observableArrayList();

        // Initialize lists with empty strings
        for (int i = 0; i < changeDays.size(); i++) {
            this.startingPoints.add("");
            this.finishPoints.add("");
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Debugging: Print transferred data
        System.out.println("Change Days: " + changeDays);
        System.out.println("Is Budget Yes Selected: " + isBudgetYesSelected);

        // Restrict list size to a maximum of 3 elements
        while (changeDays.size() > 3) {
            changeDays.remove(changeDays.size() - 1);
        }
        while (startingPoints.size() > 3) {
            startingPoints.remove(startingPoints.size() - 1);
        }
        while (finishPoints.size() > 3) {
            finishPoints.remove(finishPoints.size() - 1);
        }

        // Create table for displaying change days
        TableView<Integer> changeDaysTable = new TableView<>();
        changeDaysTable.setPrefWidth(120); 

        TableColumn<Integer, Integer> changeDaysColumn = new TableColumn<>("Change Days");
        changeDaysColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue()));
        changeDaysColumn.setPrefWidth(120); 
        changeDaysTable.getColumns().add(changeDaysColumn);
        changeDaysTable.setItems(changeDays);

        changeDaysTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Remove empty column
        changeDaysTable.setEditable(false); 
        changeDaysTable.setMaxHeight(130); // Remove lines not needed

        // Create input fields for starting points
        VBox startingPointsBox = new VBox(5);
        startingPointsBox.setPrefWidth(200); 
        for (int i = 0; i < changeDays.size(); i++) {
            TextField textField = new TextField();
            int index = i;
            textField.setText(startingPoints.get(index));
            textField.textProperty().addListener((obs, oldText, newText) -> startingPoints.set(index, newText));
            startingPointsBox.getChildren().add(textField);
        }

        // Create input fields for finish points
        VBox finishPointsBox = new VBox(5);
        finishPointsBox.setPrefWidth(200); 
        for (int i = 0; i < changeDays.size(); i++) {
            TextField textField = new TextField();
            int index = i;
            textField.setText(finishPoints.get(index));
            textField.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
                if (!isFocused && textField.getText().isEmpty()) {
                    // If it's empty, copy from Starting Points
                    textField.setText(startingPoints.get(index));
                    finishPoints.set(index, startingPoints.get(index));
                }
            });
            textField.textProperty().addListener((obs, oldText, newText) -> finishPoints.set(index, newText));
            finishPointsBox.getChildren().add(textField);
        }

        // Continue button to move to the next screen
        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-font-weight: bold; -fx-padding: 10px;");
        continueButton.setOnAction(e -> {
            // Replace empty finishPoints with startingPoints
            for (int i = 0; i < finishPoints.size(); i++) {
                if (finishPoints.get(i).isEmpty()) {
                    finishPoints.set(i, startingPoints.get(i));
                }
            }
            
            // Debugging : Print the lists of startingPoints and finishPoints
            System.out.println("Starting Points: " + startingPoints);
            System.out.println("Finish Points: " + finishPoints);

            // Calculate the trailheadsDays list
            List<Integer> trailheadsDays = new ArrayList<>();
            for (int day = 1; day <= duration; day++) {
                int trailhead = 1;
                for (int changeDay : changeDays) {
                    if (day > changeDay) {
                        trailhead++;
                    }
                }
                trailheadsDays.add(trailhead);
            }

            // Store the trailheadsDays in the Trip object
            trip = new Trip(trailheadsDays);

            // Debugging: Print the trailheadsDays list
            System.out.println("Trailheads Days: " + trailheadsDays);

            if (isBudgetYesSelected) {
                System.out.println("Proceeding to Screen 5...");
                UI_Screen5 screen5 = new UI_Screen5(isBudgetYesSelected);
                try {
                    screen5.start(primaryStage); // Use same primaryStage
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                // Show completion message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Completion");
                alert.setHeaderText(null);
                alert.setContentText("Your process has been successfully completed!");
                alert.showAndWait();
                primaryStage.close(); // Close the current stage
            }
        });

        // Labels with bold for tables
        Label startingPointsLabel = new Label("Starting Points");
        startingPointsLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label finishPointsLabel = new Label("Finish Points");
        finishPointsLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Layout for tables
        HBox tablesBox = new HBox(20); // Place tables aligned
        tablesBox.getChildren().addAll(
                new VBox(changeDaysTable),
                new VBox(startingPointsLabel, startingPointsBox),
                new VBox(finishPointsLabel, finishPointsBox)
        );

        // Layout to position the Continue button at the bottom-right
        HBox buttonBox = new HBox(continueButton);
        buttonBox.setStyle("-fx-alignment: bottom-right;");

        // Create the main layout
        VBox mainLayout = new VBox(20, new Label("Enter Starting and Finish Points for Change Days"), tablesBox, buttonBox);
        mainLayout.setStyle("-fx-padding: 20;");

        // Create and set the scene
        Scene scene = new Scene(mainLayout, 600, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Screen 4.2 - Change Days");
        primaryStage.show();
    }
 
 }