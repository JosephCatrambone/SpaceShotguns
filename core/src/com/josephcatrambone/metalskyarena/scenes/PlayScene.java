package com.josephcatrambone.metalskyarena.scenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.josephcatrambone.metalskyarena.MainGame;

/**
 * Created by Jo on 12/20/2015.
 */
public class PlayScene extends Scene {
	Stage stage;
	SpriteBatch batch;

	@Override
	public void create() {
		stage = new Stage();

	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void render(float deltaTime) {
		stage.draw();
	}

	@Override
	public void update(float deltaTime) {
		MainGame.world.step(deltaTime, 8, 3);
		stage.act(deltaTime);
	}

}
