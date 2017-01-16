package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.IStatic;

/**
 * pedestrian traffic light enumeration
 *
 * @author Ehsan Tatasadi
 */
public enum EPedestrianTrafficLight implements IStatic
{
    RED, GREEN;

    @Override
    public Object call() throws Exception
    {
        switch ( this )
        {
            case RED : return GREEN;
            case GREEN : return RED;

            default:
                return RED;
        }
    }
}
