package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

/**
 * pedestrian traffic light class
 *
 * @author Ehsan Tatasadi
 */
public class CPedestrianTrafficLight extends IBaseTrafficLight<EPedestrianTrafficLight>
{

    /**
     * traffic light constructor
     *
     * @param p_duration duration of traffic light colors
     */
    protected CPedestrianTrafficLight( final int... p_duration)
    {
        super( EPedestrianTrafficLight.RED, p_duration );
    }
}
