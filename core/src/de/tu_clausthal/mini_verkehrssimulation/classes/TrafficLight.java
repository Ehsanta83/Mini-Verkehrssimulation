/**
 * 
 */
package de.tu_clausthal.mini_verkehrssimulation.classes;

/**
 * @author Ehsan Tatasadi
 *
 */
public class TrafficLight {
	private TrafficLightStatus status;
	
	public TrafficLight(TrafficLightStatus status){
		this.status = status;
	}

	public TrafficLightStatus getStatus() {
		return status;
	}

	public void setStatus(TrafficLightStatus status) {
		this.status = status;
	}

}
