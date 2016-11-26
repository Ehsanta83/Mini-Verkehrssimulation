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
	private Block[] blocks;
	
	public Street() {
		super();
		this.trafficLight = new TrafficLight(TrafficLightStatus.RED);
		blocks = new Block[9];
	}

	public TrafficLight getTrafficLight() {
		return trafficLight;
	}

	public void setTrafficLight(TrafficLight trafficLight) {
		this.trafficLight = trafficLight;
	}

	public Block[] getBlocks() {
		return blocks;
	}

	public void setBlocks(Block[] blocks) {
		this.blocks = blocks;
	}
	
	public void turnTrafficLightToRed(){
		this.trafficLight.setStatus(TrafficLightStatus.RED);
	}
	
	public void turnTrafficLightToGreen(){
		this.trafficLight.setStatus(TrafficLightStatus.GREEN);
	}
	
}
