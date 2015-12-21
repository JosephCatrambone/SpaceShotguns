package com.josephcatrambone.metalskyarena;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.josephcatrambone.metalskyarena.scenes.PlayScene;
import com.josephcatrambone.metalskyarena.scenes.Scene;

import java.util.Stack;

public class MainGame extends ApplicationAdapter {
	public static final World world;
	public static final AssetManager assetManager;
	public static final Stack<Scene> scenes;

	static {
		world = new World(new Vector2(0, 0), true);
		assetManager = new AssetManager();
		scenes = new Stack<>();
	}
	
	@Override
	public void create () {
		scenes.push(new PlayScene());
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		scenes.peek().update(dt);
		scenes.peek().render(dt);
	}
}
