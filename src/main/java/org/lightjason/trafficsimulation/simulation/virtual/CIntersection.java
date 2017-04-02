package org.lightjason.trafficsimulation.simulation.virtual;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;


/**
 * intersection class
 */
public final class CIntersection extends IBaseLane<CIntersection>
{
    private static final String FUNCTOR = "intersection";
    private static final AtomicLong m_counter = new AtomicLong();

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment
     * @param p_values
     * @todo check parameter
     */
    private CIntersection(
        final IAgentConfiguration<CIntersection> p_configuration,
        final IEnvironment p_environment,
        final String p_name, final Number... p_values
    )
    {
        super( p_configuration, p_environment, FUNCTOR, p_name, p_values );
    }



    public static final class CGenerator extends IGenerator<CIntersection>
    {

        /**
         * @param p_stream
         * @param p_actions
         * @param p_aggregation
         * @param p_environment
         * @throws Exception on any error
         */
        public CGenerator( final InputStream p_stream, final Stream<IAction> p_actions,
                              final IAggregation p_aggregation,
                              final IEnvironment p_environment ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, CIntersection.class, p_environment );
        }

        @Override
        @SuppressWarnings( "unchecked" )
        protected final Pair<CIntersection, Stream<String>> generate( final Object... p_data )
        {
            return new ImmutablePair<>(
                                        new CIntersection(
                                                           m_configuration,
                                                           m_environment,
                                                           MessageFormat.format( "{0} {1}", FUNCTOR, m_counter.getAndIncrement() ),
                                                           (Number[]) p_data
                                        ),

                                        Stream.of()
            );
        }

        /**
         * reset the object counter
         */
        public static void resetcount()
        {
            m_counter.set( 0 );
        }
    }

}
