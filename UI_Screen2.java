import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UI_Screen2 extends Application {

    private int duration = -1; // Default value to track invalid input
    private Boolean isOneTrailheadSelected = null; // Tracks selection for Question 2
    private Boolean isBudgetYesSelected = null;   // Tracks selection for Question 3

    @Override
    public void start(Stage primaryStage) {
        // Create a VBox layout for content
        VBox contentVBox = new VBox(15); // 15px spacing between elements
        contentVBox.setPadding(new Insets(20, 20, 20, 40)); // Add left padding to move content to the right

        // Add a title at the top center
        Label title = new Label("Let's start by getting to know more about your trip!");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Question 1: Integer input
        Label question1 = new Label("1. How many days are you spending in Athens?");
        TextField durationInput = new TextField();
        durationInput.setPromptText("Enter a number");

        // Question 2: Button choices
        Label question2 = new Label("2. How many trailheads are you going to have? (tip: count your starting points).");
        Button oneTrailButton = new Button("One");
        Button manyTrailsButton = new Button("Many");

        oneTrailButton.setOnAction(e -> {
            System.out.println("Selected: One trailhead");
            isOneTrailheadSelected = true;
        });

        manyTrailsButton.setOnAction(e -> {
            System.out.println("Selected: Many trailheads");
            isOneTrailheadSelected = false;
        });

        // Question 3: Button choices
        Label question3 = new Label("3. Are you travelling on a budget?");
        Button budgetYesButton = new Button("Yes");
        Button budgetNoButton = new Button("No");

        budgetYesButton.setOnAction(e -> {
            System.out.println("Selected: Travelling on a budget");
            isBudgetYesSelected = true;
        });

        budgetNoButton.setOnAction(e -> {
            System.out.println("Selected: Not travelling on a budget");
            isBudgetYesSelected = false;
        });

        // Continue button (placed later in the layout)
        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        continueButton.setOnAction(e -> {
            // Validate αν έχουν εισαχθεί τα δεδομένα
            try {
                duration = Integer.parseInt(durationInput.getText().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input! Please enter a valid number.");
                return;
            }
        
            if (isOneTrailheadSelected == null) {
                System.out.println("Please select an option for trailheads.");
                return;
            }
        
            if (isBudgetYesSelected == null) {
                System.out.println("Please select an option for budget.");
                return;
            }
        
            // Μετάβαση στην επόμενη οθόνη
            if (isOneTrailheadSelected) {
                System.out.println("Proceeding to Screen 3...");
                UI_Screen3 nextScreen = new UI_Screen3();
                nextScreen.setIsYesBudget(isBudgetYesSelected); // Μεταφορά δεδομένων
                try {
                    nextScreen.start(primaryStage); // Χρήση του ίδιου stage
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("Proceeding to Screen 4.1...");
                UI_Screen4_1 nextScreen = new UI_Screen4_1(duration, isBudgetYesSelected);
                try {
                    nextScreen.start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        

        // Add all components to the VBox
        contentVBox.getChildren().addAll(
            title,
            question1, durationInput,
            question2, oneTrailButton, manyTrailsButton,
            question3, budgetYesButton, budgetNoButton
        );

        // Use a BorderPane to place the VBox at the center and the button at the bottom right
        BorderPane root = new BorderPane();
        root.setCenter(contentVBox);

        // Create a StackPane for the continue button to position it bottom-right
        StackPane continuePane = new StackPane(continueButton);
        StackPane.setMargin(continueButton, new Insets(10)); // Add margin to the button
        continuePane.setAlignment(Pos.BOTTOM_RIGHT);

        // Add the continue button to the BorderPane
        root.setBottom(continuePane);

        // Create and set the scene
        Scene scene = new Scene(root, 700, 450); // Increased height to 450px
        primaryStage.setScene(scene);
        primaryStage.setTitle("Trip Questionnaire");
        primaryStage.show();
    } 

    public static void main(String[] args) {
        launch(args);
    }
}
