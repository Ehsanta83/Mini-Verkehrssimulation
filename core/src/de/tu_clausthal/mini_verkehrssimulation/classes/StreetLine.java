/**
 * 
 */
package de.tu_clausthal.mini_verkehrssimulation.classes;

/**
 * @author Ehsan Tatasadi
 *
 */
public class StreetLine {
	private Block[] blocks;
	
	public StreetLine(){
		blocks = new Block[]{new Block(), new Block(), new Block(), new Block(), new Block(), new Block(), new Block(), new Block(), new Block()};
	}
	
	public void occupyBlock(int blockIndex){
		blocks[blockIndex].setIsOccupied(true);
	}
	
	public void emptyBlock(int blockIndex){
		blocks[blockIndex].setIsOccupied(false);
	}
	
	public boolean isBlockOccupied(int blockIndex){
		return blocks[blockIndex].getIsOccupied();
	}
	
	public int getNextOccupiedBlockIndexFromIndex(int blockIndex){
		int nextOccupiedBlockIndex = -1;
		for(int i = (blockIndex == -1) ? 0 : blockIndex + 1; i < blocks.length; i++){
			if(blocks[i].getIsOccupied()){
				nextOccupiedBlockIndex = i;
				break;
			}
		}
		return nextOccupiedBlockIndex;
	}
}
