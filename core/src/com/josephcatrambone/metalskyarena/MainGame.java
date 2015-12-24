package com.josephcatrambone.metalskyarena;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.josephcatrambone.metalskyarena.scenes.PlayScene;
import com.josephcatrambone.metalskyarena.scenes.Scene;

import java.util.Random;
import java.util.Stack;

public class MainGame extends ApplicationAdapter {
	public static final Random random;
	public static final World world;
	public static final AssetManager assetManager;
	public static final Stack<Scene> scenes;

	static {
		random = new Random();
		world = new World(new Vector2(0, 0), true);
		assetManager = new AssetManager();
		scenes = new Stack<Scene>();
	}
	
	@Override
	public void create () {
		loadAllAssets();

		Scene play = new PlayScene();
		play.create();
		scenes.push(play);
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		scenes.peek().update(dt);
		scenes.peek().render(dt);
	}

	public void loadAllAssets() {
		assetManager.load("missing.png", Texture.class);
		assetManager.load("player.png", Texture.class);
		assetManager.load("flak.png", Texture.class);
		assetManager.load("slug.png", Texture.class);
		assetManager.finishLoading();
	}
}
