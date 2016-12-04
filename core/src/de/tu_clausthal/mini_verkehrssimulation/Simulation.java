/**
 * 
 */
package de.tu_clausthal.mini_verkehrssimulation;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import de.tu_clausthal.mini_verkehrssimulation.classes.Car;
import de.tu_clausthal.mini_verkehrssimulation.classes.CarTurning;
import de.tu_clausthal.mini_verkehrssimulation.classes.Parameters;
import de.tu_clausthal.mini_verkehrssimulation.classes.Street;
import de.tu_clausthal.mini_verkehrssimulation.classes.TrafficLightStatus;

/**
 * @author Ehsan Tatasadi
 *
 */
public class Simulation extends ApplicationAdapter{
	int countOfCars = 10;
	int trafficLightDuration = 5;
	TiledMap roadsTiledMap;
	OrthographicCamera camera;
	TiledMapRenderer roadsTiledMapRenderer;
	SpriteBatch spriteBatch;
    Texture carTexture;
    HashMap<String, Street> streets;
    ShapeRenderer trafficLightEastShapeRenderer, trafficLightSouthShapeRenderer, trafficLightWestShapeRenderer, trafficLightNorthShapeRenderer;
    long startTime;
    Car[] cars;
    
	@Override
	public void create () {
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
       
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        
		roadsTiledMap = new TmxMapLoader().load("roads.tmx");
		roadsTiledMapRenderer = new OrthogonalTiledMapRenderer(roadsTiledMap);
		
		
		Street streetEast = new Street(90, -90, "north", "south", 512, 480, 520, 520);
		Street streetSouth = new Street(0, 180, "east", "west", 512, 512, 488, 520);
		Street streetWest = new Street(-90, 90, "south", "north", 480, 512, 488, 488);
		Street streetNorth = new Street(180, 0, "west", "east", 480, 480, 520, 488);
		streets = new HashMap<>();
		streets.put("east", streetEast);
		streets.put("south", streetSouth);
		streets.put("west", streetWest);
		streets.put("north", streetNorth);
		 		
 		startTime = System.currentTimeMillis();
		
		trafficLightEastShapeRenderer = new ShapeRenderer();
		trafficLightSouthShapeRenderer = new ShapeRenderer();
		trafficLightWestShapeRenderer = new ShapeRenderer();
		trafficLightNorthShapeRenderer = new ShapeRenderer();
		
		spriteBatch = new SpriteBatch();
		carTexture = new Texture(Gdx.files.internal("car.png"));
		cars = new Car[countOfCars];
		for(int i=0; i < countOfCars; i++){
			cars[i] = createRandomCar();
		}
	}
	
	@Override
    public void dispose() {
		spriteBatch.dispose();
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
			streets.get("east").turnTrafficLightToGreen();
		}else if(mod >= trafficLightDuration && mod < 2 * trafficLightDuration) {
			turnAllTrafficLightsToRed();
			streets.get("south").turnTrafficLightToGreen();
		}else if(mod >= 2 * trafficLightDuration && mod < 3 * trafficLightDuration) {
			turnAllTrafficLightsToRed();
			streets.get("west").turnTrafficLightToGreen();
		}else{
			turnAllTrafficLightsToRed();
			streets.get("north").turnTrafficLightToGreen();
		}
		
		trafficLightEastShapeRenderer.begin(ShapeType.Filled);
		trafficLightEastShapeRenderer.setColor(streets.get("east").getTrafficLight().getStatus() == TrafficLightStatus.GREEN ? Color.GREEN : Color.RED);
		trafficLightEastShapeRenderer.circle(576, 552, 8);
		trafficLightEastShapeRenderer.end();
		
		trafficLightSouthShapeRenderer.begin(ShapeType.Filled);
		trafficLightSouthShapeRenderer.setColor(streets.get("south").getTrafficLight().getStatus() == TrafficLightStatus.GREEN ? Color.GREEN : Color.RED);
		trafficLightSouthShapeRenderer.circle(552, 448, 8);
		trafficLightSouthShapeRenderer.end();
		
		trafficLightWestShapeRenderer.begin(ShapeType.Filled);
		trafficLightWestShapeRenderer.setColor(streets.get("west").getTrafficLight().getStatus() == TrafficLightStatus.GREEN ? Color.GREEN : Color.RED);
		trafficLightWestShapeRenderer.circle(448, 472, 8);
		trafficLightWestShapeRenderer.end();
		
