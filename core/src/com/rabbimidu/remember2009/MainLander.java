package com.rabbimidu.remember2009;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rabbimidu.remember2009.screens.MainMenuScreen;

public class MainLander extends Game {

	public Stage stage;
	public SpriteBatch spriteBatch;

	@Override
	public void create() {
		Assets.createMaps();
		stage = new Stage();
		spriteBatch = new SpriteBatch();
		setScreen(new MainMenuScreen(this));
	}
}