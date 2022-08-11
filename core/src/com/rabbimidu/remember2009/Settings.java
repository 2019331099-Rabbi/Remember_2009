package com.rabbimidu.remember2009;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {

	public static int rocketLife = 0;
	public static int rocketTime = 0;
	public static int nivelRotacion = 0;
	public static int rocketVelocityY = 0;
	public static int nivelPower = 0;
	public static int nivelOtro1 = 0;
	private final static Preferences pref = Gdx.app.getPreferences("com.tiar.aaa.aaa.aa");

	public static int[] levelsStar;// Cada posicion es un mundo
	public static boolean[] arrIsWorldLocked;

	public static void load(int numMapas) {
		levelsStar = new int[numMapas];
		arrIsWorldLocked = new boolean[numMapas];
		pref.clear();
		pref.flush();
		for (int i = 0; i < numMapas; i++) {
			levelsStar[i] = pref.getInteger("arrEstrellasMundo" + i, 0);

			if (i == 0)
				arrIsWorldLocked[0] = false;
			else
				arrIsWorldLocked[i] = pref.getBoolean("arrIsWorldLocked" + i, true);
		}

		// Upgrades
		rocketLife = pref.getInteger("nivelVida", 0);
		rocketTime = pref.getInteger("nivelGas", 0);
		nivelRotacion = pref.getInteger("nivelRotacion", 0);
		rocketVelocityY = pref.getInteger("nivelVelocidadY", 0);
		nivelPower = pref.getInteger("nivelPower", 0);
		nivelOtro1 = pref.getInteger("nivelOtro1", 0);
		// Fin upgrades

	}

	public static void save() {

		for (int i = 0; i < levelsStar.length; i++) {
			pref.putInteger("arrEstrellasMundo" + i, levelsStar[i]);
			pref.putBoolean("arrIsWorldLocked" + i, arrIsWorldLocked[i]);
		}

		// Upgrades
		pref.putInteger("nivelVida", rocketLife);
		pref.putInteger("nivelGas", rocketTime);
		pref.putInteger("nivelRotacion", nivelRotacion);
		pref.putInteger("nivelVelocidadY", rocketVelocityY);
		pref.putInteger("nivelPower", nivelPower);
		pref.putInteger("nivelOtro1", nivelOtro1);
		// Fin upgrades

		pref.flush();
	}

	/**
	 * Guarda el numero de estrellas del mundo que se completo y desbloque el siguiente mundo
	 */
	public static void setStarsFromLevel(int level, int numStars) {
		int startActuales = levelsStar[level];
		if (startActuales < numStars) {
			levelsStar[level] = numStars;
		}

		if (arrIsWorldLocked.length >= level + 1)
			arrIsWorldLocked[level + 1] = false;

		save();
	}
}
