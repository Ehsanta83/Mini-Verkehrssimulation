package org.lightjason.trafficsimulation.simulation.movable;

import cern.colt.matrix.DoubleMatrix1D;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.simulation.IObject;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;


/**
 * pedestrian class
 */
public final class CPedestrian extends IBaseMoveable<CPedestrian>
{
    private static final String FUNCTOR = "pedestrian";
    private static final AtomicLong m_counter = new AtomicLong();

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment
     * @param p_position
     */
    private CPedestrian(
        final IAgentConfiguration<CPedestrian> p_configuration,
        final IEnvironment p_environment, final String p_name,
        final DoubleMatrix1D p_position
    )
    {
        super( p_configuration, p_environment, FUNCTOR, p_name, p_position );
    }


    @Override
    protected final Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object
    )
    {
        return Stream.of();
    }


    public static final class CGenerator extends IGenerator<CPedestrian>
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
                              final IEnvironment p_environment
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, CPedestrian.class, p_environment );
        }

        @Override
        @SuppressWarnings( "unchecked" )
        protected final Pair<CPedestrian, Stream<String>> generate( final Object... p_data )
        {
            return new ImmutablePair<>(
                                        new CPedestrian(
                                                         m_configuration,
                                                         m_environment,
                                                         MessageFormat.format( "{0} {1}", FUNCTOR, m_counter.getAndIncrement() ),
                                                         (DoubleMatrix1D) p_data[0]
                                        ),

                                        Stream.of( FUNCTOR, GROUP )
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
