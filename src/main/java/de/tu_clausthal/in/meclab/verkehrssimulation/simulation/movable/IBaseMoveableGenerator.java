package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IBaseObjectGenerator;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.Set;


/**
 *
 * @todo implement agent action for generating objects
 */
public abstract class IBaseMoveableGenerator<T extends IMoveable<?>> extends IBaseAgent<T>
{
    protected final IEnvironment<?> m_environment;


    /**
     * ctor
     *
     * @param p_configuration agent configuration
     */
    protected IBaseMoveableGenerator( final IAgentConfiguration<T> p_configuration, final IEnvironment<?> p_environment )
    {
        super( p_configuration );
        m_environment = p_environment;
    }


    /**
     * generator instance for generated agent
     */
    protected abstract class IGenerator extends IBaseObjectGenerator<T>
    {


        /**
         * @param p_stream
         * @param p_actions
         * @param p_aggregation
         * @param p_environment
         * @throws Exception
         */
        public IGenerator( final InputStream p_stream, final Set<IAction> p_actions,
                           final IAggregation p_aggregation,
                           final IEnvironment<?> p_environment
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, p_environment );
        }
    }

}
