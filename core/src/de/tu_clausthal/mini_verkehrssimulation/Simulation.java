/**
 * 
 */
package de.tu_clausthal.mini_verkehrssimulation;

import java.util.concurrent.ThreadLocalRandom;

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

import de.tu_clausthal.mini_verkehrssimulation.classes.Car;
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
    Street streetEast, streetSouth, streetWest, streetNorth;
    ShapeRenderer trafficLightEastShapeRenderer, trafficLightSouthShapeRenderer, trafficLightWestShapeRenderer, trafficLightNorthShapeRenderer;
    long startTime;
    Car[] cars;
    Sprite carSprite;
    
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
		cars = new Car[countOfCars];
		for(int i=0; i < countOfCars; i++){
			cars[i] = createRandomCar();
		}
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
		for(Car car: cars){
			if(car.getStartStreet().equals(streetEast)){
				car.getSprite().translateX(-1);
			}else if(car.getStartStreet().equals(streetSouth)){
				car.getSprite().translateY(1);
			}else if(car.getStartStreet().equals(streetWest)){
				car.getSprite().translateX(1);
			}else if(car.getStartStreet().equals(streetNorth)){
				car.getSprite().translateY(-1);
			}
			car.getSprite().draw(sb);
		}
        sb.end();
		
	}
	
	private void turnAllTrafficLightsToRed(){
		streetEast.turnTrafficLightToRed();
		streetSouth.turnTrafficLightToRed();
		streetWest.turnTrafficLightToRed();
		streetNorth.turnTrafficLightToRed();
	}
	
	private Car createRandomCar(){
		Sprite carSprite = new Sprite(carTexture);
		int velocity = ThreadLocalRandom.current().nextInt(1, 6);
		int random = ThreadLocalRandom.current().nextInt(1, 5);
		int[] position = new int[2];
		Street startStreet = null;
		Street[] possibleNextStreets = new Street[3];
		Street nextStreet;
		switch(random){
		case 1:
			position[0] = -32;
			position[1] = Gdx.graphics.getHeight() / 2 - 24;
			carSprite.setPosition(position[0], position[1]);
			startStreet = streetWest;
			possibleNextStreets = new Street[]{streetNorth, streetEast, streetSouth}; 
			break;
		case 2:			
			position[0] = Gdx.graphics.getWidth()/2 - 32;
			position[1] = Gdx.graphics.getHeight() + 8;
			carSprite.setPosition(position[0], position[1]);
			carSprite.setRotation(-90);
			startStreet = streetNorth;
			possibleNextStreets = new Street[]{streetEast, streetSouth, streetWest}; 
			break;
		case 3:
			position[0] = Gdx.graphics.getWidth();
			position[1] = Gdx.graphics.getHeight() / 2 + 8;
			carSprite.setPosition(position[0], position[1]);
			startStreet = streetEast;
			carSprite.setRotation(180);
			possibleNextStreets = new Street[]{streetSouth, streetWest, streetNorth}; 
			break;
		case 4:
			position[0] = Gdx.graphics.getWidth()/2;
			position[1] = -24;
			carSprite.setPosition(position[0], position[1]);
			carSprite.setRotation(90);
			startStreet = streetSouth;
			possibleNextStreets = new Street[]{streetWest, streetNorth, streetEast}; 
			break;
		}
		random = ThreadLocalRandom.current().nextInt(0, 3);
		nextStreet = possibleNextStreets[random];
		Car car = new Car(carSprite, velocity, position, startStreet, nextStreet);
		return car;
	}
}
