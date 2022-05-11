package rabbimidu.remember_2009;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LevelController {

    @FXML
    private Button level1;

    @FXML
    void handleMouseClicked(ActionEvent event) {
        if (event.getSource() == level1) loadPage("FXML/GameLevels/Level1.fxml");
    }

    private void loadPage(String fxml) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.setTitle("Level 1");

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
