package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.List;
import java.util.stream.Stream;


/**
 * footway class
 */

public class CSidewalk extends IBaseLane<CSidewalk>
{
    private static final String FUNCTOR = "sidealk";

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment
     * @param p_leftbottom left-bottom position
     * @param p_righttop right-up position
     * @todo check parameter
     */
    protected CSidewalk(
        final IAgentConfiguration<CSidewalk> p_configuration,
        final IEnvironment p_environment,
        final List<Integer> p_leftbottom,
        final List<Integer> p_righttop
    )
    {
        super( p_configuration, p_environment, FUNCTOR, p_leftbottom, p_righttop );
    }



    @Override
    protected final Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object )
    {
        return Stream.of();
    }
}

