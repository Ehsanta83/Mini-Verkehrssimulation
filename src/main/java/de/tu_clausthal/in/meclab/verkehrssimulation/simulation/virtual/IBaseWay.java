package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.List;

/**
 * Way abstract class
 */
public abstract class IBaseWay implements IVirtual
{

    /**
     * sprite
     */
    private Sprite m_sprite;
    /**
     * defines the left bottom position (row / column), width, height
     */
    private final DoubleMatrix1D m_position;

    /**
     * rotation of the traffic light
     */
    private final int m_rotation;

    /**
     * constructor
     *
     * @param p_position position
     * @param p_rotation rotation
     * @param p_width width
     * @param p_height height
     */
    protected IBaseWay( final List<Integer> p_position, final int p_rotation, final int p_width, final int p_height )
    {
        m_position = new DenseDoubleMatrix1D( new double[]{p_position.get( 1 ), p_position.get( 0 ), p_width, p_height} );
        m_rotation = p_rotation;
    }

    /**
     * sprite initialize
     *
     * @param p_texture texture
     */
    public void spriteinitialize( final float p_unit, final Texture p_texture )
    {
        m_sprite = new Sprite( p_texture );
        m_sprite.setPosition( (float) m_position.get( 1 ), (float) m_position.get( 0 ) );
        m_sprite.setSize( (float) m_position.get( 2 ) * p_unit, (float) m_position.get( 3 ) * p_unit );
        m_sprite.setOrigin( 0, 0 );
        m_sprite.setRotation( m_rotation );
    }

    @Override
    public Sprite sprite()
    {
        return m_sprite;
    }

    @Override
    public final DoubleMatrix1D position()
    {
        return m_position;
    }

    @Override
    public IBaseWay call() throws Exception
    {
        return this;
    }
}
