package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable;

import cern.colt.matrix.DoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IBaseObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IBaseObjectGenerator;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.Set;
import java.util.stream.Stream;



/**
 * moveable object
 *
 * @todo implement moving
 */
public abstract class IBaseMoveable<T extends IBaseMoveable<?>> extends IBaseObject<T> implements IMoveable<T>
{
    /**
     * current position of the agent
     */
    private final DoubleMatrix1D m_position;


    /**
     * ctor
     *
     * @param p_configuration agent configuration
     */
    protected IBaseMoveable( final IAgentConfiguration<T> p_configuration, final IEnvironment p_environment, final String p_literalfunctor,
                             final DoubleMatrix1D p_position
    )
    {
        super( p_configuration, p_environment, p_literalfunctor );
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
