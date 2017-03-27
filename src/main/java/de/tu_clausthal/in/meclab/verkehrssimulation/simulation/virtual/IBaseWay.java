package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;


/**
 * Way abstract class
 */
public abstract class IBaseWay<T extends IBaseWay<?>> extends IBaseAgent<T> implements IVirtual<T>
{
    /**
     * defines the left bottom position in grid (row / column), width, height,
     */
    protected final DoubleMatrix1D m_position;
    /**
     * sprite
     */
    private Sprite m_sprite;
    /**
     * rotation of the traffic light
     */
    private final int m_rotation;

    /**
     * constructor
     *
     * @param p_configuration agent configuration
     * @param p_leftbottom leftbottom position in grid
     * @param p_righttop righttop position in grid
     * @param p_rotation rotation
     * @bug check parameter
     */
    protected IBaseWay( final IAgentConfiguration<T> p_configuration, final DoubleMatrix1D p_leftbottom, final DoubleMatrix1D p_righttop, final int p_rotation )
    {
        super( p_configuration );

        if ( ( p_leftbottom == null ) || ( p_leftbottom.size() != 2 ) )
            throw new RuntimeException( "left-bottom corner is not set" );
        if ( ( p_righttop == null ) || ( p_righttop.size() != 2 ) )
            throw new RuntimeException( "right-top corner is not set" );

        m_position = new DenseDoubleMatrix1D( new double[]{
            p_leftbottom.get( 0 ),
            p_leftbottom.get( 1 ),
            p_righttop.get( 0 ) - p_leftbottom.get( 0 ) + 1,
            p_righttop.get( 1 ) - p_leftbottom.get( 1 ) + 1
        } );
        m_rotation = p_rotation;
    }


    /**
     * sprite initialize
     *
     * @param p_texture texture
     * @bug incomplete
     */
    public void spriteinitialize( final float p_unit, final Texture p_texture )
    {
        m_sprite = new Sprite( p_texture );
        //m_sprite.setPosition( (float) m_spriteposition.get( 0 ), (float) m_spriteposition.get( 1 ) );
        m_sprite.setOrigin( 0, 0 );
        m_sprite.setRotation( m_rotation );
        //ToDO: I will change this after changing to another game engine
        m_sprite.setSize( 28, 4 );
    }

    @Override
    public Sprite sprite()
    {
        return m_sprite;
    }

}
