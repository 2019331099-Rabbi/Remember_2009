package com.rabbimidu.remember2009.dialogs;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SizeToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.rabbimidu.remember2009.Assets;
import com.rabbimidu.remember2009.MainLander;
import com.rabbimidu.remember2009.Settings;
import com.rabbimidu.remember2009.screens.Screens;

public class DialogShop extends Window {
	static public float fadeDuration = 0.4f;

	final int MAX_INT = 15;

	MainLander game;

	Image[] arrVida;
	Image[] arrVelocidadY;
	Image[] arrRotacion;
	Image[] arrGas;
	Image[] arrPower;
	Image[] arrOtro1;

	ImageButton btUpVida, btUpVelocidadY, btUpGas, btUpRotacion, btUpPower, btUpOtro1;

	ImageButton btMenu;

	public DialogShop(final MainLander game) {
		super("", Assets.styleDialogGameOver);
		this.game = game;
		this.setMovable(false);

		arrVida = new Image[MAX_INT];
		arrVelocidadY = new Image[MAX_INT];
		arrRotacion = new Image[MAX_INT];
		arrGas = new Image[MAX_INT];
		arrPower = new Image[MAX_INT];
		arrOtro1 = new Image[MAX_INT];

		initBotonesMenu();
		initBotonesUpgrades();

		Label upgrades = new Label("Upgrades", Assets.styleLabelMediana);

		this.row().padTop(25);
		this.add(upgrades).colspan(4);

		// Velocidad
		Table auxTab = new Table();
		auxTab.defaults().pad(1.15f);
		for (int i = 0; i < MAX_INT; i++) {
			arrVelocidadY[i] = new Image(Assets.upgradeOff);
			auxTab.add(arrVelocidadY[i]).width(14).height(18);
		}
		this.row().padTop(20);
		this.add(new Label("Thrusters", Assets.styleLabelMediana));
		this.add(auxTab);
		this.add(btUpVelocidadY);
		this.add(new Label("$100", Assets.styleLabelMediana));

		// Vida
		auxTab = new Table();
		auxTab.defaults().pad(1.15f);
		for (int i = 0; i < MAX_INT; i++) {
			arrVida[i] = new Image(Assets.upgradeOff);
			auxTab.add(arrVida[i]).width(14).height(18);
		}
		this.row().padTop(20);
		this.add(new Label("Life", Assets.styleLabelMediana));
		this.add(auxTab);
		this.add(btUpVida);
		this.add(new Label("$100", Assets.styleLabelMediana));

		// Gas
		auxTab = new Table();
		auxTab.defaults().pad(1.15f);
		for (int i = 0; i < MAX_INT; i++) {
			arrGas[i] = new Image(Assets.upgradeOff);
			auxTab.add(arrGas[i]).width(14).height(18);
		}
		this.row().padTop(20);
		this.add(new Label("Gas", Assets.styleLabelMediana));
		this.add(auxTab);
		this.add(btUpGas);
		this.add(new Label("$100", Assets.styleLabelMediana));

		// Rotacion
		auxTab = new Table();
		auxTab.defaults().pad(1.15f);
		for (int i = 0; i < MAX_INT; i++) {
			arrRotacion[i] = new Image(Assets.upgradeOff);
			auxTab.add(arrRotacion[i]).width(14).height(18);
		}
		this.row().padTop(20);
		this.add(new Label("Rotacion", Assets.styleLabelMediana));
		this.add(auxTab);
		this.add(btUpRotacion);
		this.add(new Label("$100", Assets.styleLabelMediana));

		// Power
		auxTab = new Table();
		auxTab.defaults().pad(1.15f);
		for (int i = 0; i < MAX_INT; i++) {
			arrPower[i] = new Image(Assets.upgradeOff);
			auxTab.add(arrPower[i]).width(14).height(18);
		}
		this.row().padTop(20);
		this.add(new Label("Power", Assets.styleLabelMediana));
		this.add(auxTab);
		this.add(btUpPower);
		this.add(new Label("$100", Assets.styleLabelMediana));

		// Otro1
		auxTab = new Table();
		auxTab.defaults().pad(1.15f);
		for (int i = 0; i < MAX_INT; i++) {
			arrOtro1[i] = new Image(Assets.upgradeOff);
			auxTab.add(arrOtro1[i]).width(14).height(18);
		}
		this.row().padTop(20);
		this.add(new Label("Otroo", Assets.styleLabelMediana));
		this.add(auxTab);
		this.add(btUpOtro1);
		this.add(new Label("$100", Assets.styleLabelMediana));

		// Menu
		this.row().padTop(30).expandX();
		this.add(btMenu).colspan(4);

		this.row().expandX();
		this.add(new Label("Menu", Assets.styleLabelMediana)).colspan(4);

		setArrays();

	}

