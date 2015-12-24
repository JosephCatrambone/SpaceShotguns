package com.josephcatrambone.metalskyarena.actors;

import com.badlogic.gdx.math.Vector2;
import com.josephcatrambone.metalskyarena.weapons.FlakCannon;
import com.josephcatrambone.metalskyarena.weapons.SlugGun;

import static com.josephcatrambone.metalskyarena.PhysicsConstants.PPM;

/**
 * Created by Jo on 12/23/2015.
 */
public class Player extends Pawn {

	public Player(int x, int y) {
		super(x, y);
		weapon = new SlugGun();
		weapon.setOwner(this);
	}

	public void handleTouchDown(float x, float y, int button) {

	}

	public void handleTouchUp(float x, float y, int button) {
		if(weapon != null) {
			weapon.shoot(x, y);
		}
	}

}
