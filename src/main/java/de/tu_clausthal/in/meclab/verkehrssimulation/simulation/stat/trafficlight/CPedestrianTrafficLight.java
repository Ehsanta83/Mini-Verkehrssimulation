package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

/**
 * pedestrian traffic light class
 *
 * @author Ehsan Tatasadi
 */
public class CPedestrianTrafficLight extends IBaseTrafficLight
{
    /**
     * pedestrian traffic light constructor
     *
     * @param p_status traffic light status
     */
    public CPedestrianTrafficLight( final ETrafficLightStatus p_status )
    {
        super( p_status );
    }
}
