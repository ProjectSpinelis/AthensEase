import java.awt.Button;

public class MyButton extends Button {
    public MyButton(String text) {
        super(text);
        setStyle("-fx-font-size: 20px; -fx-background-color: rgb(238, 10, 10); -fx-text-fill: white;");
        setPrefWidth(200);
        setPrefHeight(50);
        setOnMouseEntered(e -> setStyle("-fx-background-color: rgb(10, 40, 238); -fx-text-fill: black;"));
        setOnMouseExited(e -> setStyle("-fx-font-size: 20px; -fx-background-color: rgb(238, 10, 10); -fx-text-fill: white;"));
    }
}
