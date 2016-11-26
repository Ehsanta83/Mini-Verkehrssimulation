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
	private Block[] firstLineBlocks, secondLineBlocks;
	
	public Street() {
		super();
		this.trafficLight = new TrafficLight(TrafficLightStatus.RED);
		firstLineBlocks = new Block[]{new Block(), new Block(), new Block(), new Block(), new Block(), new Block(), new Block(), new Block(), new Block()};
		secondLineBlocks = new Block[]{new Block(), new Block(), new Block(), new Block(), new Block(), new Block(), new Block(), new Block(), new Block()};
	}

	public TrafficLight getTrafficLight() {
		return trafficLight;
	}

	public void setTrafficLight(TrafficLight trafficLight) {
		this.trafficLight = trafficLight;
	}
	
	public void turnTrafficLightToRed(){
		this.trafficLight.setStatus(TrafficLightStatus.RED);
	}
	
	public void turnTrafficLightToGreen(){
		this.trafficLight.setStatus(TrafficLightStatus.GREEN);
	}
	
	public void occupyBlockInFirstLine(int blockIndex){
		firstLineBlocks[blockIndex].setIsOccupied(true);
	}
	
	public void occupyBlockInSecondLine(int blockIndex){
		secondLineBlocks[blockIndex].setIsOccupied(true);
	}
	
	public void emptyBlockInFirstLine(int blockIndex){
		firstLineBlocks[blockIndex].setIsOccupied(false);
	}
	
	public void emptyBlockInSecondLine(int blockIndex){
		secondLineBlocks[blockIndex].setIsOccupied(false);
	}
	
	public boolean isBlockInFirstLineOccupied(int blockIndex){
		return firstLineBlocks[blockIndex].getIsOccupied();
	}
	
	public boolean isBlockInSecondLineOccupied(int blockIndex){
		return secondLineBlocks[blockIndex].getIsOccupied();
	}
	
	public int getNextOccupiedBlockIndexInFirstLineFromIndex(int blockIndex){
		int nextOccupiedBlockIndex = -1;
		for(int i = (blockIndex == -1) ? 0 : blockIndex + 1; i < firstLineBlocks.length; i++){
			if(firstLineBlocks[i].getIsOccupied()){
				nextOccupiedBlockIndex = i;
				break;
			}
		}
		return nextOccupiedBlockIndex;
	}
	
	public int getNextOccupiedBlockIndexInSecondLineFromIndex(int blockIndex){
		int nextOccupiedBlockIndex = -1;
		for(int i = (blockIndex == -1) ? 0 : blockIndex + 1; i < secondLineBlocks.length; i++){
			if(secondLineBlocks[i].getIsOccupied()){
				nextOccupiedBlockIndex = i;
				break;
			}
		}
		return nextOccupiedBlockIndex;
	}
}
