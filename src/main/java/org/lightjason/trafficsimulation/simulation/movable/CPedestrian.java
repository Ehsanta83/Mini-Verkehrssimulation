package org.lightjason.trafficsimulation.simulation.movable;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.math.EDistributionFactory;
import org.lightjason.trafficsimulation.simulation.IObject;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;
import org.lightjason.trafficsimulation.simulation.virtual.EArea;

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
    private static final AtomicLong COUNTER = new AtomicLong();

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment environment
     * @param p_position position
     */
    private CPedestrian(
        final IAgentConfiguration<CPedestrian> p_configuration,
        final IEnvironment p_environment, final String p_name,
        final DoubleMatrix1D p_position
    )
    {
        super( p_configuration, p_environment, FUNCTOR, p_name, p_position, new DenseDoubleMatrix1D( 2 ) );
    }


    @Override
    protected final Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object )
    {
        return Stream.of();
    }


    /**
     * generator class
     *
     * @bug environment position setter must be refactored
     */
    public static final class CGenerator extends IGenerator<CPedestrian>
    {

        /**
         * ctor
         *
         * @param p_stream stream
         * @param p_actions actions
         * @param p_aggregation aggregation
         * @param p_environment environment
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
            final CPedestrian l_pedestrian = new CPedestrian(
                m_configuration,
                m_environment,
                MessageFormat.format( "{0} {1}", FUNCTOR, COUNTER.getAndIncrement() ),
                new DenseDoubleMatrix1D( 2 )
            );

            return new ImmutablePair<>( l_pedestrian, Stream.of( FUNCTOR, GROUP ) );
        }

        /**
         * reset the object counter
         */
        public static void resetcount()
        {
            COUNTER.set( 0 );
        }
    }
}
