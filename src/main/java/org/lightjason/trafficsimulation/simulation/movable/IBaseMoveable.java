package org.lightjason.trafficsimulation.simulation.movable;

import cern.colt.matrix.DoubleMatrix1D;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.simulation.IBaseObject;
import org.lightjason.trafficsimulation.simulation.IBaseObjectGenerator;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;

import java.io.InputStream;
import java.util.stream.Stream;



/**
 * moveable object
 *
 * @todo implement moving
 * @todo can we create a main generate method for vehicles and pedestrian?
 */
public abstract class IBaseMoveable<T extends IBaseMoveable<?>> extends IBaseObject<T> implements IMoveable<T>
{
    protected final static String GROUP = "moveable";
    /**
     * current position of the agent
     * @todo can we remove position to iobject?
     */
    private final DoubleMatrix1D m_position;


    /**
     * ctor
     *
     * @param p_configuration agent configuration
     */
    protected IBaseMoveable( final IAgentConfiguration<T> p_configuration, final IEnvironment p_environment,
                             final String p_functor, final String p_name, final DoubleMatrix1D p_position
    )
    {
        super( p_configuration, p_environment, p_functor, p_name );
        m_position = p_position;
    }


    /**
     * generator
     * @param <T>
     */
    protected abstract static class IGenerator<T extends IMoveable<?>> extends IBaseObjectGenerator<T>
    {


        /**
         * @param p_stream
         * @param p_actions
         * @param p_aggregation
         * @param p_environment
         * @throws Exception on any error
         */
        protected IGenerator( final InputStream p_stream, final Stream<IAction> p_actions,
                              final IAggregation p_aggregation, final Class<T> p_agentclass,
                              final IEnvironment p_environment
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, p_agentclass, p_environment );
        }

    }
}
