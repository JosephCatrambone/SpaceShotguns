package com.josephcatrambone.metalskyarena.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.josephcatrambone.metalskyarena.actors.Pawn;

import static com.josephcatrambone.metalskyarena.PhysicsConstants.PPM;

/**
 * Created by Jo on 12/23/2015.
 */
public class Weapon {
	String name;
	int ammoCount;
	int ammoInMagazine;
	int magazineSize;
	float reloadTime;
	float reloadTimeRemaining; // If a reload has started, this is how many MS are left before ammo.
	float muzzleVelocity;
	float bulletBuffer; // How far away should a bullet be spawned?
	Pawn owner;

	public void setOwner(Pawn owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public int getAmmoLeft() {
		return ammoCount;
	}

	public int getAmmoLeftInMagazine() {
		return ammoInMagazine;
	}

	public void reload() {
		if(reloadTimeRemaining > 0) {
			// Do nothing.  Already reloading.
		} else {
			reloadTimeRemaining = reloadTime;
		}
	}

	public void act(float deltatime) {
		if(reloadTimeRemaining - deltatime <= 0) {
			// Reload.
			// If we have enough for a full magazine, use a full magazine, otherwise just use what's left.

		}
	}

	public void shoot(float sceneX, float sceneY) {
		if(this.owner == null) {
			// TODO: Log error.
			return;
		}

		// Take out ammo.  Auto reload if shooting with no bullets.
		if(ammoInMagazine <= 0) {
			reload();
			return; // No ammo. :(
		} else {
			// TODO: Disable infinite ammo.
			//ammoInMagazine -= 1;
		}

		// X and Y are relative to the background scene here.
		// The player is also relative to the scene, so we have to kinda' hack it and project player to game coords,
		// subtract click coords, and project back to phys coords.
		float x = sceneX - (owner.getX()-owner.getOriginX());
		float y = sceneY - (owner.getY()-owner.getOriginY());

		float invMag = 1.0f/(float)Math.sqrt(x*x + y*y);

		// Calculate the offset of the round.
		Body ownerBody = this.owner.getBody();
		Vector2 roundTarget = new Vector2(x*invMag, y*invMag); // Don't worry about dividing out PPM here, since we normalize.
		Vector2 roundOffset = ownerBody.getPosition().cpy().add(roundTarget.cpy().scl(bulletBuffer));

		spawnRounds(roundOffset, ownerBody.getLinearVelocity(), roundTarget);

		owner.getBody().applyForceToCenter(roundTarget.scl(-muzzleVelocity), true);
	}

	/*** spawnRounds
	 * A method called with the position at which one (or more) rounds should be spawned and the velocity of the parent.
	 * @param position The position at which the round should be spawned. (Phys coordinate because it's easier.)
	 * @param parentVelocity The velocity of the parent to impart to the charge. (Phys coordinates.)
	 * @param heading The direction in which the player is aiming. (Phys coordinates.)
	 * @return A list of the spawned rounds.
	 */
	protected Pawn[] spawnRounds(Vector2 position, Vector2 parentVelocity, Vector2 heading) {
		return null;
	}
}
