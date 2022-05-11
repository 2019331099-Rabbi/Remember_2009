package rabbimidu.remember_2009.LevelControllers;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball extends Circle {

    public PVector position, velocity, acceleration;
    public double mass;
    public static final double Radius = 8d;

    public Ball(Paint paint, PVector position, PVector velocity, PVector acceleration) {
        super(position.x, position.y, Radius, paint);
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public void move() {
        setCenterX(getCenterX() + velocity.x);
        setCenterY(getCenterY() + velocity.y);
        adjustPosition();

        velocity.x += acceleration.x;
        velocity.y += acceleration.y;

//        acceleration.x = new Random().nextDouble(-0.05, 0.05);
//        acceleration.y = new Random().nextDouble(-0.05, 0.05);


        acceleration.x = 0;
        acceleration.y = 0;
    }

    private void adjustPosition() {
        if (getCenterX() - Radius <= 0 || getCenterX() + Radius >= Level1Controller.WIDTH) velocity.x *= -1;
        if (getCenterY() - Radius <= 0 || getCenterY() + Radius >= Level1Controller.HEIGHT) velocity.y *= -1;
    }

    public void applyForce(PVector force) {
        acceleration.x += force.x / mass;
        acceleration.y += force.y / mass;
    }
}

