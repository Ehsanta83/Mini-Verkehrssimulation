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
    private EVehiclesTrafficLight m_color = EVehiclesTrafficLight.RED;
    private final int[] m_duration;
    private int m_time;

    /**
     * traffic light constructor
     */
    protected IBaseTrafficLight( final int... p_duration )
    {
        m_duration = p_duration;
        m_time = m_duration[m_color.ordinal()];
    }

    /**
     * get traffic light status
     * @return traffic light status
     */
    public EVehiclesTrafficLight getStatus()
    {
        return m_status;
    }

    /**
     * set traffic light status
     * @param p_status traffic light status
     */
    public void setStatus( final EVehiclesTrafficLight p_status )
    {
        this.m_status = p_status;
    }

    @Override
    public Object call()
    {
        m_time--;
        if ( m_time <= 0 )
        {
            m_color = m_color.call();
            m_time = m_duration[m_color.ordinal()];
        }
        return this;
    }
}
