import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UI_Screen3 extends Application {

    private String trailhead; // Variable to store trailhead input
    private boolean isBudgetYesSelected;

    public void setIsYesBudget(boolean isBudgetYesSelected) {
        this.isBudgetYesSelected = isBudgetYesSelected; // Αντικαθιστά το isYesBudget με isBudgetYesSelected
    }    


    @Override
    public void start(Stage primaryStage) {
        // Top layout for question and submit
        VBox topLeftVBox = new VBox(10);
        topLeftVBox.setPadding(new Insets(10, 10, 0, 20)); // Add padding for spacing
        topLeftVBox.setAlignment(Pos.TOP_LEFT);

        // Title
        Label title = new Label("Please enter your trailhead below:");
        title.setStyle("-fx-font-size: 14px;");

        // Trailhead input
        TextField trailheadInput = new TextField();
        trailheadInput.setPromptText("Enter your trailhead");

        // Store user input in real time
        trailheadInput.textProperty().addListener((observable, oldValue, newValue) -> {
            trailhead = newValue;
        });

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            if (trailhead != null && !trailhead.trim().isEmpty()) {
                System.out.println("Trailhead entered: " + trailhead);
            } else {
                System.out.println("No trailhead entered.");
            }
        });

        // Add Enter key functionality to Submit
        trailheadInput.setOnAction(e -> submitButton.fire());

        // Add title, input, and submit button to top-left layout
        topLeftVBox.getChildren().addAll(title, trailheadInput, submitButton);

        // Bottom layout for Continue button
        HBox bottomHBox = new HBox();
        bottomHBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomHBox.setPadding(new Insets(10, 20, 10, 20));

        // Continue button
        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-font-weight: bold; -fx-padding: 10px;");
        continueButton.setOnAction(e -> {
            if (isBudgetYesSelected) {
                System.out.println("Proceeding to Screen 5...");
                UI_Screen5 screen5 = new UI_Screen5(isBudgetYesSelected);  // Εδώ περνάμε την τιμή
                try {
                    screen5.start(primaryStage); // Χρήση του ίδιου stage
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Completion");
                alert.setHeaderText(null);
                alert.setContentText("Your process has been successfully completed!");
                alert.showAndWait();
                primaryStage.close(); // Κλείνουμε το παράθυρο
            }
        });

        


        // Add Continue button to bottom layout
        bottomHBox.getChildren().add(continueButton);

        // Main layout
        BorderPane root = new BorderPane();
        root.setTop(topLeftVBox); // Place top-left content at the top
        root.setBottom(bottomHBox); // Place Continue button at the bottom

        // Create and set the scene
        Scene scene = new Scene(root, 500, 300); // Increased size for better layout
        primaryStage.setScene(scene);
        primaryStage.setTitle("Enter Trailhead");
        primaryStage.show();
    }

}
