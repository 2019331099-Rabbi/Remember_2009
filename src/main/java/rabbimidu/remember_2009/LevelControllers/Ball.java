package rabbimidu.remember_2009.LevelControllers;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Ball {
    public Node node;

    private float posX;
    private float posY;
    private int radius;

    private Color color;

    public Ball(float posX, float posY, int radius, Color color) {
        this.posX = posX;
        this.posY = posY;
        this.radius = radius;
        this.color = color;
        node = create();
    }

    private Node create() {
        Circle ball = new Circle();
        ball.setRadius(radius);
        ball.setFill(color);

        ball.setLayoutX(Utils.toPixelPosX(posX));
        ball.setLayoutY(Utils.toPixelPosY(posY));

        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(posX, posY);

        CircleShape cs = new CircleShape();
        cs.m_radius = radius * 0.1f;

        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 0.6f;
        fd.friction = 0.3f;
        fd.restitution = 0.8f;


        Body body = Utils.world.createBody(bd);
        body.createFixture(fd);
        ball.setUserData(body);

        return ball;
    }
}
