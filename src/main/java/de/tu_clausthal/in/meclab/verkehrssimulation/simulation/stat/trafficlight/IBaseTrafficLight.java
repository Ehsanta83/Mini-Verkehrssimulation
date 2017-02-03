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
     * defines the left upper position (row / column / height in cells / width in cells )
     */
    private final DoubleMatrix1D m_position;

    /**
     * constructor
     *
     * @param p_leftbottom left-bottom position
     * @param p_rightupper right-upper position
     * @param p_startColor start color of the traffic light
     * @param p_startColorDuration duration of the start color
     * @param p_duration duration of the traffic light colors
     */
    protected IBaseTrafficLight( final List<Integer> p_leftbottom, final List<Integer> p_rightupper, final T p_startColor, final int p_startColorDuration, final int... p_duration )
    {
        m_position = new DenseDoubleMatrix1D( new double[]{
            Math.min( p_leftbottom.get( 0 ), p_rightupper.get( 0 ) ),
            Math.min( p_leftbottom.get( 1 ), p_rightupper.get( 1 ) ),

            Math.abs( p_rightupper.get( 0 ) - p_leftbottom.get( 0 ) ),
            Math.abs( p_rightupper.get( 1 ) - p_leftbottom.get( 1 ) )
        } );

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
            m_sprite.setTexture( m_color.getTexture() );
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
    public void spriteinitialize( final int p_cellsize, final float p_unit )
    {
        m_sprite = new Sprite( m_color.getTexture() );
        m_sprite.setPosition( (float) m_position.get( 0 ), (float) m_position.get( 1 ) );
        m_sprite.setScale( p_unit );
    }

    @Override
    public final DoubleMatrix1D position()
    {
        return m_position;
    }
}
