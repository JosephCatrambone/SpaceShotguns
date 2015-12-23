package com.josephcatrambone.metalskyarena.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.josephcatrambone.metalskyarena.MainGame;

import static com.josephcatrambone.metalskyarena.PhysicsConstants.PPM;

/**
 * Created by Jo on 12/20/2015.
 */
public class Player extends Actor {
	public enum State {IDLE, SHOOT, HIT, DEAD, NUM_STATES};

	// Gameplay
	float weaponForce;
	int ammo;

	// Sounds


	// Animation
	public final String SPRITE_SHEET_FILENAME = "missing.png";
	Texture spriteSheet; // Don't use Image subclass or Sprite.
	Animation[] animations;

	// Physics
	public static int PLAYER_HALFWIDTH = 8;
	public static int PLAYER_HALFHEIGHT = 8;
	State state;
	Body physicsBody;

	/*** Player
	 * Given a reference to the Box2D world and pixel coordinates in SCREEN SPACE, create a new player.
	 * @param x
	 * @param y
	 */
	public Player(int x, int y) {
		// Load graphics.
		spriteSheet = MainGame.assetManager.get(SPRITE_SHEET_FILENAME);

		// Set game state.
		weaponForce = 50;
		state = State.IDLE;

		// Create visible bounds.
		this.setPosition(x, y, Align.center);
		this.setBounds(0, 0, PLAYER_HALFWIDTH*2, PLAYER_HALFHEIGHT*2);
		this.setOrigin(0.5f, 0.5f);

		// Create physics body.
		World world = MainGame.world;
		BodyDef bdef = new BodyDef();
		bdef.fixedRotation = true;
		bdef.position.set(x/PPM, y/PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		physicsBody = world.createBody(bdef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(PLAYER_HALFWIDTH/PPM, PLAYER_HALFHEIGHT/PPM);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		physicsBody.createFixture(fdef);
	}

	public void handleTouchDown(float x, float y, int button) {

	}

	public void handleTouchUp(float x, float y, int button) {
		Vector2 pos = physicsBody.getPosition().cpy();
		pos.add(-x/PPM, -y/PPM); // Assume x and y are remapped from screen to proper orientation, but convert to phys.
		pos.nor();
		System.out.println("Net: " + pos.x + ", " + pos.y);
		physicsBody.applyForceToCenter(pos.scl(weaponForce), true);
	}

	public void dispose() {
		MainGame.assetManager.unload(SPRITE_SHEET_FILENAME);
		MainGame.world.destroyBody(this.physicsBody);
	}

	@Override
	public void act(float deltaTime) {
		// Update position based on rigit body.
		Vector2 pos = physicsBody.getPosition();
		this.setX(pos.x*PPM);
		this.setY(pos.y*PPM);
	}

	@Override
	public void draw(Batch spriteBatch, float alpha) {
		// TODO: Won't be using getWidth() forever.  Use animation frame size around origin.
		spriteBatch.draw(spriteSheet, this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

}
