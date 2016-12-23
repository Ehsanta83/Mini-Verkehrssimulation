package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

/**
 * turning traffic light class
 *
 * @author Ehsan Tatasadi
 */
public class CTurningTrafficLight extends IBaseTrafficLight
{
    /**
     * turning traffic light constructor
     *
     * @param p_status traffic light status
     */
    public CTurningTrafficLight( final ETrafficLightStatus p_status )
    {
        super( p_status );
    }
}
