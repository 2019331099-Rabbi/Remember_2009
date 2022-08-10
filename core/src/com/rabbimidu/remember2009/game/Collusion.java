package com.rabbimidu.remember2009.game;

import com.badlogic.gdx.physics.box2d.*;
import com.rabbimidu.remember2009.game.objects.Platform;
import com.rabbimidu.remember2009.game.objects.Rocket;
import com.rabbimidu.remember2009.game.objects.Star;
import com.rabbimidu.remember2009.game.objects.Time;

public class Collusion implements ContactListener {

	WorldGame oWorld;

	public Collusion(WorldGame oWorld) {
		this.oWorld = oWorld;
	}

	@Override
	public void beginContact(Contact contact) {

		if (contact.getFixtureA().getBody().getUserData() instanceof Rocket)
			beginContactWithRocket(contact.getFixtureA(), contact.getFixtureB());
		else if (contact.getFixtureB().getBody().getUserData() instanceof Rocket)
			beginContactWithRocket(contact.getFixtureB(), contact.getFixtureA());
	}

	public void beginContactWithRocket(Fixture rocket, Fixture obj) {
		Body rocketBody = rocket.getBody();
		Rocket rocketBodyUserData = (Rocket) rocketBody.getUserData();

		Body objBody = obj.getBody();
		Object objBodyUserData = objBody.getUserData();

		if (objBodyUserData instanceof Time) {
			Time time = (Time) objBodyUserData;

			if (time.state == Time.STATE_NORMAL) {
				rocketBodyUserData.time += 100;
				time.state = Time.STATE_END;
			}
			return;
		}
		else if (objBodyUserData instanceof Star) {
			Star star = (Star) objBodyUserData;

			if (star.state == Star.STATE_NORMAL) {
				oWorld.numberOfTarget++;
				star.state = Star.STATE_FINISHED;
				oWorld.getStar.play();
				oWorld.getStar.setVolume(1.0f);
			}
			return;
		}

		float impactVelocity = Math.abs(rocketBody.getLinearVelocity().x) + Math.abs(rocketBody.getLinearVelocity().y);
		if (impactVelocity > 1.5f) {
			rocketBodyUserData.colision(impactVelocity * 2.5f);
		}

		if (objBodyUserData instanceof Platform)
			if (((Platform) objBodyUserData).isFinal && oWorld.numberOfTarget == 3)
				rocketBodyUserData.isLanded = true;
	}

	@Override
	public void endContact(Contact contact) {
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();

		if (a != null && a.getBody().getUserData() instanceof Rocket)
			endContactWithRocket(contact.getFixtureA(), contact.getFixtureB());
		else if (b != null && b.getBody().getUserData() instanceof Rocket)
			endContactWithRocket(contact.getFixtureB(), contact.getFixtureA());
	}

	private void endContactWithRocket(Fixture rocket, Fixture obj) {
		if (rocket == null || obj == null)
			return;

		Body rocketBody = rocket.getBody();
		Rocket rocketBodyUserData = (Rocket) rocketBody.getUserData();

		Body objBody = obj.getBody();
		Object objBodyUserData = objBody.getUserData();

		if (objBodyUserData instanceof Platform) {
			if (((Platform) objBodyUserData).isFinal)
				rocketBodyUserData.isLanded = false;
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

}
