package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

/**
 * vehicles traffic light class
 *
 * @author Ehsan Tatasadi
 */
public class CVehiclesTrafficLight extends IBaseTrafficLight<EVehiclesTrafficLight>
{
    /**
     * traffic light constructor
     *
     * @param p_startColor start color of the traffic light
     * @param p_startColorDuration duration of the start color
     * @param p_duration duration of the traffic light colors
     */
    public CVehiclesTrafficLight( final EVehiclesTrafficLight p_startColor, final int p_startColorDuration, final int... p_duration )
    {
        super( p_startColor, p_startColorDuration, p_duration );
    }
}
