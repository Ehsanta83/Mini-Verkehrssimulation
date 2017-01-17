package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

/**
 * turning traffic light class
 *
 * @author Ehsan Tatasadi
 */
public class CTurningTrafficLight extends IBaseTrafficLight<EVehiclesTrafficLight>
{
    /**
     * traffic light constructor
     *
     * @param p_duration duration of traffic light colors
     */
    public CTurningTrafficLight( final int... p_duration)
    {
        super( EVehiclesTrafficLight.RED, p_duration );
    }
}
