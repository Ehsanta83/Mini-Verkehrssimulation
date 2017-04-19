package org.lightjason.trafficsimulation.simulation.movable;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.simulation.IObject;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;
import org.lightjason.trafficsimulation.simulation.virtual.EArea;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;


/**
 * vehicle class
 */
@IAgentAction
public final class CVehicle extends IBaseMoveable<CVehicle>
{
    private static final String FUNCTOR = "vehicle";
    private static final AtomicLong COUNTER = new AtomicLong();

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment environment
     * @param p_position position
     */
    private CVehicle(
        final IAgentConfiguration<CVehicle> p_configuration,
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


    @IAgentActionFilter
    @IAgentActionName( name = "accelerate" )
    private void accelerate( final Number p_value )
    {

    }

    @IAgentActionFilter
    @IAgentActionName( name = "deccelerate" )
    private void deccelerate( final Number p_value )
    {

    }


    /**
     * generator class
     */
    public static final class CGenerator extends IGenerator<CVehicle>
    {

        /**
         * generator
         *
         * @param p_stream stream
         * @param p_actions action
         * @param p_aggregation aggregation
         * @param p_environment environment
         * @throws Exception on any error
         */
        public CGenerator( final InputStream p_stream, final Stream<IAction> p_actions, final IAggregation p_aggregation,
                           final IEnvironment p_environment, final Object... p_arguments
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, CVehicle.class, p_environment );
        }

        @Override
        @SuppressWarnings( "unchecked" )
        protected final Pair<CVehicle, Stream<String>> generate( final Object... p_data )
        {
            return new ImmutablePair<>(
                                        new CVehicle(
                                                      m_configuration,
                                                      m_environment,
                                                      MessageFormat.format( "{0} {1}", FUNCTOR, COUNTER.getAndIncrement() ),
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
            COUNTER.set( 0 );
        }
    }
}