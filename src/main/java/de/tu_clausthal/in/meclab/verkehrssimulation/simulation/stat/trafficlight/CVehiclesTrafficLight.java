package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

/**
 * vehicles traffic light class
 *
 * @author Ehsan Tatasadi
 */
public class CVehiclesTrafficLight extends IBaseTrafficLight
{
    /**
     * vehicle traffic light constructor
     *
     * @param p_status traffic light status
     */
    public CVehiclesTrafficLight( final ETrafficLightStatus p_status )
    {
        super( p_status );
    }
}
