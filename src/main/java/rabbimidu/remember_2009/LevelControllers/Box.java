package rabbimidu.remember_2009.LevelControllers;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

/**
 *
 * @author dilip
 */
public class Box{

    //JavaFX UI for ball
    public Node node;

    //X and Y position of the ball in JBox2D world
    private float posX;
    private float posY;

    private int width;
    private int height;


    public Box(float posX, float posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        node = create();
    }

    /*
     * This method creates a ball by using Circle object from JavaFX and CircleShape from JBox2D
     */
    private Node create() {
        //Create an UI for ball - JavaFX code
        Rectangle box = new Rectangle();
        box.setHeight(Utils.toPixelHeight(height));
        box.setWidth(Utils.toPixelWidth(width));
        box.setLayoutX(Utils.toPixelPosX(posX));
        box.setLayoutY(Utils.toPixelPosY(posY));

        /*
         * Set ball position on JavaFX scene. We need to convert JBox2D coordinates
         * to JavaFX coordinates which are in pixels.
         */
        box.setLayoutX(Utils.toPixelPosX(posX));
        box.setLayoutY(Utils.toPixelPosY(posY));
        box.setCache(true); //Cache this object for better performance

        //Create an JBox2D body defination for ball.
        BodyDef bd = new BodyDef();
        bd.type = BodyType.STATIC;
        bd.position.set(posX -width / 2, posY + height / 2);

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width / 2, height / 2);

        // Create a fixture for ball
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;


        /*
         * Virtual invisible JBox2D body of ball. Bodies have velocity and position.
         * Forces, torques, and impulses can be applied to these bodies.
         */
        Body body = Utils.world.createBody(bd);
        body.createFixture(fd);
        box.setUserData(body);
        return box;
    }
}
