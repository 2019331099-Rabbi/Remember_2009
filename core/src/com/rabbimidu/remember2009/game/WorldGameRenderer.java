package com.rabbimidu.remember2009.game;

import java.util.Iterator;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.rabbimidu.remember2009.Assets;
import com.rabbimidu.remember2009.game.objects.Star;
import com.rabbimidu.remember2009.game.objects.Time;
import com.rabbimidu.remember2009.game.objects.Rocket;
import com.rabbimidu.remember2009.screens.Screens;

public class WorldGameRenderer {

	final float WIDTH = Screens.WORLD_SCREEN_WIDTH;
	final float HEIGHT = Screens.WORLD_SCREEN_HEIGHT;

	float CAM_MIN_X;
	float CAM_MIN_Y;
	float CAM_MAX_X;
	float CAM_MAX_Y;

	SpriteBatch batcher;
	WorldGame oWorld;
	OrthographicCamera oCam;
	OrthogonalTiledMapRenderer tiledRenderer;

	Box2DDebugRenderer renderBox;

	public WorldGameRenderer(SpriteBatch batcher, WorldGame oWorld) {

		this.oCam = new OrthographicCamera(WIDTH, HEIGHT);
		this.oCam.position.set(WIDTH / 2f, HEIGHT / 2f, 0);

		CAM_MAX_X = Integer.valueOf(Assets.map.getProperties().get("tamanoMapaX", String.class));
		CAM_MAX_X = CAM_MAX_X * oWorld.unitScale * 32 - (WIDTH / 2f);

		CAM_MAX_Y = Integer.valueOf(Assets.map.getProperties().get("tamanoMapaY", String.class));
		CAM_MAX_Y = CAM_MAX_Y * oWorld.unitScale * 32 - (HEIGHT / 2f);

		CAM_MIN_X = WIDTH / 2f;
		CAM_MIN_Y = HEIGHT / 2f;

		this.batcher = batcher;
		this.oWorld = oWorld;
		this.renderBox = new Box2DDebugRenderer();
		this.tiledRenderer = new OrthogonalTiledMapRenderer(Assets.map, oWorld.unitScale);

	}

	public void render(float delta) {

		oCam.position.x = oWorld.rocket.position.x;
		oCam.position.y = oWorld.rocket.position.y;

		if (oCam.position.y < CAM_MIN_Y)
			oCam.position.y = CAM_MIN_Y;
		if (oCam.position.x < CAM_MIN_X)
			oCam.position.x = CAM_MIN_X;

		if (oCam.position.y > CAM_MAX_Y)
			oCam.position.y = CAM_MAX_Y;

		if (oCam.position.x > CAM_MAX_X)
			oCam.position.x = CAM_MAX_X;

		oCam.update();
		batcher.setProjectionMatrix(oCam.combined);

		renderTiled();

		batcher.enableBlending();
		batcher.begin();
		renderRocket();
		renderTime();
		renderStars();

		batcher.end();

		if (Assets.isDebug) {
			renderBox.render(oWorld.oWorldBox, oCam.combined);
		}
	}

	public void renderTiled() {
		tiledRenderer.setView(oCam);
		tiledRenderer.render();
	}

	public void renderRocket() {
		Rocket obj = oWorld.rocket;

		TextureRegion keyframe;

		if (obj.state == Rocket.STATE_NORMAL) {
			if (obj.isFlying)
				keyframe = Assets.naveFly.getKeyFrame(obj.stateTime, true);
			else
				keyframe = Assets.rocket;
			batcher.draw(keyframe, obj.position.x - Rocket.DRAW_WIDTH / 2f, obj.position.y - 1.025f, Rocket.DRAW_WIDTH / 2f, 1.025f, Rocket.DRAW_WIDTH, Rocket.DRAW_HEIGHT, 1, 1, (float) Math.toDegrees(obj.angleRad));

		}
		else {
			keyframe = Assets.explosion.getKeyFrame(obj.stateTime, false);
			batcher.draw(keyframe, obj.position.x - .5f, obj.position.y - .5f, .5f, .5f, 1f, 1f, 1, 1, (float) Math.toDegrees(obj.angleRad));
		}
	}

	public void renderTime() {
		Iterator<Time> i = oWorld.times.iterator();
		while (i.hasNext()) {
			Time obj = i.next();
			batcher.draw(Assets.gas, obj.position.x - .25f, obj.position.y - .25f, .5f, .5f);

		}
	}

	public void renderStars() {
		Iterator<Star> i = oWorld.targets.iterator();
		while (i.hasNext()) {
			Star obj = i.next();
			batcher.draw(Assets.star, obj.position.x - .25f, obj.position.y - .25f, .5f, .5f);
		}
	}
}
