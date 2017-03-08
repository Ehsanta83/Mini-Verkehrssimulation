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
     * defines the left bottom position of the sprite (row / column)
     * ToDO: we will be thinking of deleting this after changing to another game engine
     */
    private final DoubleMatrix1D m_spriteposition;

    /**
     * defines the left bottom position in grid (row / column), width, height,
     */
    private final DoubleMatrix1D m_position;

    /**
     * rotation of the traffic light
     */
    private final int m_rotation;

    /**
     * constructor
     *
     * @param p_spriteposition position
     * @param p_leftbottom leftbottom position in grid
     * @param p_righttop righttop position in grid
     * @param p_rotation rotation
     */
    protected IBaseWay( final List<Integer> p_spriteposition, final List<Integer> p_leftbottom, final List<Integer> p_righttop, final int p_rotation )
    {
        m_spriteposition = new DenseDoubleMatrix1D( new double[]{
            p_spriteposition.get( 0 ),
            p_spriteposition.get( 1 )
        } );

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
     */
    public void spriteinitialize( final float p_unit, final Texture p_texture )
    {
        m_sprite = new Sprite( p_texture );
        m_sprite.setPosition( (float) m_spriteposition.get( 0 ), (float) m_spriteposition.get( 1 ) );
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
