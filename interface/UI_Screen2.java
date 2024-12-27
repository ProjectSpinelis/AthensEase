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

    private int duration; // Variable to store the integer input
    private boolean isOneTrailheadSelected = false; // Tracks selection for Question 2
    private boolean isBudgetYesSelected = false;   // Tracks selection for Question 3

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

        // Submit action for Question 1
        Runnable submitDurationAction = () -> {
            String input = durationInput.getText().trim();
            try {
                duration = Integer.parseInt(input);
                System.out.println("Number of days in Athens: " + duration);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        };

        // Attach the action to Enter key press
        durationInput.setOnAction(e -> submitDurationAction.run());

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
            // Validate if duration has been entered
            if (durationInput.getText().trim().isEmpty()) {
                System.out.println("Please enter the number of days.");
                return;
            }
            submitDurationAction.run(); // Ensure duration is set

            // Check that user has selected an option for trailheads and budget
            if (!oneTrailButton.isFocused() && !manyTrailsButton.isFocused()) {
                System.out.println("Please select an option for trailheads.");
                return;
            }
            if (!budgetYesButton.isFocused() && !budgetNoButton.isFocused()) {
                System.out.println("Please select an option for budget.");
                return;
            }

            // Logic for transitioning based on selections
            if (isOneTrailheadSelected) {
                System.out.println("Proceeding to Screen 3 (One Trailhead Screen)...");
                // Transition to Screen 3
                UI_Screen3 nextScreen = new UI_Screen3();
                try {
                    nextScreen.start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("Proceeding to Screen 4.1 (Many Trailheads Screen)...");
                // Transition to Screen 4.1
                UI_Screen4_1 nextScreen = new UI_Screen4_1();
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
        Scene scene = new Scene(root, 700, 450); // Increased height to 600px
        primaryStage.setScene(scene);
        primaryStage.setTitle("Trip Questionnaire");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
