package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

/**
 * pedestrian traffic light class
 */
public class CPedestrianTrafficLight extends IBaseTrafficLight<EPedestrianTrafficLight>
{
    /**
     * traffic light constructor
     *
     * @param p_startColor start color of the traffic light
     * @param p_startColorDuration duration of the start color
     * @param p_duration duration of the traffic light colors
     */
    public CPedestrianTrafficLight( final EPedestrianTrafficLight p_startColor, final int p_startColorDuration, final int... p_duration )
    {
        super( p_startColor, p_startColorDuration, p_duration );
    }
}
