package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.IStatic;

/**
 * traffic light abstract class
 *
 * @author Ehsan Tatasadi
 */
public abstract class IBaseTrafficLight<T extends Enum<T> & IETrafficLight> implements IStatic
{
    private T m_color;
    private final int[] m_duration;
    private int m_time;

    /**
     * traffic light constructor
     *
     * @param p_color default color of traffic light
     * @param p_duration duration of traffic light colors
     */
    protected IBaseTrafficLight( T p_color, final int... p_duration )
    {
        m_color = p_color;
        m_duration = p_duration;
        m_time = m_duration[m_color.ordinal()];
    }

    @Override
    public Object call() throws Exception
    {
        m_time--;
        if ( m_time <= 0 )
        {
            m_color = (T) m_color.call();
            m_time = m_duration[m_color.ordinal()];
        }
        return this;
    }

    public T getColor()
    {
        return m_color;
    }
}
