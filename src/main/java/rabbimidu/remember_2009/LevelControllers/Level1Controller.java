package rabbimidu.remember_2009.LevelControllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.jbox2d.dynamics.Body;

import java.net.URL;
import java.util.ResourceBundle;

public class Level1Controller implements Initializable {
    public static final int WIDTH = 900;
    public static final int HEIGHT = 500;
    public static final int BALL_RADIUS = 8;

    @FXML
    private Button btnStart;

    @FXML
    private AnchorPane root;
    private Ball ball;


    final Timeline timeline = new Timeline();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawBall();

        Utils.addGround(100, 10);
        //Utils.addRoof(100, 10);
        Utils.addWall(0,100,1,100); //Left wall
        Utils.addWall(99,100,1,100); //Right wall



        timeline.setCycleCount(Timeline.INDEFINITE);

        Duration duration = Duration.seconds(1.0/60.0); // Set duration for frame.

        //Create an ActionEvent, on trigger it executes a world time step and moves the balls to new position
        EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //Create time step. Set Iteration count 8 for velocity and 3 for positions
                Utils.world.step(1.0f/60.f, 8, 3);

                //Move balls to the new position computed by JBox2D
                Body body = (Body)ball.node.getUserData();
                float xpos = Utils.toPixelPosX(body.getPosition().x);
                float ypos = Utils.toPixelPosY(body.getPosition().y);
                ball.node.setLayoutX(xpos);
                ball.node.setLayoutY(ypos);
            }
        };


        /**
         * Set ActionEvent and duration to the KeyFrame.
         * The ActionEvent is trigged when KeyFrame execution is over.
         */
        KeyFrame frame = new KeyFrame(duration, ae, null,null);

        timeline.getKeyFrames().add(frame);

    }



    @FXML
    private void startGame(ActionEvent event) {
        if (event.getSource() == btnStart) {
            timeline.playFromStart();
            btnStart.setVisible(false);
        }
    }

    private void drawBall() {
        ball = new Ball(Utils.toPosX(WIDTH / 2), Utils.toPosY(HEIGHT / 2), Utils.BALL_RADIUS, Color.RED);
        root.getChildren().add(ball.node);
    }
}
