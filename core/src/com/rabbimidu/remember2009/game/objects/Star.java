package com.rabbimidu.remember2009.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Star {

	public static int STATE_NORMAL = 0;
	public static int STATE_FINISHED = 1;

	public Vector2 position;
	public Vector2 size;
	public float stateTime;

	public int state;

	public Star(float x, float y, float width, float height) {
		position = new Vector2(x, y);
		size = new Vector2(width, height);
		stateTime = 0;
		state = STATE_NORMAL;
	}
}
