package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.human;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IAgent;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import org.lightjason.agentspeak.language.execution.IVariableBuilder;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.Collections;
import java.util.Random;
import java.util.Set;

/**
 * pedestrian generator
 */
public class CPedestrianGenerator extends IBaseAgentGenerator<IAgent>
{
    /**
     * environment reference
     */
    private final IEnvironment m_environment;
    /**
     * random generator
     */
    private final Random m_random = new Random();

    /**
     * ctor
     *
     * @param p_environment environment
     * @param p_stream input asl stream
     * @param p_actions action set
     * @param p_aggregation aggregation set
     * @throws Exception on any error
     */
    public CPedestrianGenerator( final IEnvironment p_environment, final InputStream p_stream,
                             final Set<IAction> p_actions, final IAggregation p_aggregation ) throws Exception
    {
        super( p_stream, p_actions, p_aggregation, Collections.emptySet(), IVariableBuilder.EMPTY );
        m_environment = p_environment;
    }

    @Override
    public IAgent generatesingle( final Object... p_data )
    {
        return new CPedestrian(
                m_environment,
                m_configuration
        );
    }
}