		trafficLightNorthShapeRenderer.begin(ShapeType.Filled);
		trafficLightNorthShapeRenderer.setColor(streets.get("north").getTrafficLight().getStatus() == TrafficLightStatus.GREEN ? Color.GREEN : Color.RED);
		trafficLightNorthShapeRenderer.circle(472, 576, 8);
		trafficLightNorthShapeRenderer.end();
		
		spriteBatch.begin();
		for(int i=0; i< cars.length; i++){
			Car car = cars[i];
			int velocity = car.getVelocity();
			int x = (int) car.getSprite().getX();
			int y = (int) car.getSprite().getY();
			int blockIndex = car.getBlockIndex();
			CarTurning turning = car.getTurning();
			String currentStreet = car.getCurrentStreet();
			String currentDrivingDirection = car.getCurrentDrivingDirection();
			
			int nextOccupiedBlockIndex = -1;
			int distanceFromStartInMovingAxis = 0;
			
			switch(currentStreet){
			case "west": 
				distanceFromStartInMovingAxis = x;
				break;
			case "south":
				distanceFromStartInMovingAxis = y;
				break;
			case "east":
				distanceFromStartInMovingAxis = 1024 - x;
				break;
			case "north":
				distanceFromStartInMovingAxis = 1024 - y;
				break;
			}
			
			if(distanceFromStartInMovingAxis < 432){ 
				int newBlockIndex = (int) distanceFromStartInMovingAxis / 48;
				if(newBlockIndex > blockIndex){
					if(blockIndex > -1)	streets.get(currentStreet).getFirstLine().emptyBlock(blockIndex);
					streets.get(currentStreet).getFirstLine().occupyBlock(newBlockIndex);
					blockIndex = newBlockIndex;
				}
				nextOccupiedBlockIndex = streets.get(currentStreet).getFirstLine().getNextOccupiedBlockIndexFromIndex(blockIndex);
				velocity = applyNagelSchreckenbergModel(velocity, blockIndex, nextOccupiedBlockIndex);
				velocity = applyTrafficLight(streets.get(currentStreet), velocity, blockIndex);
			}else if(distanceFromStartInMovingAxis >= 432 && distanceFromStartInMovingAxis < 480){
				if(blockIndex > -1){
					streets.get(currentStreet).getFirstLine().emptyBlock(blockIndex);
					blockIndex = -1;
				}
			}else if(distanceFromStartInMovingAxis >= 480 && distanceFromStartInMovingAxis < 512){
				if(turning.equals(CarTurning.RIGHT)){
					car.getSprite().setRotation(streets.get(currentStreet).getRightRotationAngel());
					x = streets.get(currentStreet).getNewXAfterTurningRight();
					y = streets.get(currentStreet).getNewYAfterTurningRight();
					String newStreet = streets.get(currentStreet).getNewDirectionAfterTurningRight();
					car.setCurrentStreet(newStreet);
					car.setCurrentDrivingDirection(newStreet);
					car.setTurning(CarTurning.NONE);
				}
			}else if(distanceFromStartInMovingAxis >= 512 && distanceFromStartInMovingAxis < 592){			
				if(turning.equals(CarTurning.LEFT)){
					car.getSprite().setRotation(streets.get(currentStreet).getLeftRotationAngel());
					x = streets.get(currentStreet).getNewXAfterTurningLeft();
					y = streets.get(currentStreet).getNewYAfterTurningLeft();
					String newStreet = streets.get(currentStreet).getNewDirectionAfterTurningLeft();
					car.setCurrentStreet(newStreet);
					car.setCurrentDrivingDirection(newStreet);
					car.setTurning(CarTurning.NONE);
				}
			}else if(distanceFromStartInMovingAxis >= 592 && distanceFromStartInMovingAxis < 1024){
				int newBlockIndex = (int) (distanceFromStartInMovingAxis - 592) / 48;
				if(newBlockIndex > blockIndex){
					if(blockIndex > -1)	streets.get(currentStreet).getSecondLine().emptyBlock(blockIndex);
					streets.get(currentStreet).getSecondLine().occupyBlock(newBlockIndex);
					blockIndex = newBlockIndex;
				}			
				nextOccupiedBlockIndex = streets.get(currentStreet).getSecondLine().getNextOccupiedBlockIndexFromIndex(blockIndex);
				velocity = applyNagelSchreckenbergModel(velocity, blockIndex, nextOccupiedBlockIndex);
			}else if(distanceFromStartInMovingAxis >= 1024){
				if(blockIndex > -1){
					streets.get(currentStreet).getSecondLine().emptyBlock(blockIndex);
				}
				cars[i] = createRandomCar();
				continue;
			}
			
			car.setBlockIndex(blockIndex);
			car.setVelocity(velocity);
			car.getSprite().setPosition(x, y);
			
			if(velocity > 0){
				car.move();
				
				switch(currentDrivingDirection){
				case "east":
					car.getSprite().translateX(velocity);
					break;
				case "north":
					car.getSprite().translateY(velocity);
					break;
				case "west":
					car.getSprite().translateX(-velocity);
					break;
				case "south":
					car.getSprite().translateY(-velocity);
					break;
				}
			}

			car.getSprite().draw(spriteBatch);
		}
        
