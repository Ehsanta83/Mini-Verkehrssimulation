/**
 *
 */

package de.tu_clausthal.in.meclab.verkehrssimulation.classes;

/**
 * @author Ehsan Tatasadi
 *
 */
public class Block {
	private boolean isOccupied;

	public Block(){
		this.isOccupied = false;
	}
	public boolean getIsOccupied() {
		return isOccupied;
	}

	public void setIsOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
}
