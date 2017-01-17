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
     * @param p_duration duration of traffic light colors
     */
    protected CVehiclesTrafficLight( final int... p_duration )
    {
        super( EVehiclesTrafficLight.RED, p_duration );
    }
}
