package com.josephcatrambone.metalskyarena.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.josephcatrambone.metalskyarena.MainGame;

/**
 * Created by Jo on 12/23/2015.
 */
public class Flak extends Pawn {
	public static final float FLAK_MASS = 0.01f;
	int frame = 0;

	public Flak(float x, float y, float dx, float dy) {
		create(x, y, 4, 4, FLAK_MASS, "flak.png");
		this.physicsBody.setBullet(true);
		// If we unset fixed rotation, we have to copy back rotation data from other sim.
		//this.physicsBody.setFixedRotation(false);
		this.physicsBody.applyForceToCenter(dx, dy, true);
		frame = MainGame.random.nextInt(4);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(spriteSheet, this.getX()-this.getOriginX(), this.getY()-this.getOriginY(), 8*(frame/2), 8*(frame%2), 8, 8);
	}

}
