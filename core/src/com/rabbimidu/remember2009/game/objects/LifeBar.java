package com.rabbimidu.remember2009.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rabbimidu.remember2009.Assets;

public class LifeBar extends Actor {

	public float maxLife;
	public float actualLife;

	public LifeBar(float maxLife) {
		this.maxLife = maxLife;
		this.actualLife = maxLife;
	}

	public void updateActualLife(float actualLife) {
		this.actualLife = actualLife;

		if (actualLife > maxLife)
			maxLife = actualLife;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		batch.draw(Assets.barraMarcadorRojo, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		if (actualLife > 0)
			batch.draw(Assets.barraMarcadorVerde, this.getX(), this.getY(), this.getWidth() * (actualLife / maxLife), this.getHeight());
	}
}
