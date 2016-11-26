/**
 * 
 */
package de.tu_clausthal.mini_verkehrssimulation;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Ehsan Tatasadi
 *
 */
public class Simulation extends ApplicationAdapter{
	int countOfCars = 5;
	
	TiledMap roadsTiledMap;
	OrthographicCamera camera;
	TiledMapRenderer roadsTiledMapRenderer;
	SpriteBatch sb;
    Texture carTexture;
    Sprite carSprite;
    ShapeRenderer trafficLightShapeRenderer;

	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        
		roadsTiledMap = new TmxMapLoader().load("roads.tmx");
		roadsTiledMapRenderer = new OrthogonalTiledMapRenderer(roadsTiledMap);
		
		sb = new SpriteBatch();
		carTexture = new Texture(Gdx.files.internal("car.png"));
		carSprite = new Sprite(carTexture);

		// going left to right
		carSprite.setPosition(-carSprite.getWidth(), Gdx.graphics.getHeight() / 2 - 24); 
		// going up to down
		//carSprite.setPosition(Gdx.graphics.getWidth()/2 - 64, Gdx.graphics.getHeight());
		//carSprite.setRotation(-90);
		
		trafficLightShapeRenderer = new ShapeRenderer();
		

	}
	
	@Override
    public void dispose() {
		sb.dispose();
		carTexture.dispose();
    }

	@Override
	public void render () {
        carSprite.translateX(1);

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		roadsTiledMapRenderer.setView(camera);
		roadsTiledMapRenderer.render();
		
		sb.begin();
		carSprite.draw(sb);
        sb.end();
		
		trafficLightShapeRenderer.begin(ShapeType.Filled);
		trafficLightShapeRenderer.setColor(Color.RED);
		//trafficLightShapeRenderer.setColor(Color.GREEN);
		trafficLightShapeRenderer.circle(576, 560, 8);
		trafficLightShapeRenderer.end();
		
	}
}
