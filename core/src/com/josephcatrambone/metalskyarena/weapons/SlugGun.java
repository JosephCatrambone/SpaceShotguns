package com.josephcatrambone.metalskyarena.weapons;

import com.badlogic.gdx.math.Vector2;
import com.josephcatrambone.metalskyarena.MainGame;
import com.josephcatrambone.metalskyarena.actors.Flak;
import com.josephcatrambone.metalskyarena.actors.Pawn;
import com.josephcatrambone.metalskyarena.actors.Slug;

/**
 * Created by Jo on 12/23/2015.
 */
public class SlugGun extends Weapon {
	public SlugGun() {
		this.name = "Slug Thrower";
		this.ammoCount = 30;
		this.ammoInMagazine = 10;
		this.magazineSize = 10;
		this.muzzleVelocity = 30;
		this.bulletBuffer = 4; // Size of slug plus some room for player.
	}

	@Override
	protected Pawn[] spawnRounds(Vector2 position, Vector2 parentVelocity, Vector2 heading) {
		Slug s = new Slug(
				position,
				heading.scl(muzzleVelocity),
				parentVelocity
		);
		owner.getStage().addActor(s);
		return new Pawn[]{s};
	}
}
