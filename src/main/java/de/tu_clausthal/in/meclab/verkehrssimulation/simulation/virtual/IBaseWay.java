package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IBaseObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;


/**
 * Way abstract class
 */
public abstract class IBaseWay<T extends IBaseWay<?>> extends IBaseObject<T> implements IVirtual<T>
{
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
     * @param p_configuration agent configuration
     * @param p_leftbottom leftbottom position in grid
     * @param p_righttop righttop position in grid
     * @param p_rotation rotation
     * @bug check parameter
     */
    protected IBaseWay( final IAgentConfiguration<T> p_configuration, final IEnvironment<?> p_environment, final String p_functor, final DoubleMatrix1D p_leftbottom, final DoubleMatrix1D p_righttop, final int p_rotation )
    {
        super( p_configuration, p_environment, p_functor );

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

}
