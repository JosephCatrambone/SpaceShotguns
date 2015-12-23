package com.josephcatrambone.metalskyarena;

import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.josephcatrambone.metalskyarena.MainGame;

import static com.josephcatrambone.metalskyarena.PhysicsConstants.LEVEL_COLLISION_MASK;
import static com.josephcatrambone.metalskyarena.PhysicsConstants.PPM;

/**
 * Created by Jo on 12/22/2015.
 */
public class Level {
	public final int[] BACKGROUND_LAYERS = new int[]{0, 1}; // Background + foreground.  No overlay yet.
	public final int[] OVERLAY_LAYERS = new int[]{2};
	public final String COLLISION_LAYER = "collision";
	TiledMap map;
	TiledMapRenderer renderer;
	Body collision;

	public Level() {}
	public Level(String filename) {
		load(filename);
	}

	public void load(String filename) {
		// Use asset manager to resolve the asset loading.
		// See the extra setup in MainGame.
		//assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		//assetManager.load("level1.tmx", TiledMap.class);
		// TiledMap map = assetManager.get("level1.tmx");
		// For external maps:
		// map = new TmxMapLoader(new ExternalFileHandleResolver()).load(filename);
		map = new TmxMapLoader().load(filename);
		renderer = new OrthogonalTiledMapRenderer(map);

		// Create the rigid bodies for this map for collisions.
		BodyDef bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.StaticBody;
		collision = MainGame.world.createBody(bdef);
		//TiledMapTileLayer mapLayer = (TiledMapTileLayer) // TODO: Error checking.
		MapObjects mapObjects = map.getLayers().get(COLLISION_LAYER).getObjects();
		// First, make the rectangle colliders.
		for(RectangleMapObject rob : mapObjects.getByType(RectangleMapObject.class)) {
			// Boxes come back in pixel coordinates, not map coordinates.
			PolygonShape shape = new PolygonShape();
			Rectangle r = rob.getRectangle(); // Rekt? Not rekt?
			float w = r.getWidth();
			float h = r.getHeight();
			float x = r.getX();
			float y = r.getY();
			shape.setAsBox(w/PPM, h/PPM);
			shape.set(new float[]{ // Assumes exterior is right hand of each line in XY order.  CCW winding.
				x/PPM, y/PPM, (x+w)/PPM, (y)/PPM, (x+w)/PPM, (y+h)/PPM, (x)/PPM, (y+h)/PPM
			});
			FixtureDef fdef = new FixtureDef();
			fdef.shape = shape;
			Fixture f = collision.createFixture(fdef);
		}
		for(PolygonMapObject pob : mapObjects.getByType(PolygonMapObject.class)) {
			// TODO: Poly support.
		}
	}

	public void dispose() {
		// TODO: Unload sprite sheet.
		MainGame.world.destroyBody(collision);
	}

	public void drawBG(Camera camera) {
		renderer.setView((OrthographicCamera)camera);
		renderer.render(BACKGROUND_LAYERS);
	}

	public void drawOverlay(Camera camera) {
		renderer.setView((OrthographicCamera)camera);
		renderer.render(OVERLAY_LAYERS);
	}

	public void act(float deltatime) {

	}
}
