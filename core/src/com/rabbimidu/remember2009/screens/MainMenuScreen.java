package com.rabbimidu.remember2009.screens;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rabbimidu.remember2009.Assets;
import com.rabbimidu.remember2009.MainLander;
import com.rabbimidu.remember2009.dialogs.DialogShop;

public class MainMenuScreen extends Screens {

	TextButton btPlay, btSettings, btMore;

	DialogShop dialogShop;

	public MainMenuScreen(MainLander game) {
		super(game);

		initButtons();

		MoveToAction action = Actions.action(MoveToAction.class);
		action.setInterpolation(Interpolation.linear);
		action.setPosition(5f, 540);
		action.setDuration(.75f);
		ScaleToAction scAction = Actions.action(ScaleToAction.class);
		scAction.setInterpolation(Interpolation.fade);
		scAction.setDuration(1f);
		scAction.setScale(1);
		Image title = new Image(Assets.title);
		title.setSize(447, 225);
		title.setPosition(5f, 1000);
		title.setScale(.3f);
		title.addAction(Actions.parallel(action, scAction));

		stage.addActor(btPlay);
		stage.addActor(btSettings);
		stage.addActor(btMore);
		stage.addActor(title);

		dialogShop = new DialogShop(game);
	}

	private void initButtons() {

		float buttonWidth = 300;
		float buttonX = SCREEN_WIDTH / 2f - buttonWidth / 2f;

		btPlay = new TextButton("Play", Assets.styleTextButtonMenu);
		btPlay.setSize(buttonWidth, 100);
		btPlay.setPosition(buttonX, -10);
		btPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				changeScreen(LevelScreen.class);
			}
		});

		btSettings = new TextButton("Settings", Assets.styleTextButtonMenu);
		btSettings.setSize(buttonWidth, 100);
		btSettings.setPosition(buttonX, -50);
		btSettings.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dialogShop.show(stage);
			}
		});

		btMore = new TextButton("More", Assets.styleTextButtonMenu);
		btMore.setSize(buttonWidth, 100);
		btMore.setPosition(buttonX, -90);
		btMore.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//Will be implemented
			}
		});

		addActionToButtonEnter(btPlay, buttonX, 420);
		addActionToButtonEnter(btSettings, buttonX, 300);
		addActionToButtonEnter(btMore, buttonX, 180);
	}

	public void addActionToButtonEnter(TextButton bt, float x, float y) {
		MoveToAction action = Actions.action(MoveToAction.class);
		action.setInterpolation(Interpolation.exp10Out);
		action.setPosition(x, y);
		action.setDuration(.75f);
		bt.addAction(action);
	}

	public void changeScreen(final Class<?> screen) {

		addActionToButtonLeave(btPlay, btPlay.getX(), -100);
		addActionToButtonLeave(btSettings, btSettings.getX(), -100);
		addActionToButtonLeave(btMore, btMore.getX(), -100);

		stage.addAction(Actions.sequence(Actions.delay(.75f), Actions.run(new Runnable() {
			@Override
			public void run() {
				if (screen == LevelScreen.class) {
					game.setScreen(new LevelScreen(game));
				}
			}
		})));
	}

	public void addActionToButtonLeave(TextButton bt, float x, float y) {
		MoveToAction action = Actions.action(MoveToAction.class);
		action.setInterpolation(Interpolation.exp10In);
		action.setPosition(x, y);
		action.setDuration(.75f);
		bt.addAction(action);
	}

	@Override
	public void draw(float delta) {
		oCam.update();
		batcher.setProjectionMatrix(oCam.combined);

		batcher.begin();
		batcher.disableBlending();
		batcher.draw(Assets.fondo, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		batcher.end();

	}

	@Override
	public void update(float delta) {

	}

}
