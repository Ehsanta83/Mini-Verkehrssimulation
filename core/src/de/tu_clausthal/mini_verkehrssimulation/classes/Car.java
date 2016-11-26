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
	
	public Car(Sprite sprite, int velocity, int[] position, Street startStreet, Street nextStreet) {
		super();
		this.sprite = sprite;
		this.velocity = velocity;
		this.position = position;
		this.startStreet = startStreet;
		this.nextStreet = nextStreet;
		this.blockIndex = -1;
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
	
}
