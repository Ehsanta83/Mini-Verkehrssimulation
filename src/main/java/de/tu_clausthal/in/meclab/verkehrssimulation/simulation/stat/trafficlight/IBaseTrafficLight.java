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
     * @param p_startColor start color of the traffic light
     * @param p_startColorDuration duration of the start color
     * @param p_duration duration of the traffic light colors
     */
    protected IBaseTrafficLight( final T p_startColor, final int p_startColorDuration, final int... p_duration )
    {
        m_color = p_startColor;
        m_duration = p_duration;
        m_time = p_startColorDuration;
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
