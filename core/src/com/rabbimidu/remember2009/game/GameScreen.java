package com.rabbimidu.remember2009.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rabbimidu.remember2009.Assets;
import com.rabbimidu.remember2009.MainLander;
import com.rabbimidu.remember2009.Settings;
import com.rabbimidu.remember2009.dialogs.GameCompleted;
import com.rabbimidu.remember2009.dialogs.GameOver;
import com.rabbimidu.remember2009.dialogs.GamePaused;
import com.rabbimidu.remember2009.game.objects.LifeBar;
import com.rabbimidu.remember2009.screens.MainMenuScreen;
import com.rabbimidu.remember2009.screens.Screens;

public class GameScreen extends Screens {
	public static final int STATE_READY = 0;
	public static final int STATE_RUNNING = 1;
	public static final int STATE_PAUSED = 2;
	public static final int STATE_GAME_OVER = 3;
	public static int state;

	public final int level;
	WorldGame oWorld;
	WorldGameRenderer renderer;

	float sensitivity = 3;

	LifeBar lifeBar;
	LifeBar timeBar;
	Table lifeTimeTable;

	ImageButton btPause;
	GameOver dialogGameOver;
	GamePaused dialogPaused;
	GameCompleted dialogNextLevel;

	public GameScreen(MainLander game, int level) {
		super(game);
		this.level = level;
		Assets.createMap(level);
		oWorld = new WorldGame();
		renderer = new WorldGameRenderer(batcher, oWorld);

		dialogGameOver = new GameOver(game, oWorld, level);
		dialogPaused = new GamePaused(game, oWorld, level);
		dialogNextLevel = new GameCompleted(game, oWorld, level);

		lifeTimeTable = new Table();
		lifeTimeTable.setSize(172, 98);
		lifeTimeTable.setBackground(Assets.marcoStats);
		lifeTimeTable.setPosition(0, SCREEN_HEIGHT - 99);

		lifeBar = new LifeBar(oWorld.rocket.life);
		timeBar = new LifeBar(oWorld.rocket.time);

		lifeTimeTable.add(lifeBar).width(90).height(25).padLeft(35).padBottom(5);
		lifeTimeTable.row();
		lifeTimeTable.add(timeBar).width(90).height(25).padLeft(35).padTop(6);

		btPause = new ImageButton(Assets.styleImageButtonPause);
		btPause.setSize(32, 32);
		btPause.setPosition(SCREEN_WIDTH - btPause.getWidth() - 5, SCREEN_HEIGHT - btPause.getHeight() - 5);
		btPause.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setPaused();
			}
		});

		stage.addActor(lifeTimeTable);
		stage.addActor(btPause);

		state = STATE_RUNNING;
	}

	@Override
	public void update(float delta) {
		switch (state) {
			case STATE_READY:
				updateReady(delta);
				break;
			case STATE_RUNNING:
				updateRunning(delta);
				break;
		}
	}

	private void updateReady(float delta) {

	}

	private void updateRunning(float delta) {
		float accelX = 0, accelY = 0;

		if (Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)) {
			accelX = Gdx.input.getAccelerometerX() / sensitivity * -1;
		}

		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT))
			accelX = -1;
		else if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT))
			accelX = 1;

		if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.SPACE))
			accelY = 1;

		oWorld.update(delta, accelY, accelX);

		lifeBar.updateActualLife(oWorld.rocket.life);
		timeBar.updateActualLife(oWorld.rocket.time);

		if (oWorld.state == WorldGame.STATE_GAME_OVER) {
			setGameOver();
		}
		else if (oWorld.state == WorldGame.STATE_NEXT_LEVEL) {
			setNextLevel();
		}

	}

	@Override
	public void draw(float delta) {
		renderer.render(delta);
		oCam.update();
		batcher.setProjectionMatrix(oCam.combined);

		batcher.begin();
		batcher.end();

	}

	private void setPaused() {
		state = STATE_PAUSED;
		dialogPaused.show(stage);
	}

	private void setGameOver() {
		state = STATE_GAME_OVER;
		dialogGameOver.show(stage);

	}

	private void setNextLevel() {
		state = STATE_GAME_OVER;
		Settings.setStarsFromLevel(level, oWorld.numberOfTarget);
		dialogNextLevel.show(stage);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
			game.setScreen(new MainMenuScreen(game));
			return true;
		}
		return false;
	}
}
