package rabbimidu.remember_2009.LevelControllers;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 *
 * @author dilip
 */
public class Utils {
    //Create a JBox2D world.
    public static final World world = new World(new Vec2(0.0f, -100.0f));

    //Screen width and height
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    //Ball radius in pixel
    public static final int BALL_SIZE = 8;

    //This method adds a ground to the screen.
    public static void addGround(float width, float height){
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width,height);

        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = 1.0f;
        fd.friction = 0.3f;

        BodyDef bd = new BodyDef();
        bd.position= new Vec2(0.0f,-10f);

        world.createBody(bd).createFixture(fd);
    }

    //This method adds a ground to the screen.
    public static void addRoof(float width, float height) {
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width, height);

        BodyDef bd = new BodyDef();
        bd.position = new Vec2(0.0f, 109.0f);

        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = 1.0f;
        fd.friction = 0.3f;

        world.createBody(bd).createFixture(fd);
    }

    //This method creates a walls.
    public static void addWall(float posX, float posY, float width, float height){
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width,height);

        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = 1.0f;
        fd.friction = 0.3f;

        BodyDef bd = new BodyDef();
        bd.position.set(posX, posY);

        Utils.world.createBody(bd).createFixture(fd);
    }

    //Convert a JBox2D x coordinate to a JavaFX pixel x coordinate
    public static float toPixelPosX(float posX) {
        float x = WIDTH*posX / 100.0f;
        return x;
    }

    //Convert a JavaFX pixel x coordinate to a JBox2D x coordinate
    public static float toPosX(float posX) {
        float x =   (posX*100.0f*1.0f)/WIDTH;
        return x;
    }

    //Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
    public static float toPixelPosY(float posY) {
        float y = HEIGHT - (1.0f*HEIGHT) * posY / 100.0f;
        return y;
    }

    //Convert a JavaFX pixel y coordinate to a JBox2D y coordinate
    public static float toPosY(float posY) {
        float y = 100.0f - ((posY * 100*1.0f) /HEIGHT) ;
        return y;
    }

    //Convert a JBox2D width to pixel width
    public static float toPixelWidth(float width) {
        return WIDTH * width / 100.0f;
    }

    //Convert a JBox2D height to pixel height
    public static float toPixelHeight(float height) {
        return HEIGHT*height/100.0f;
    }

    //Convert a pixel width to Box2D width
    public static float toWidth(float width) {
        return 100.0f * width / WIDTH;
    }

    //Convert a pixel height to Box2D height
    public static float toHeight(float height) {
        return 100.0f * height / WIDTH;
    }

}
