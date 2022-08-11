package com.rabbimidu.remember2009.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rabbimidu.remember2009.Assets;
import com.rabbimidu.remember2009.game.objects.Platform;
import com.rabbimidu.remember2009.game.objects.Rocket;
import com.rabbimidu.remember2009.game.objects.Star;
import com.rabbimidu.remember2009.game.objects.Time;

import java.util.Random;

public class WorldGame {
	static int STATE_RUNNING = 0;
	static int STATE_PAUSED = 1;
	static int STATE_GAME_OVER = 2;
	static int STATE_NEXT_LEVEL = 3;

	public World oWorldBox;
	public Vector2 gravity;
	float unitScale = 1 / 100f;

	final float TIME_OUT = 1.5f;
	float timeOut;

	final float TIME_FOR_NEXT_LEVEL = .75f;
	float timeForNextLevel;

	Rocket rocket;
	Array<Platform> platforms;
	Array<Star> targets;
	Array<Time> times;

	Array<Body> arrBodies;

	Random oRan;
	public int state;

	public int numberOfTarget;
	Music getStar, explosion;

	public WorldGame() {
		gravity = new Vector2(0, -4.9f);
		oWorldBox = new World(gravity, true);
		oWorldBox.setContactListener(new Collusion(this));

		arrBodies = new Array<>();
		platforms = new Array<>();
		targets = new Array<>();
		times = new Array<>();
		numberOfTarget = 0;

		new TiledMapManagerBox2d(this, unitScale).createMap(Assets.map);
		oRan = new Random();
		getStar = Gdx.audio.newMusic(Gdx.files.internal("data/Sound/pickStar.mp3"));
		explosion = Gdx.audio.newMusic(Gdx.files.internal("data/Sound/explosion.mp3"));
	}

	public void update(float delta, float accelY, float accelX) {
		oWorldBox.step(1 / 60f, 8, 4);
		oWorldBox.clearForces();

		oWorldBox.getBodies(arrBodies);

		for (Body body : arrBodies) {
			if (body.getUserData() instanceof Rocket) {
				updateRocket(body, delta, accelY, accelX);
			} else if (body.getUserData() instanceof Time) {
				Time obj = (Time) body.getUserData();
				if (obj.state == Time.STATE_END && !oWorldBox.isLocked()) {
					oWorldBox.destroyBody(body);
					times.removeValue(obj, true);
				}
			} else if (body.getUserData() instanceof Star) {
				Star obj = (Star) body.getUserData();
				if (obj.state == Star.STATE_FINISHED && !oWorldBox.isLocked()) {
					oWorldBox.destroyBody(body);
					targets.removeValue(obj, true);
				}
			}
		}

		if (rocket.time <= 0 && state == STATE_RUNNING) {
			timeOut += delta;
			if (timeOut >= TIME_OUT) {
				state = STATE_GAME_OVER;
			}
		}

		if (rocket.isLanded) {
			timeForNextLevel += delta;
			if (timeForNextLevel >= TIME_FOR_NEXT_LEVEL) {
				state = STATE_NEXT_LEVEL;
				getStar.dispose();
				if (rocket.state == Rocket.STATE_EXPLODE) {
					explosion.play();
					explosion.setVolume(0.5f);
				}
			}
		}
		else {
			timeForNextLevel = 0;
		}
	}

	private void updateRocket(Body body, float delta, float accelY, float accelX) {
		Rocket rocket = (Rocket) body.getUserData();
		if (rocket.state == Rocket.STATE_EXPLODE && rocket.stateTime > Rocket.EXPLODE_TIME && !oWorldBox.isLocked()) {
			explosion.play();
			explosion.setVolume(1.0f);
			oWorldBox.destroyBody(body);
			state = STATE_GAME_OVER;
			return;
		}
		rocket.update(delta, body, accelX, accelY);
	}
}
