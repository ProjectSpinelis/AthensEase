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

public class SightScene extends Scene {
    public SightScene(BorderPane root) {
        super(root, 400, 400);

        VBox vbox = new VBox(10);
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToHeight(true);
        root.setCenter(scrollPane);
        root.setPadding(new Insets(15));
        Label messageLabel = new Label("test Label");

        int i;

        for (i = 0; i < 32; i++) {
            final CheckBox tmp = new CheckBox("test" + Integer.toString(i));
            Hyperlink tmpLink = new Hyperlink("www.tripadvisor.com");
            tmpLink.setText("trip advisor" + Integer.toString(i));
            tmpLink.setOnAction(e -> {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(new URI("http://www.tripadvisor.com"));
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            tmp.selectedProperty().addListener(
                    (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                        if (new_val) {
                            messageLabel.setText("pressed" + tmp.getText());
                        } else {
                            messageLabel.setText("released" + tmp.getText());
                        }
                    });
            HBox hbox = new HBox(tmp, tmpLink);
            vbox.getChildren().add(hbox);
        }
        Button clearAll = new Button("Clear All");
        clearAll.setOnAction(e -> {
            vbox.getChildren().forEach(node -> {
                CheckBox cb = (CheckBox) ((HBox) node).getChildren().get(0);
                cb.setSelected(false);
            });
        });
        Button selectAll = new Button("Select All");
        selectAll.setOnAction(e -> {
            vbox.getChildren().forEach(node -> {
                CheckBox cb = (CheckBox) ((HBox) node).getChildren().get(0);
                cb.setSelected(true);
            });
        });
        HBox lastHbox = new HBox(clearAll, messageLabel,selectAll);
        vbox.getChildren().add(lastHbox);
    }
}
