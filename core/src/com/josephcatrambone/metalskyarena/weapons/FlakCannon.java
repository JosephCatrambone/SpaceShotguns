package com.josephcatrambone.metalskyarena.weapons;

import com.badlogic.gdx.math.Vector2;
import com.josephcatrambone.metalskyarena.MainGame;
import com.josephcatrambone.metalskyarena.actors.Flak;
import com.josephcatrambone.metalskyarena.actors.Pawn;

import static com.josephcatrambone.metalskyarena.PhysicsConstants.PPM;

/**
 * Created by Jo on 12/23/2015.
 */
public class FlakCannon extends Weapon {
	public static final int FLAK_PIECES_PER_SHOT = 4;
	public static final float FLAK_VARIATION = 2.0f;

	public FlakCannon() {
		this.name = "Flak Cannon";
		this.ammoCount = 600;
		this.ammoInMagazine = 60;
		this.magazineSize = 60;
		this.muzzleVelocity = 250;
	}

	@Override
	public void shoot(float x, float y) {
		super.shoot(x, y);

		float invMag = 1.0f/(float)Math.sqrt(x*x + y*y);

		for(int i=0; i < FLAK_PIECES_PER_SHOT; i++) {
			Flak f = new Flak(
					(MainGame.random.nextFloat()*FLAK_VARIATION)+owner.getX()+(owner.getWidth()*x*invMag),
					(MainGame.random.nextFloat()*FLAK_VARIATION)+owner.getY()+(owner.getHeight()*y*invMag),
					(MainGame.random.nextFloat()*FLAK_VARIATION)+(x*invMag)*(muzzleVelocity/FLAK_PIECES_PER_SHOT),
					(MainGame.random.nextFloat()*FLAK_VARIATION)+(y*invMag)*(muzzleVelocity/FLAK_PIECES_PER_SHOT)
			);
			f.getBody().applyLinearImpulse(owner.getBody().getLinearVelocity(), owner.getBody().getLocalCenter(), true);
			owner.getStage().addActor(f);
		}
	}
}
