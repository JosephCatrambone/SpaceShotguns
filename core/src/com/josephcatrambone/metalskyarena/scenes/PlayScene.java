package com.josephcatrambone.metalskyarena.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.josephcatrambone.metalskyarena.MainGame;
import com.josephcatrambone.metalskyarena.actors.Player;

/**
 * Created by Jo on 12/20/2015.
 */
public class PlayScene extends Scene {
	Stage stage;
	Camera camera;

	Box2DDebugRenderer debugRenderer;

	Player player;

	@Override
	public void create() {
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())); // Fit viewport = black bars.
		//debugRenderer = new Box2DDebugRenderer();

		// Setup camera.  Enforce y-up.
		camera = stage.getCamera();
		((OrthographicCamera)camera).setToOrtho(false, stage.getWidth(), stage.getHeight());
		camera.update(true);

		player = new Player(0, 0);
		stage.addActor(player);
		stage.addActor(new Player(25, 0));
		stage.addActor(new Player(0, 25));
		stage.addActor(new Player(-25, -25));

		// We add a global input handler so the player can shoot anywhere.
		stage.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// Unmap touch coordinates.
				Vector3 coords = camera.unproject(new Vector3(x, y, camera.near));
				player.handleTouchDown(coords.x, coords.y, button);
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("Raw x,y: " + x + "," + y);
				Vector3 coords = camera.unproject(new Vector3(x, y, camera.near));
				System.out.println("Unp x,y: " + coords.x + "," + coords.y);
				player.handleTouchUp(coords.x, coords.y, button);
			}
		});

		// TODO: When resuming, restore input processors.
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		stage.draw();
		//debugRenderer.render(MainGame.world, camera.combined);
	}

	@Override
	public void update(float deltaTime) {
		MainGame.world.step(deltaTime, 8, 3);
		stage.act(deltaTime);
		//camera.position.set(player.getPosition().scl(0.5f).sub(new Vector2(camera.viewportWidth/2, camera.viewportHeight/2)), camera.position.z); // Half way between origin and player.
		//camera.update();
	}

}
