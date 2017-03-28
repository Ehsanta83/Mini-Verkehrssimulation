package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stationary.trafficlight;

import cern.colt.matrix.DoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.stream.Stream;


/**
 * pedestrian traffic light class
 */
public final class CTrafficLightPedestrian extends IBaseTrafficLight<CTrafficLightPedestrian, ELightColorPedestrian>
{
    private static final String FUNCTOR = "pedestrianlight";

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment environment reference
     * @param p_position
     * @param p_rotation
     */
    private CTrafficLightPedestrian(
        final IAgentConfiguration<CTrafficLightPedestrian> p_configuration,
        final IEnvironment p_environment,
        final DoubleMatrix1D p_position,
        final int p_rotation
    )
    {
        super( p_configuration, p_environment, FUNCTOR, ELightColorPedestrian.class, p_position, p_rotation );
    }


    @Override
    protected final Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object )
    {
        return Stream.of();
    }


    /**
     * generator of pedestrian traffic lights
     */
    public static final class CGenerator extends IGenerator<CTrafficLightPedestrian>
    {

        public CGenerator( final InputStream p_stream, final Stream<IAction> p_actions,
                           final IAggregation p_aggregation,
                           final IEnvironment p_environment
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, CTrafficLightPedestrian.class, p_environment );
        }

        @Override
        protected final CTrafficLightPedestrian generate( final IEnvironment p_environment, final DoubleMatrix1D p_position, final int p_rotation )
        {
            return new CTrafficLightPedestrian( m_configuration, p_environment, p_position, p_rotation );
        }
    }

}
