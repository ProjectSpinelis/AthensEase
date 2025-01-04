import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Desktop;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CategoriesScene extends Scene {
    public CategoriesScene(BorderPane root) {
        super(root, 600, 600);

        VBox vbox = new VBox(10);
        root.setPadding(new Insets(15));
        Label messageLabel = new Label("test Label");

        final CheckBox checkbox1 = new CheckBox("HISTORY");
        checkbox1.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                    selectedCategories[0] = new_val;
                });
        vbox.getChildren().add(checkbox1);

        final CheckBox checkbox2 = new CheckBox("ART & CULTURE");
        checkbox2.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                    selectedCategories[1] = new_val;
                });
        vbox.getChildren().add(checkbox2);

        final CheckBox checkbox3 = new CheckBox("NATURE & OUTDOORS");
        checkbox3.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                    selectedCategories[2] = new_val;
                });
        vbox.getChildren().add(checkbox3);


        MyButton clearAll = new MyButton("Clear All");
        clearAll.setOnAction(e -> {
            vbox.getChildren().forEach(node -> {
                CheckBox cb = (CheckBox) ((HBox) node).getChildren().get(0);
                cb.setSelected(false);
            });
        });
        MyButton selectAll = new MyButton("Select All");
        selectAll.setOnAction(e -> {
            vbox.getChildren().forEach(node -> {
                CheckBox cb = (CheckBox) ((HBox) node).getChildren().get(0);
                cb.setSelected(true);
            });
        });
        MyButton nextButton = new MyButton("Next");
        nextButton.setOnAction(e -> {
            SightScene sightScene = new SightScene( new BorderPane());
            primaryStage.setScene(sightScene);
            primaryStage.setTitle("select Sights");
            primaryStage.show();
        });
        HBox lastHbox = new HBox(clearAll, messageLabel,selectAll);
        vbox.getChildren().add(lastHbox);
    }
}
