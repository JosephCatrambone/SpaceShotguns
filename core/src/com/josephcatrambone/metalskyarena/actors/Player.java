package com.josephcatrambone.metalskyarena.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.josephcatrambone.metalskyarena.MainGame;

import static com.josephcatrambone.metalskyarena.PhysicsConstants.PPM;

/**
 * Created by Jo on 12/20/2015.
 */
public class Player extends Actor {
	public enum State {IDLE, SHOOT, HIT, DEAD, NUM_STATES};

	// Gameplay


	// Sounds


	// Animation
	private final String SPRITE_SHEET_FILENAME = "player.png";
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

		// Create visible bounds.


		// Create physics body.
		World world = MainGame.world;
		BodyDef bdef = new BodyDef();
		bdef.position.set(x/PPM, y/PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		physicsBody = world.createBody(bdef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(PLAYER_HALFWIDTH, PLAYER_HALFHEIGHT);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		physicsBody.createFixture(fdef);

		// Handle input.
		setupInput();
	}

	public void setupInput() {
		// This is overridden by the network player.
		// TODO: Add this to screen instead.
		this.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				//System.out.println("up");
			}
		});
	}

	public void dispose() {
		MainGame.assetManager.unload(SPRITE_SHEET_FILENAME);
		MainGame.world.destroyBody(this.physicsBody);
	}

	@Override
	public void act(float deltaTime) {

	}

	@Override
	public void draw(Batch spriteBatch, float alpha) {

	}

}
