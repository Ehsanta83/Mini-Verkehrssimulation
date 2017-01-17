package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

/**
 * vehicle traffic light enumeration
 *
 * @author Ehsan Tatasadi
 */
public enum EVehiclesTrafficLight implements IETrafficLight
{
    RED, REDYELLOW, GREEN, YELLOW;

    @Override
    public Object call() throws Exception
    {
        switch ( this )
        {
            case RED : return REDYELLOW;
            case REDYELLOW: return GREEN;
            case GREEN : return YELLOW;
            case YELLOW : return RED;

            default:
                return RED;
        }
    }
}