	private void initBotonesUpgrades() {

		btUpVelocidadY = new ImageButton(Assets.styleImageButtonUpgradePlus);
		btUpVelocidadY.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Settings.rocketVelocityY++;
				setArrays();

			}
		});

		btUpVida = new ImageButton(Assets.styleImageButtonUpgradePlus);
		btUpVida.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Settings.rocketLife++;
				setArrays();

			}
		});

		btUpGas = new ImageButton(Assets.styleImageButtonUpgradePlus);
		btUpGas.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Settings.rocketTime++;
				setArrays();

			}
		});

		btUpRotacion = new ImageButton(Assets.styleImageButtonUpgradePlus);
		btUpRotacion.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Settings.nivelRotacion++;
				setArrays();

			}
		});

		btUpPower = new ImageButton(Assets.styleImageButtonUpgradePlus);
		btUpPower.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Settings.nivelPower++;
				setArrays();

			}
		});

		btUpOtro1 = new ImageButton(Assets.styleImageButtonUpgradePlus);
		btUpOtro1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Settings.nivelOtro1++;
				setArrays();

			}
		});

	}

	private void initBotonesMenu() {
		btMenu = new ImageButton(Assets.styleImageButtonPause);
		btMenu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hide();
			}
		});
	}

	private void setArrays() {
		// Rotacion
		for (int i = 0; i < Settings.nivelRotacion; i++) {
			arrRotacion[i].setDrawable(new TextureRegionDrawable(Assets.upgradeOn));
		}
		if (Settings.nivelRotacion >= MAX_INT) {
			btUpRotacion.setVisible(false);
		}

		// VelocidadY
		for (int i = 0; i < Settings.rocketVelocityY; i++) {
			arrVelocidadY[i].setDrawable(new TextureRegionDrawable(Assets.upgradeOn));
		}
		if (Settings.rocketVelocityY >= MAX_INT) {
			btUpVelocidadY.setVisible(false);
		}

		// Vida
		for (int i = 0; i < Settings.rocketLife; i++) {
			arrVida[i].setDrawable(new TextureRegionDrawable(Assets.upgradeOn));
		}
		if (Settings.rocketLife >= MAX_INT) {
			btUpVida.setVisible(false);
		}

		// Gas
		for (int i = 0; i < Settings.rocketTime; i++) {
			arrGas[i].setDrawable(new TextureRegionDrawable(Assets.upgradeOn));
		}
		if (Settings.rocketTime >= MAX_INT) {
			btUpGas.setVisible(false);
		}

		// Power
		for (int i = 0; i < Settings.nivelPower; i++) {
			arrPower[i].setDrawable(new TextureRegionDrawable(Assets.upgradeOn));
		}
		if (Settings.nivelPower >= MAX_INT) {
			btUpPower.setVisible(false);
		}

		// Otro1
		for (int i = 0; i < Settings.nivelOtro1; i++) {
			arrOtro1[i].setDrawable(new TextureRegionDrawable(Assets.upgradeOn));
		}
		if (Settings.nivelOtro1 >= MAX_INT) {
			btUpOtro1.setVisible(false);
		}

	}

	public void show(Stage stage) {

		this.pack();

		setSize(Screens.SCREEN_WIDTH, 0);

		SizeToAction sizeAction = Actions.action(SizeToAction.class);
		sizeAction.setSize(Screens.SCREEN_WIDTH, 600);// ALTURA FINAL
		sizeAction.setDuration(.25f);

		setPosition(Screens.SCREEN_WIDTH / 2f - getWidth() / 2f, Screens.SCREEN_HEIGHT / 2f - 600 / 2f);// 500 ALTURA FINAL
		addAction(sizeAction);

		stage.addActor(this);
		if (fadeDuration > 0) {
			getColor().a = 0;
			addAction(Actions.fadeIn(fadeDuration, Interpolation.fade));
		}
	}

	public void hide() {
		if (fadeDuration > 0) {
			addCaptureListener(ignoreTouchDown);

			SizeToAction sizeAction = Actions.action(SizeToAction.class);
			sizeAction.setSize(Screens.SCREEN_WIDTH, 0);// ALTURA FINAL
			sizeAction.setDuration(.25f);
			addAction(sequence(Actions.parallel(fadeOut(fadeDuration, Interpolation.fade), sizeAction), Actions.removeListener(ignoreTouchDown, true), Actions.removeActor()));
		}
		else
			remove();

	}

	InputListener ignoreTouchDown = new InputListener() {
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			event.cancel();
			return false;
		}
	};
}
