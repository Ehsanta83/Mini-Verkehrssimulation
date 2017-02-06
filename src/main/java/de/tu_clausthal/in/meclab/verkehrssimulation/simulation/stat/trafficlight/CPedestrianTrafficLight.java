package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import java.util.List;

/**
 * pedestrian traffic light class
 */
public class CPedestrianTrafficLight extends IBaseTrafficLight<EPedestrianTrafficLight>
{
    /**
     * traffic light constructor
     *
     * @param p_position left bottom position
     * @param p_rotation rotation
     * @param p_width width
     * @param p_height height
     * @param p_startColor start color of the traffic light
     * @param p_startColorDuration duration of the start color
     * @param p_duration duration of the traffic light colors
     */
    public CPedestrianTrafficLight( final List<Integer> p_position, final int p_rotation, final int p_width, final int p_height,
                                    final EPedestrianTrafficLight p_startColor, final int p_startColorDuration, final int... p_duration )
    {
        super( p_position, p_rotation, p_width, p_height, p_startColor, p_startColorDuration, p_duration );
    }
}
