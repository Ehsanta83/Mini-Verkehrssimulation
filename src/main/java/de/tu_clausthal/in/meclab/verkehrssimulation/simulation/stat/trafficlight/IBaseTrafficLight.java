package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.IStatic;

/**
 * traffic light abstract class
 *
 * @author Ehsan Tatasadi
 */
public abstract class IBaseTrafficLight<T extends Enum<T> & IETrafficLight> implements IStatic
{
    /**
     * color of traffic light
     */
    private T m_color;
    /**
     * duration of each color
     */
    private final int[] m_duration;
    /**
     * duration of current color
     */
    private int m_time;
    /**
     * sprite
     */
    private Sprite m_sprite;
    /**
     * texture of the sprite
     */
    private Texture m_texture;
    /**
     * x position
     */
    private int m_xPosition;
    /**
     * y position
     */
    private int m_yPosition;

    /**
     * constructor
     *
     * @param p_startColor start color of the traffic light
     * @param p_startColorDuration duration of the start color
     * @param p_duration duration of the traffic light colors
     */
    protected IBaseTrafficLight( final T p_startColor, final int p_startColorDuration, final int p_xPosition, final int p_yPosition,  final int... p_duration )
    {
        m_color = p_startColor;
        m_time = p_startColorDuration;
        m_xPosition = p_xPosition;
        m_yPosition = p_yPosition;
        m_duration = p_duration;
        m_texture = new Texture( Gdx.files.internal( "trafficlights/tl_" + m_color.toString().toLowerCase() + ".png" ) );
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

    /**
     * get color of the traffic light
     *
     * @return color of the traffic light
     */
    public T getColor()
    {
        return m_color;
    }

    @Override
    public Sprite sprite()
    {
        return m_sprite;
    }

    @Override
    public void spriteInitialize()
    {
        m_sprite = new Sprite( m_texture );
    }


}
