package com.rabbimidu.remember2009.game.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.rabbimidu.remember2009.Settings;

public class Rocket {

	final public static float DRAW_WIDTH = .7f;
	final public static float DRAW_HEIGHT = 1.59f;
	final public static float WIDTH = .5f;
	final public static float HEIGHT = 1.0f;

	final public static float Density = .7f;
	final private int MAX_ANGLE_DEGREES = 20;

	final public static float flyVelocity = 2f;
	final public static float MAX_SPEED_Y = 2;
	final public static float MIN_SPEED_Y = -4;
	public float curVelocity;

	final public static float Velocity_Move = 1.3f;
	public float velocityMove;
	final public static float MAX_SPEED_X = 1f;

	final public static float initialTime = 100;
	public float time;

	final public static float initialLife = 20;
	public float life;

	public static int STATE_NORMAL = 0;
	public static int STATE_EXPLODE = 1;
	public static float EXPLODE_TIME = .05f * 20;

	public Vector2 position;
	public Vector2 velocity;
	public float angleRad;

	public int state;
	public float stateTime;

	public boolean isFlying;
	public boolean isLanded;

	public Rocket(float x, float y) {
		position = new Vector2(x, y);
		state = STATE_NORMAL;
		time = initialTime;
		life = initialLife;
		curVelocity = flyVelocity;
		velocityMove = Velocity_Move;
		isFlying = false;


		curVelocity += (.09 * Settings.rocketVelocityY);
		velocityMove += (.02 * Settings.nivelRotacion);
		life += (5.3f * Settings.rocketLife);
		time += (33.3f * Settings.rocketTime);
	}

	public void update(float delta, Body body, float accelX, float accelY) {

		if (state == STATE_NORMAL) {

			if (time < 0 || accelY == 0) {
				accelX = accelY = 0;
				isFlying = false;
			}
			else
				isFlying = true;
			body.applyForceToCenter(velocityMove * accelX, curVelocity * accelY, true);
			body.applyForceToCenter(body.getLinearVelocity().x * -.015f, 0, true);

			velocity = body.getLinearVelocity();

			if (velocity.y > Rocket.MAX_SPEED_Y) {
				velocity.y = Rocket.MAX_SPEED_Y;
				body.setLinearVelocity(velocity);

			}
			else if (velocity.y < MIN_SPEED_Y) {
				velocity.y = MIN_SPEED_Y;
				body.setLinearVelocity(velocity);
			}
			if (velocity.x > Rocket.MAX_SPEED_X) {
				velocity.x = Rocket.MAX_SPEED_X;
				body.setLinearVelocity(velocity);
			}
			else if (velocity.x < -Rocket.MAX_SPEED_X) {
				velocity.x = -Rocket.MAX_SPEED_X;
				body.setLinearVelocity(velocity);
			}

			angleRad = MathUtils.atan2(-accelX, accelY);

			position.x = body.getPosition().x;
			position.y = body.getPosition().y;

			float angleLimitRad = (float) Math.toRadians(MAX_ANGLE_DEGREES);

			if (angleRad > angleLimitRad)
				angleRad = angleLimitRad;
			else if (angleRad < -angleLimitRad)
				angleRad = -angleLimitRad;

			body.setTransform(position.x, position.y, angleRad);

			if (accelX != 0 || accelY != 0)
				time -= (5 * delta);
		}
		else {
			body.setLinearVelocity(0, 0);
		}

		stateTime += delta;
	}

	public void collide(float damage) {
		if (state == STATE_NORMAL) {
			life -= damage;
			if (life <= 0) {
				state = STATE_EXPLODE;
				stateTime = 0;
			}
		}
	}
}
