/**
 * 
 */
package de.tu_clausthal.in.meclab.verkehrssimulation.core.classes;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * @author Ehsan Tatasadi
 *
 */
public class Car {
	private Sprite sprite;
	private int velocity;
	private String currentStreet;
	private String currentDrivingDirection;
	private int blockIndex;
	private CarTurning turning;
	private boolean isTurned;
	private int index;
		
	public Car(Sprite sprite, int velocity, String currentStreet, String currentDrivingDirection, CarTurning turning) {
		this.sprite = sprite;
		this.velocity = velocity;
		this.currentStreet = currentStreet;
		this.currentDrivingDirection = currentDrivingDirection;
		this.blockIndex = -1;
		this.turning = turning;	
		this.isTurned = false;
		this.index = -1;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	public String getCurrentStreet() {
		return currentStreet;
	}

	public void setCurrentStreet(String currentStreet) {
		this.currentStreet = currentStreet;
	}

	public int getBlockIndex() {
		return blockIndex;
	}

	public void setBlockIndex(int blockIndex) {
		this.blockIndex = blockIndex;
	}

	public CarTurning getTurning() {
		return turning;
	}

	public void setTurning(CarTurning turning) {
		this.turning = turning;
	}
	
	public String getCurrentDrivingDirection() {
		return currentDrivingDirection;
	}

	public void setCurrentDrivingDirection(String currentDrivingDirection) {
		this.currentDrivingDirection = currentDrivingDirection;
	}

	public boolean isTurned() {
		return isTurned;
	}

	public void setTurned(boolean isTurned) {
		this.isTurned = isTurned;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void move(){
		if(currentDrivingDirection.equals("east")){
			sprite.translateX(velocity);
		}else if(currentDrivingDirection.equals("north")){
			sprite.translateY(velocity);
		}else if(currentDrivingDirection.equals("west")){
			sprite.translateX(-velocity);
		}else if(currentDrivingDirection.equals("south")){
			sprite.translateY(-velocity);
		}
	}
}
