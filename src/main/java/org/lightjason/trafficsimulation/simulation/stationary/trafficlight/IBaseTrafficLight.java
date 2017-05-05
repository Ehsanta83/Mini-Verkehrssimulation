package org.lightjason.trafficsimulation.simulation.stationary.trafficlight;

import cern.colt.matrix.DoubleMatrix1D;
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.simulation.IBaseObject;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;


/**
 * traffic light abstract class
 */
@IAgentAction
public abstract class IBaseTrafficLight<T extends IBaseTrafficLight<?, ?>, L extends Enum<?> & ITrafficLightColor<L>> extends IBaseObject<T> implements ITrafficLight<T>
{
    protected static final String GROUP = "trafficlight";
    /**
     * color of traffic light
     */
    private final AtomicReference<L> m_color;
    /**
     * rotation of the traffic light
     */
    private final int m_rotation;


    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment environment reference
     * @param p_functor functor
     * @param p_light light class
     * @param p_position position
     * @param p_rotation rotation
     */
    protected IBaseTrafficLight( final IAgentConfiguration<T> p_configuration, final IEnvironment p_environment,
                                 final String p_functor, final String p_name, final Class<L> p_light,
                                 final DoubleMatrix1D p_position, final int p_rotation
    )
    {
        super( p_configuration, p_environment, p_functor, p_name, p_position );
        m_rotation = p_rotation;
        m_color = new AtomicReference<L>( p_light.getEnumConstants()[0] );
    }



    /**
     * changes the color of the light
     */
    @IAgentActionFilter
    @IAgentActionName( name = "state/next" )
    private void nextstate()
    {
        m_color.set( m_color.get().next() );
        System.out.println( "---test---> " + m_color  );
    }


    /**
     * abstract generator for traffic lights
     *
     * @bug fix documentation
     */
    protected abstract static class IGenerator<T extends IBaseTrafficLight<?, ?>> extends IBaseGenerator<T>
    {


        /**
         * @param p_stream stream
         * @param p_actions action
         * @param p_aggregation aggregation
         * @param p_environment environment
         * @throws Exception on any error
         */
        public IGenerator( final InputStream p_stream, final Stream<IAction> p_actions,
                           final IAggregation p_aggregation, final Class<T> p_agentclass,
                           final IEnvironment p_environment
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, p_agentclass, p_environment );
        }

        @Override
        @SuppressWarnings( "unchecked" )
        protected final Pair<T, Stream<String>> generate( final Object... p_data )
        {
            return this.generate( m_environment, (DoubleMatrix1D) p_data[0], (int) p_data[1] );
        }

        /**
         * generates the agent
         *
         * @param p_environment environment
         * @param p_position position
         * @param p_rotation rotation
         * @return pair of IBaseTrafficLight and stream of strings,
         */
        protected abstract Pair<T, Stream<String>> generate( final IEnvironment p_environment, final DoubleMatrix1D p_position, final int p_rotation );

    }
}
