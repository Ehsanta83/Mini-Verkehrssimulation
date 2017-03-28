package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;

import java.util.List;


/**
 * intersection class
 */
public class CIntersection extends IBaseLane<CIntersection>
{
    private static final String FUNCTOR = "intersection";

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment
     * @param p_leftbottom left-bottom position
     * @param p_righttop right-up position
     * @todo check parameter
     */
    protected CIntersection(
        final IAgentConfiguration<CIntersection> p_configuration,
        final IEnvironment p_environment,
        final String p_functor,
        final List<Integer> p_leftbottom,
        final List<Integer> p_righttop
    )
    {
        super( p_configuration, p_environment, FUNCTOR, p_leftbottom, p_righttop );
    }

}
