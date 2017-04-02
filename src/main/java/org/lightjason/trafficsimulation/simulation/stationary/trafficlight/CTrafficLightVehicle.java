package org.lightjason.trafficsimulation.simulation.stationary.trafficlight;

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
 * vehicles traffic light class
 */
public final class CTrafficLightVehicle extends IBaseTrafficLight<CTrafficLightVehicle, ELightColorVehicle>
{
    private static final String FUNCTOR = "vehiclelight";
    private static final AtomicLong m_counter = new AtomicLong();

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment environment reference
     * @param p_position
     * @param p_rotation
     */
    private CTrafficLightVehicle(
        final IAgentConfiguration<CTrafficLightVehicle> p_configuration,
        final IEnvironment p_environment, final String p_name,
        final DoubleMatrix1D p_position,
        final int p_rotation
    )
    {
        super( p_configuration, p_environment, FUNCTOR, p_name, ELightColorVehicle.class, p_position, p_rotation );
    }



    @Override
    protected final Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object )
    {
        return Stream.of();
    }


    /**
     * generator for vehicle traffic light agents
     *
     * @bug fix documentation
     */
    public static final class CGenerator extends IGenerator<CTrafficLightVehicle>
    {

        public CGenerator( final InputStream p_stream, final Stream<IAction> p_actions,
                           final IAggregation p_aggregation,
                           final IEnvironment p_environment
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, CTrafficLightVehicle.class, p_environment );
        }

        @Override
        protected final Pair<CTrafficLightVehicle, Stream<String>> generate( final IEnvironment p_environment, final DoubleMatrix1D p_position, final int p_rotation )
        {
            return new ImmutablePair<>(
                                        new CTrafficLightVehicle(
                                                                  m_configuration,
                                                                  p_environment,
                                                                  MessageFormat.format( "{0} {1}", FUNCTOR, m_counter.getAndIncrement() ),
                                                                  p_position,
                                                                  p_rotation
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
