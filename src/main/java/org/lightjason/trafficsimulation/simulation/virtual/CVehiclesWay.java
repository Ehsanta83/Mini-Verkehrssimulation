package org.lightjason.trafficsimulation.simulation.virtual;

import org.lightjason.trafficsimulation.simulation.IObject;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.stream.Stream;


/**
 * vehicles way class
 */
public class CVehiclesWay extends IBaseWay<CVehiclesWay>
{
    private static final String FUNCTOR = "vehicleway";

    /**
     * constructor
     *
     * @param p_configuration agent configuration
     * @param p_environment
     * @param p_rotation rotation
     * @param p_position @bug check parameter
     */
    protected CVehiclesWay(
        final IAgentConfiguration<CVehiclesWay> p_configuration,
        final IEnvironment p_environment,
        final String p_functor,
        final int p_rotation,
        final Number... p_position
    )
    {
        super( p_configuration, p_environment, FUNCTOR, p_rotation, p_position );
    }



    @Override
    protected final Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object )
    {
        return Stream.of();
    }
}
