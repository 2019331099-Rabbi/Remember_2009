package com.rabbimidu.remember2009.game.objects;

import com.badlogic.gdx.math.Vector2;

public class Time {

	public static int STATE_NORMAL = 0;
	public static int STATE_END = 1;

	public Vector2 position;
	public Vector2 size;
	public float stateTime;

	public int state;

	public Time(float x, float y, float width, float height) {
		position = new Vector2(x, y);
		size = new Vector2(width, height);
		stateTime = 0;
		state = STATE_NORMAL;
	}
}
