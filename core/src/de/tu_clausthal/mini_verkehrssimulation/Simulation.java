/**
 * 
 */
package de.tu_clausthal.mini_verkehrssimulation;

import java.util.concurrent.ThreadLocalRandom;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
	int countOfCars = 5;
	int trafficLightDuration = 5;
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
		
		streetEast = new Street(2);
		streetSouth = new Street(1);
 		streetWest = new Street(0);
 		streetNorth = new Street(3);
 		
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
		for(int i=0; i< cars.length; i++){
			Car car = cars[i];
			int velocity = car.getVelocity();
			int[] position = car.getPosition();
			int x = position[0];
			//int y = position[1];
			int blockIndex = car.getBlockIndex();
			Street startStreet = car.getStartStreet();
			Street nextStreet = car.getNextStreet();
			//int startStreetIndex = startStreet.getIndex();
			//int nextStreetIndex = nextStreet.getIndex();
			CarTurning turning = car.getTurning();
						
			int nextOccupiedBlockIndex = -1;

			if(x < 432){ //area 0, 1
				nextOccupiedBlockIndex = startStreet.getNextOccupiedBlockIndexInFirstLineFromIndex(blockIndex);
				velocity = applyNagelSchreckenbergModel(velocity, blockIndex, nextOccupiedBlockIndex);
				velocity = applyTrafficLight(streetWest, velocity, blockIndex);
			}else if(x >= 480 && x < 512){
				if(turning.equals(CarTurning.RIGHT)){
					car.getSprite().setRotation(-90);
					position[0] = 480;
					car.setDrivingDirection("south");
				}
			}else if (x >= 512){
				if(turning.equals(CarTurning.LEFT)){
					car.getSprite().setRotation(90);
					position[0] = 512;
					car.setDrivingDirection("north");
				}else{
					//later change streetEast to next street
					nextOccupiedBlockIndex = nextStreet.getNextOccupiedBlockIndexInSecondLineFromIndex(blockIndex);
					velocity = applyNagelSchreckenbergModel(velocity, blockIndex, nextOccupiedBlockIndex);
				}
			}
			
			car.setVelocity(velocity);

			if(velocity > 0){
				car.move();
				if(car.isOut()) cars[i] = createRandomCar();
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
		//int random = ThreadLocalRandom.current().nextInt(1, 5);
		int random = 1; // all cars come from west
		int[] position = new int[2];
		Street startStreet = null;
		Street[] possibleNextStreets = new Street[3];
		Street nextStreet = null;
		String drivingDirection = "";
		CarTurning turning = CarTurning.NONE;
		int random2 = ThreadLocalRandom.current().nextInt(0, 3);
		//random2 = 2; 
		switch(random){
		case 1:
			position[0] = Parameters.STREETWEST_START_POINT_WIDTH;
			position[1] = Parameters.STREETWEST_START_POINT_HEIGHT;
			carSprite.setPosition(position[0], position[1]);
			startStreet = streetWest;
			drivingDirection = "east";
			possibleNextStreets = new Street[]{streetNorth, streetEast, streetSouth}; 
			nextStreet = possibleNextStreets[random2];
			if(nextStreet.equals(streetNorth)){
				turning = CarTurning.LEFT;
			}else if(nextStreet.equals(streetSouth)){
				turning = CarTurning.RIGHT;
			}
			break;
		case 2:			
			position[0] = Parameters.STREETNORTH_START_POINT_WIDTH;
			position[1] = Parameters.STREETNORTH_START_POINT_HEIGHT;
			carSprite.setPosition(position[0], position[1]);
			carSprite.setRotation(-90);
			startStreet = streetNorth;
			drivingDirection = "south";
			possibleNextStreets = new Street[]{streetEast, streetSouth, streetWest};
			nextStreet = possibleNextStreets[random2];
			if(nextStreet.equals(streetEast)){
				turning = CarTurning.LEFT;
			}else if(nextStreet.equals(streetWest)){
				turning = CarTurning.RIGHT;
			}
			break;
		case 3:
			position[0] = Parameters.STREETEAST_START_POINT_WIDTH;
			position[1] = Parameters.STREETEAST_START_POINT_HEIGHT;
			carSprite.setPosition(position[0], position[1]);
			startStreet = streetEast;
			carSprite.setRotation(180);
			drivingDirection = "west";
			possibleNextStreets = new Street[]{streetSouth, streetWest, streetNorth};
			nextStreet = possibleNextStreets[random2];
			if(nextStreet.equals(streetSouth)){
				turning = CarTurning.LEFT;
			}else if(nextStreet.equals(streetNorth)){
				turning = CarTurning.RIGHT;
			}
			break;
		case 4:
			position[0] = Parameters.STREETSOUTH_START_POINT_WIDTH;
			position[1] = Parameters.STREETSOUTH_START_POINT_HEIGHT;
			carSprite.setPosition(position[0], position[1]);
			carSprite.setRotation(90);
			startStreet = streetSouth;
			drivingDirection = "north";
			possibleNextStreets = new Street[]{streetWest, streetNorth, streetEast}; 
			nextStreet = possibleNextStreets[random2];
			if(nextStreet.equals(streetWest)){
				turning = CarTurning.LEFT;
			}else if(nextStreet.equals(streetEast)){
				turning = CarTurning.RIGHT;
			}
			break;
		}
		
		
		Car car = new Car(carSprite, velocity, position, startStreet, nextStreet, drivingDirection, turning);
		return car;
	}
	
	private int applyNagelSchreckenbergModel(int velocity, int blockIndex, int nextOccupiedBlockIndex){
		//1. rule in Nagel-Schreckenberg-Modell
		if(velocity < 5) velocity ++;
		//2. rule in Nagel-Schreckenberg-Modell
		if(nextOccupiedBlockIndex != -1) {
			if(blockIndex == -1 && nextOccupiedBlockIndex <= 5){
				velocity = nextOccupiedBlockIndex;
			}else{ 
				if(velocity > nextOccupiedBlockIndex - blockIndex - 1){
					velocity = nextOccupiedBlockIndex - blockIndex - 1;
				}
			}
		}
		//3. rule in Nagel-Schreckenberg-Modell
		int randomNum = ThreadLocalRandom.current().nextInt(1, 4); // get a random number between 1,2,3 
		if(randomNum == 1 && velocity >= 1) velocity--;
		return velocity;
	}
	
	private int applyTrafficLight(Street street, int velocity, int blockIndex) {
		if(blockIndex == 6 && velocity > 3) velocity = 3;
		if(blockIndex == 7 && velocity > 2) velocity = 2;
		if(blockIndex == 8 && street.getTrafficLight().getStatus() == TrafficLightStatus.RED) velocity = 0;
		return velocity;
	}
}
