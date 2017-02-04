package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.IStatic;

import java.util.List;

/**
 * traffic light abstract class
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
     * defines the left bottom position (row / column)
     */
    private final DoubleMatrix1D m_position;

    /**
     * rotation of the traffic light
     */
    private final int m_rotation;

    /**
     * constructor
     *
     * @param p_position left bottom position
     * @param p_rotation rotation
     * @param p_startColor start color of the traffic light
     * @param p_startColorDuration duration of the start color
     * @param p_duration duration of the traffic light colors
     */
    protected IBaseTrafficLight( final List<Integer> p_position, final int p_rotation, final T p_startColor, final int p_startColorDuration, final int... p_duration )
    {
        m_position = new DenseDoubleMatrix1D( new double[]{p_position.get( 0 ), p_position.get( 1 )} );
        m_rotation = p_rotation;
        m_color = p_startColor;
        m_time = p_startColorDuration;
        m_duration = p_duration;
    }

    @Override
    public IBaseTrafficLight<T> call() throws Exception
    {
        m_time--;
        if ( m_time <= 0 )
        {
            m_color = (T) m_color.call();
            m_time = m_duration[m_color.ordinal()];
            if ( m_color.getTexture() != null )
            {
                m_sprite.setTexture( m_color.getTexture() );
            }
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
    public void spriteinitialize( final float p_unit )
    {
        m_sprite = new Sprite( m_color.getTexture() );
        m_sprite.setPosition( (float) m_position.get( 0 ), (float) m_position.get( 1 ) );
        m_sprite.setScale( p_unit );
        m_sprite.setRotation( m_rotation );
    }

    @Override
    public final DoubleMatrix1D position()
    {
        return m_position;
    }
}
