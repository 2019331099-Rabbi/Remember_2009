package rabbimidu.remember_2009.LevelControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Level1Controller implements Initializable {

    @FXML
    private AnchorPane root;
    public static final double WIDTH = 900d;
    public static final double HEIGHT = 500d;

    private Ball ball;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        draw();
    }

    private void draw() {
        ball = new Ball(Color.BLACK, new PVector(Ball.Radius, HEIGHT-Ball.Radius), new PVector(0, 0), new PVector(0, 0));
        root.getChildren().add(ball);
    }
}
