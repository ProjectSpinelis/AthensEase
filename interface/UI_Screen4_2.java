import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class UI_Screen4_2 extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Λίστα Change Days (Παράδειγμα δεδομένων)
        ObservableList<Integer> changeDays = FXCollections.observableArrayList(1, 2, 3);

        // Λίστες για Starting Points και Finish Points
        ObservableList<String> startingPoints = FXCollections.observableArrayList("", "", "");
        ObservableList<String> finishPoints = FXCollections.observableArrayList("", "", "");

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
            // Μετάβαση στην οθόνη 5 (Προσαρμόστε κατάλληλα)
            System.out.println("Starting Points: " + startingPoints);
            System.out.println("Finish Points: " + finishPoints);
            UI_Screen5 screen5 = new UI_Screen5();
            screen5.start(new Stage());
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

    public static void main(String[] args) {
        launch(args);
    }
}
