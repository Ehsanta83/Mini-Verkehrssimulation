package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IBaseObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;

import java.util.Arrays;


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
     * @param p_rotation rotation
     * @param p_position
     * @bug check parameter
     */
    protected IBaseWay( final IAgentConfiguration<T> p_configuration, final IEnvironment p_environment, final String p_functor,
                        final int p_rotation, final Number... p_position )
    {
        super( p_configuration, p_environment, p_functor );

        m_rotation = p_rotation;
        m_position = new DenseDoubleMatrix1D( Arrays.stream( p_position ).mapToDouble( Number::doubleValue ).toArray() );

    }

}
