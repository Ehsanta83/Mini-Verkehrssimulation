package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.IStatic;

/**
 * traffic light abstract class
 *
 * @author Ehsan Tatasadi
 */
public abstract class IBaseTrafficLight implements IStatic
{
    /**
     * traffic light status
     * red/green/yellow
     */
    private ETrafficLightStatus m_status;

    /**
     * traffic light constructor
     * @param p_status traffic light status
     */
    protected IBaseTrafficLight( final ETrafficLightStatus p_status )
    {
        this.m_status = p_status;
    }

    /**
     * get traffic light status
     * @return traffic light status
     */
    public ETrafficLightStatus getStatus()
    {
        return m_status;
    }

    /**
     * set traffic light status
     * @param p_status traffic light status
     */
    public void setStatus( final ETrafficLightStatus p_status )
    {
        this.m_status = p_status;
    }

    @Override
    public Runnable call() throws Exception
    {
        return null;
    }
}
