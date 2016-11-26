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

import de.tu_clausthal.mini_verkehrssimulation.classes.Street;
import de.tu_clausthal.mini_verkehrssimulation.classes.TrafficLightStatus;

/**
 * @author Ehsan Tatasadi
 *
 */
public class Simulation extends ApplicationAdapter{
	int countOfCars = 5;
	int trafficLightDuration = 15;
	
	TiledMap roadsTiledMap;
	OrthographicCamera camera;
	TiledMapRenderer roadsTiledMapRenderer;
	SpriteBatch sb;
    Texture carTexture;
    Sprite carSprite;
    Street streetEast, streetSouth, streetWest, streetNorth;
    ShapeRenderer trafficLightEastShapeRenderer, trafficLightSouthShapeRenderer, trafficLightWestShapeRenderer, trafficLightNorthShapeRenderer;
    long startTime;

	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        
		roadsTiledMap = new TmxMapLoader().load("roads.tmx");
		roadsTiledMapRenderer = new OrthogonalTiledMapRenderer(roadsTiledMap);
		
		streetEast = new Street();
		streetSouth = new Street();
 		streetWest = new Street();
 		streetNorth = new Street();
 		
 		startTime = System.currentTimeMillis();
		
		trafficLightEastShapeRenderer = new ShapeRenderer();
		trafficLightSouthShapeRenderer = new ShapeRenderer();
		trafficLightWestShapeRenderer = new ShapeRenderer();
		trafficLightNorthShapeRenderer = new ShapeRenderer();
		
		
		
		sb = new SpriteBatch();
		carTexture = new Texture(Gdx.files.internal("car.png"));
		carSprite = new Sprite(carTexture);

		// going left to right
		carSprite.setPosition(-carSprite.getWidth(), Gdx.graphics.getHeight() / 2 - 24); 
		// going up to down
		//carSprite.setPosition(Gdx.graphics.getWidth()/2 - 64, Gdx.graphics.getHeight());
		//carSprite.setRotation(-90);
	}
	
	@Override
    public void dispose() {
		sb.dispose();
		carTexture.dispose();
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		roadsTiledMapRenderer.setView(camera);
		roadsTiledMapRenderer.render();
		
		long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
		double mod = elapsedTime % (trafficLightDuration * 4);
		if(mod < trafficLightDuration) {
			turnAllTrafficLightsToRed();
			streetEast.turnTrafficLightToGreen();
		}else if(mod >= trafficLightDuration && mod < 2 * trafficLightDuration) {
			turnAllTrafficLightsToRed();
			streetSouth.turnTrafficLightToGreen();
		}else if(mod >= 2 * trafficLightDuration && mod < 3 * trafficLightDuration) {
			turnAllTrafficLightsToRed();
			streetWest.turnTrafficLightToGreen();
		}else{
			turnAllTrafficLightsToRed();
			streetNorth.turnTrafficLightToGreen();
		}
		
		trafficLightEastShapeRenderer.begin(ShapeType.Filled);
		trafficLightEastShapeRenderer.setColor(streetEast.getTrafficLight().getStatus() == TrafficLightStatus.GREEN ? Color.GREEN : Color.RED);
		trafficLightEastShapeRenderer.circle(576, 552, 8);
		trafficLightEastShapeRenderer.end();
		
		trafficLightSouthShapeRenderer.begin(ShapeType.Filled);
		trafficLightSouthShapeRenderer.setColor(streetSouth.getTrafficLight().getStatus() == TrafficLightStatus.GREEN ? Color.GREEN : Color.RED);
		trafficLightSouthShapeRenderer.circle(552, 448, 8);
		trafficLightSouthShapeRenderer.end();
		
		trafficLightWestShapeRenderer.begin(ShapeType.Filled);
		trafficLightWestShapeRenderer.setColor(streetWest.getTrafficLight().getStatus() == TrafficLightStatus.GREEN ? Color.GREEN : Color.RED);
		trafficLightWestShapeRenderer.circle(448, 472, 8);
		trafficLightWestShapeRenderer.end();
		
		trafficLightNorthShapeRenderer.begin(ShapeType.Filled);
		trafficLightNorthShapeRenderer.setColor(streetNorth.getTrafficLight().getStatus() == TrafficLightStatus.GREEN ? Color.GREEN : Color.RED);
		trafficLightNorthShapeRenderer.circle(472, 576, 8);
		trafficLightNorthShapeRenderer.end();
		
		

		
		
		sb.begin();
		carSprite.translateX(1);
		carSprite.draw(sb);
        sb.end();
		
	}
	
	private void turnAllTrafficLightsToRed(){
		streetEast.turnTrafficLightToRed();
		streetSouth.turnTrafficLightToRed();
		streetWest.turnTrafficLightToRed();
		streetNorth.turnTrafficLightToRed();
	}
}
