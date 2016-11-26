/**
 * 
 */
package de.tu_clausthal.mini_verkehrssimulation.classes;

/**
 * @author Ehsan Tatasadi
 *
 */
public class Car {
	private int velocity;
	private int[] position;
	private Street from;
	private Street to;
	
	public Car(int velocity, int[] position, Street from, Street to) {
		super();
		this.velocity = velocity;
		this.position = position;
		this.from = from;
		this.to = to;
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

	public Street getFrom() {
		return from;
	}

	public void setFrom(Street from) {
		this.from = from;
	}

	public Street getTo() {
		return to;
	}

	public void setTo(Street to) {
		this.to = to;
	}
}
