package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

/**
 * pedestrian traffic light enumeration
 *
 * @author Ehsan Tatasadi
 */
public enum EPedestrianTrafficLight implements IETrafficLight
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
