/**
 * 
 */
package de.tu_clausthal.in.meclab.verkehrssimulation.core.classes;

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
