/**
 * 
 */
package de.tu_clausthal.mini_verkehrssimulation.classes;

/**
 * @author Ehsan Tatasadi
 *
 */
public class Street {
	private TrafficLight trafficLight;
	private StreetLine firstLine, secondLine;
	private int rightRotationAngel;
	private int leftRotationAngel;
	private String newDirectionAfterTurningRight;
	private String newDirectionAfterTurningLeft;
	private int newXAfterTurningRight;
	private int newXAfterTurningLeft;
	private int newYAfterTurningRight;
	private int newYAfterTurningLeft;
	private String oppositeStreet;
	
	public Street(int rightRotationAngel, int leftRotationAngel, String newDirectionAfterTurningRight,
			String newDirectionAfterTurningLeft, int newXAfterTurningRight, int newXAfterTurningLeft,
			int newYAfterTurningRight, int newYAfterTurningLeft, String oppositeStreet) {
		this.rightRotationAngel = rightRotationAngel;
		this.leftRotationAngel = leftRotationAngel;
		this.newDirectionAfterTurningRight = newDirectionAfterTurningRight;
		this.newDirectionAfterTurningLeft = newDirectionAfterTurningLeft;
		this.newXAfterTurningRight = newXAfterTurningRight;
		this.newXAfterTurningLeft = newXAfterTurningLeft;
		this.newYAfterTurningRight = newYAfterTurningRight;
		this.newYAfterTurningLeft = newYAfterTurningLeft;
		this.oppositeStreet = oppositeStreet;
		this.trafficLight = new TrafficLight(TrafficLightStatus.RED);
		firstLine = new StreetLine();
		secondLine = new StreetLine();
	}

	public TrafficLight getTrafficLight() {
		return trafficLight;
	}

	public void setTrafficLight(TrafficLight trafficLight) {
		this.trafficLight = trafficLight;
	}

	public StreetLine getFirstLine() {
		return firstLine;
	}

	public void setFirstLine(StreetLine firstLine) {
		this.firstLine = firstLine;
	}

	public StreetLine getSecondLine() {
		return secondLine;
	}

	public void setSecondLine(StreetLine secondLine) {
		this.secondLine = secondLine;
	}

	public int getRightRotationAngel() {
		return rightRotationAngel;
	}

	public void setRightRotationAngel(int rightRotationAngel) {
		this.rightRotationAngel = rightRotationAngel;
	}

	public int getLeftRotationAngel() {
		return leftRotationAngel;
	}

	public void setLeftRotationAngel(int leftRotationAngel) {
		this.leftRotationAngel = leftRotationAngel;
	}

	public String getNewDirectionAfterTurningRight() {
		return newDirectionAfterTurningRight;
	}

	public void setNewDirectionAfterTurningRight(String newDirectionAfterTurningRight) {
		this.newDirectionAfterTurningRight = newDirectionAfterTurningRight;
	}

	public String getNewDirectionAfterTurningLeft() {
		return newDirectionAfterTurningLeft;
	}

	public void setNewDirectionAfterTurningLeft(String newDirectionAfterTurningLeft) {
		this.newDirectionAfterTurningLeft = newDirectionAfterTurningLeft;
	}

	public int getNewXAfterTurningRight() {
		return newXAfterTurningRight;
	}

	public void setNewXAfterTurningRight(int newXAfterTurningRight) {
		this.newXAfterTurningRight = newXAfterTurningRight;
	}

	public int getNewXAfterTurningLeft() {
		return newXAfterTurningLeft;
	}

	public void setNewXAfterTurningLeft(int newXAfterTurningLeft) {
		this.newXAfterTurningLeft = newXAfterTurningLeft;
	}

	public int getNewYAfterTurningRight() {
		return newYAfterTurningRight;
	}

	public void setNewYAfterTurningRight(int newYAfterTurningRight) {
		this.newYAfterTurningRight = newYAfterTurningRight;
	}

	public int getNewYAfterTurningLeft() {
		return newYAfterTurningLeft;
	}

	public void setNewYAfterTurningLeft(int newYAfterTurningLeft) {
		this.newYAfterTurningLeft = newYAfterTurningLeft;
	}

	public String getOppositeStreet() {
		return oppositeStreet;
	}

	public void setOppositeStreet(String oppositeStreet) {
		this.oppositeStreet = oppositeStreet;
	}

	public void turnTrafficLightToRed(){
		this.trafficLight.setStatus(TrafficLightStatus.RED);
	}
	
	public void turnTrafficLightToGreen(){
		this.trafficLight.setStatus(TrafficLightStatus.GREEN);
	}
}
