package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;

import java.util.List;


/**
 * abstract base lane class
 */
public abstract class IBaseLane<T extends IBaseLane<?>> extends IBaseAgent<T> implements ILane<T>
{
    /**
     * status (passable, not passable)
     */
    protected boolean m_passable = true;
    /**
     * defines the left upper position (row / column / height in cells / width in cells )
     */
    protected final DoubleMatrix1D m_position;

    /**
     * ctor
     *
     * @param p_leftbottom left-bottom position
     * @param p_righttop right-up position
     * @param p_configuration agent configuration
     * @todo check parameter
     */
    protected IBaseLane( final IAgentConfiguration<T> p_configuration, final List<Integer> p_leftbottom, final List<Integer> p_righttop )
    {
        super( p_configuration );
        m_position = new DenseDoubleMatrix1D( new double[]{
            p_leftbottom.get( 0 ),
            p_leftbottom.get( 1 ),
            p_righttop.get( 0 ) - p_leftbottom.get( 0 ) + 1,
            p_righttop.get( 1 ) - p_leftbottom.get( 1 ) + 1
        } );
    }

    @Override
    public Sprite sprite()
    {
        return null;
    }

    @Override
    public void spriteinitialize( float p_unit )
    {
    }

    @Override
    public DoubleMatrix1D position()
    {
        return m_position;
    }

}
