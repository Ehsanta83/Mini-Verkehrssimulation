package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import cern.colt.matrix.DoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.stream.Stream;

/**
 * vehicles traffic light class
 */
public class CTrafficLightVehicle extends IBaseTrafficLight<CTrafficLightVehicle, ELightColorVehicle>
{
    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment environment reference
     * @param p_position
     * @param p_rotation
     */
    public CTrafficLightVehicle(
        final IAgentConfiguration<CTrafficLightVehicle> p_configuration,
        final IEnvironment<?> p_environment,
        final DoubleMatrix1D p_position,
        final int p_rotation
    )
    {
        super( p_configuration, p_environment, ELightColorVehicle.class, p_position, p_rotation );
    }

    @Override
    protected final Stream<ILiteral> individual( final Stream<IObject<?>> p_object )
    {
        return Stream.of();
    }

}
