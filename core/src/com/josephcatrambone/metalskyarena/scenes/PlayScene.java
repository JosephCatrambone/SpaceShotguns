package com.josephcatrambone.metalskyarena.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.josephcatrambone.metalskyarena.MainGame;
import com.josephcatrambone.metalskyarena.Level;
import com.josephcatrambone.metalskyarena.actors.*;

/**
 * Created by Jo on 12/20/2015.
 */
public class PlayScene extends Scene {
	public final int PIXEL_DISPLAY_WIDTH = 160; // Ten pixels on a side?
	Stage stage;
	Camera camera;
	Level level;
	Player player;

	Box2DDebugRenderer debugRenderer;

	@Override
	public void create() {
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())); // Fit viewport = black bars.
		debugRenderer = new Box2DDebugRenderer();

		// Setup camera.  Enforce y-up.
		float invAspectRatio = stage.getHeight()/stage.getWidth();
		camera = stage.getCamera();
		((OrthographicCamera)camera).setToOrtho(false, PIXEL_DISPLAY_WIDTH, PIXEL_DISPLAY_WIDTH*invAspectRatio);
		camera.update(true);

		level = new Level("test.tmx");

		player = new Player(128, 128);
		stage.addActor(player);
		stage.addActor(new Pawn(125, 100));
		stage.addActor(new Pawn(100, 256));

		// We add a global input handler so the player can shoot anywhere.
		stage.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// Stage is unprojecting the coordinates for us.
				player.handleTouchDown(x, y, button);
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				player.handleTouchUp(x, y, button);
			}
		});

		// TODO: When resuming, restore input processors.
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void dispose() {
		level.dispose();
		stage.dispose();
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		level.drawBG(camera);
		stage.draw();
		//debugRenderer.render(MainGame.world, camera.combined);
	}

	@Override
	public void update(float deltaTime) {
		MainGame.world.step(deltaTime, 8, 3);
		stage.act(deltaTime);

		// Camera follows player?
		camera.position.set(player.getX(), player.getY(), camera.position.z);
		camera.update();
	}

}
