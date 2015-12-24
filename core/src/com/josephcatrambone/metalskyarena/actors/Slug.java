package com.josephcatrambone.metalskyarena.actors;

import com.badlogic.gdx.math.Vector2;
import static com.josephcatrambone.metalskyarena.PhysicsConstants.*;

/**
 * Created by Jo on 12/23/2015.
 */
public class Slug extends Pawn {
	public static final float SLUG_MASS = 0.1f; // 100g
	public Slug(Vector2 position, Vector2 force, Vector2 parentVelocity) {
		create(position.x*PPM, position.y*PPM, 2, 2, SLUG_MASS, "slug.png");
		this.physicsBody.setBullet(true);
		this.physicsBody.applyLinearImpulse(parentVelocity, this.getBody().getPosition(), true);
		this.physicsBody.applyForceToCenter(force, true);
	}
}
