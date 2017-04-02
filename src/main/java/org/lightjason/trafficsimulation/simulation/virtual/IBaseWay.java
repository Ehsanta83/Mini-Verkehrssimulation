package org.lightjason.trafficsimulation.simulation.virtual;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.simulation.IBaseObject;
import org.lightjason.trafficsimulation.simulation.IBaseObjectGenerator;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;

import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Stream;


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
     * @param p_values
     * @bug check parameter
     */
    protected IBaseWay( final IAgentConfiguration<T> p_configuration, final IEnvironment p_environment,
                        final String p_functor, final String p_name, final Number... p_values )
    {
        super( p_configuration, p_environment, p_functor, p_name );

        m_rotation = p_values[0].intValue();
        m_position = new DenseDoubleMatrix1D( Arrays.stream( p_values ).skip( 1 ).mapToDouble( Number::doubleValue ).toArray() );

    }

    protected abstract static class IGenerator<T extends IVirtual<?>> extends IBaseObjectGenerator<T>
    {

        /**
         * @param p_stream
         * @param p_actions
         * @param p_aggregation
         * @param p_agentclass
         * @param p_environment @throws Exception on any error
         */
        protected IGenerator( final InputStream p_stream, final Stream<IAction> p_actions,
                              final IAggregation p_aggregation,
                              final Class<T> p_agentclass,
                              final IEnvironment p_environment
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, p_agentclass, p_environment );
        }
    }
}
