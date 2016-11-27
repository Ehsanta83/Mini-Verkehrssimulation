/**
 * 
 */
package de.tu_clausthal.mini_verkehrssimulation.classes;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * @author Ehsan Tatasadi
 *
 */
public class Car {
	private int velocity;
	private int[] position;
	private Street startStreet;
	private Street nextStreet;
	private Sprite sprite;
	private int blockIndex;
	private String drivingDirection;
	private CarTurning turning;
	private boolean isOut;
	
	public Car(Sprite sprite, int velocity, int[] position, Street startStreet, Street nextStreet, String drivingDirection, CarTurning turning) {
		super();
		this.sprite = sprite;
		this.velocity = velocity;
		this.position = position;
		this.startStreet = startStreet;
		this.nextStreet = nextStreet;
		this.blockIndex = -1;
		this.drivingDirection = drivingDirection;
		this.turning = turning;
		this.isOut = false;
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

	public int[] getPosition() {
		return position;
	}

	public void setPosition(int[] position) {
		this.position = position;
	}

	public Street getStartStreet() {
		return startStreet;
	}

	public void setStartStreet(Street startStreet) {
		this.startStreet = startStreet;
	}

	public Street getNextStreet() {
		return nextStreet;
	}

	public void setNextStreet(Street nextStreet) {
		this.nextStreet = nextStreet;
	}

	public int getBlockIndex() {
		return blockIndex;
	}

	public void setBlockIndex(int blockIndex) {
		this.blockIndex = blockIndex;
	}

	public String getDrivingDirection() {
		return drivingDirection;
	}

	public void setDrivingDirection(String drivingDirection) {
		this.drivingDirection = drivingDirection;
	}

	public CarTurning getTurning() {
		return turning;
	}

	public void setTurning(CarTurning turning) {
		this.turning = turning;
	}
	
	public boolean isOut() {
		return isOut;
	}

	public void setOut(boolean isOut) {
		this.isOut = isOut;
	}

	public void move(){
		int[] newPosition = position;
		int newX, newY;
		switch(drivingDirection){
		case "east":
			sprite.translateX(velocity);
			newPosition[0] = newPosition[0] + velocity;
			newX = newPosition[0];
			if(newX < 432){
				int newBlockIndex = (int) newX / 48;
				if(newBlockIndex > blockIndex){
					if(blockIndex > -1)	startStreet.emptyBlockInFirstLine(blockIndex);
					startStreet.occupyBlockInFirstLine(newBlockIndex);
					blockIndex = newBlockIndex;
				}
			}else if (newX >= 432 && newX < 512){
				if(blockIndex > -1){
					startStreet.emptyBlockInFirstLine(blockIndex);
					blockIndex = -1;
				}
			}else if (newX >= 592 && newX < 1024){
				int newBlockIndex = (int) (newX - 592) / 48;
				if(newBlockIndex > blockIndex){
					if(blockIndex > -1)	nextStreet.emptyBlockInSecondLine(blockIndex);
					nextStreet.occupyBlockInSecondLine(newBlockIndex);
					blockIndex = newBlockIndex;
				}
			}else if(newX >= 1024){
				nextStreet.emptyBlockInSecondLine(blockIndex);
				isOut = true;
			}
			break;
		case "north":
			sprite.translateY(velocity);
			newPosition[1] = newPosition[1] + velocity;
			newY = newPosition[1];
			if(newY < 432){
				int newBlockIndex = (int) newY / 48;
				if(newBlockIndex > blockIndex){
					if(blockIndex > -1)	startStreet.emptyBlockInFirstLine(blockIndex);
					startStreet.occupyBlockInFirstLine(newBlockIndex);
					blockIndex = newBlockIndex;
				}
			}else if (newY >= 432 && newY < 512){
				if(blockIndex > -1){
					startStreet.emptyBlockInFirstLine(blockIndex);
					blockIndex = -1;
				}
			}else if (newY >= 592 && newY < 1024){
				int newBlockIndex = (int) (newY - 592) / 48;
				if(newBlockIndex > blockIndex){
					if(blockIndex > -1)	nextStreet.emptyBlockInSecondLine(blockIndex);
					nextStreet.occupyBlockInSecondLine(newBlockIndex);
					blockIndex = newBlockIndex;
				}
			}else if(newY >= 1024){
				nextStreet.emptyBlockInSecondLine(blockIndex);
				isOut = true;
			}
			break;
		case "west":
			sprite.translateX(-velocity);
			newPosition[0] = newPosition[0] - velocity;
			newX = newPosition[0];
			if(newX > 592){
				int newBlockIndex = (int) (1024 - newX) / 48;
				if(newBlockIndex > blockIndex){
					if(blockIndex > -1)	startStreet.emptyBlockInFirstLine(blockIndex);
					startStreet.occupyBlockInFirstLine(newBlockIndex);
					blockIndex = newBlockIndex;
				}
			}else if (newX <= 592 && newX > 512){
				if(blockIndex > -1){
					startStreet.emptyBlockInFirstLine(blockIndex);
					blockIndex = -1;
				}
			}else if (newX <= 432 && newX > 0){
				int newBlockIndex = (int) (432 - newX) / 48;
				if(newBlockIndex > blockIndex){
					if(blockIndex > -1)	nextStreet.emptyBlockInSecondLine(blockIndex);
					nextStreet.occupyBlockInSecondLine(newBlockIndex);
					blockIndex = newBlockIndex;
				}
			}else if(newX <= 0){
				nextStreet.emptyBlockInSecondLine(blockIndex);
				isOut = true;
			}
			break;
		case "south":
			sprite.translateY(-velocity);
			newPosition[1] = newPosition[1] - velocity;
			newY = newPosition[1];
			if(newY > 592){
				int newBlockIndex = (int) (1024 - newY) / 48;
				if(newBlockIndex > blockIndex){
					if(blockIndex > -1)	startStreet.emptyBlockInFirstLine(blockIndex);
					startStreet.occupyBlockInFirstLine(newBlockIndex);
					blockIndex = newBlockIndex;
				}
			}else if (newY <= 592 && newY > 512){
				if(blockIndex > -1){
					startStreet.emptyBlockInFirstLine(blockIndex);
					blockIndex = -1;
				}
			}else if (newY <= 432 && newY > 0){
				int newBlockIndex = (int) (432 - newY) / 48;
				if(newBlockIndex > blockIndex){
					if(blockIndex > -1)	nextStreet.emptyBlockInSecondLine(blockIndex);
					nextStreet.occupyBlockInSecondLine(newBlockIndex);
					blockIndex = newBlockIndex;
				}
			}else if(newY <= 0){
				nextStreet.emptyBlockInSecondLine(blockIndex);
				isOut = true;
			}
			break;
		}
		
		
		//int newY = newPosition[1];
		
		position = newPosition;
	}
	
}