        //show FPS
        int fps = Gdx.graphics.getFramesPerSecond();
        BitmapFont fpsFont = new BitmapFont();
        if(fps >= 45){
        	fpsFont.setColor(0, 1, 0, 1); //green
        }else if (fps >= 30){
        	fpsFont.setColor(1, 1, 0, 1); //yellow
        }else{
        	fpsFont.setColor(1, 0, 0, 1); //red
        }
        fpsFont.draw(spriteBatch, "FPS: " + fps, 19, 1005);
        fpsFont.setColor(1, 1, 1, 1); //white
        
        spriteBatch.end();
	}

	private void turnAllTrafficLightsToRed(){
		streets.get("west").turnTrafficLightToRed();
		streets.get("south").turnTrafficLightToRed();
		streets.get("north").turnTrafficLightToRed();
		streets.get("east").turnTrafficLightToRed();
	}
	
	private Car createRandomCar(){
		Sprite carSprite = new Sprite(carTexture);
		int velocity = ThreadLocalRandom.current().nextInt(1, 5);
		String startStreet = "";
		String currentDrivingDirection = "";
		int random = ThreadLocalRandom.current().nextInt(1, 5);
		switch(random){
		case 1:
			carSprite.setPosition(Parameters.STREETWEST_START_POINT_WIDTH, Parameters.STREETWEST_START_POINT_HEIGHT);
			startStreet = "west";
			currentDrivingDirection = "east";
			break;
		case 2:			
			carSprite.setPosition(Parameters.STREETNORTH_START_POINT_WIDTH, Parameters.STREETNORTH_START_POINT_HEIGHT);
			carSprite.setRotation(-90);
			startStreet = "north";
			currentDrivingDirection = "south";
			break;
		case 3:
			carSprite.setPosition(Parameters.STREETEAST_START_POINT_WIDTH, Parameters.STREETEAST_START_POINT_HEIGHT);
			carSprite.setRotation(180);
			startStreet = "east";
			currentDrivingDirection = "west";
			break;
		case 4:
			carSprite.setPosition(Parameters.STREETSOUTH_START_POINT_WIDTH, Parameters.STREETSOUTH_START_POINT_HEIGHT);
			carSprite.setRotation(90);
			startStreet = "south";
			currentDrivingDirection = "north";
			break;
		}
		
		random = ThreadLocalRandom.current().nextInt(1, 4);
		CarTurning turning = CarTurning.NONE;
		switch(random){
		case 1:
			turning = CarTurning.LEFT;
			break;
		case 2:			
			turning = CarTurning.RIGHT;
			break;
		}

		Car car = new Car(carSprite, velocity, startStreet, currentDrivingDirection, turning);
		return car;
	}
	
	private int applyNagelSchreckenbergModel(int velocity, int blockIndex, int nextOccupiedBlockIndex){
		//1. rule in Nagel-Schreckenberg-Modell
		if(velocity < 4) velocity ++;
		//2. rule in Nagel-Schreckenberg-Modell
		if(nextOccupiedBlockIndex != -1) {
			if(blockIndex == -1 && nextOccupiedBlockIndex <= 4){
				velocity = nextOccupiedBlockIndex;
			}else{ 
				if(velocity > nextOccupiedBlockIndex - blockIndex - 1){
					velocity = nextOccupiedBlockIndex - blockIndex - 1;
				}
			}
		}
		//3. rule in Nagel-Schreckenberg-Modell
		int random = ThreadLocalRandom.current().nextInt(1, 3); // get a random number between 1,2,3 
		if(random == 1 && velocity >= 1) velocity--;
		return velocity;
	}
	
	private int applyTrafficLight(Street street, int velocity, int blockIndex) {
		if(blockIndex == 6 && velocity > 3) velocity = 3;
		if(blockIndex == 7 && velocity > 2) velocity = 2;
		if(blockIndex == 8 && street.getTrafficLight().getStatus() == TrafficLightStatus.RED) velocity = 0;
		return velocity;
	}
}
