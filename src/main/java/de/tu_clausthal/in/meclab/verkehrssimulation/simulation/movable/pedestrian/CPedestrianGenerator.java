package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.pedestrian;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IMovableAgent;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import org.lightjason.agentspeak.language.execution.IVariableBuilder;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * pedestrian generator
 */
public class CPedestrianGenerator extends IBaseAgentGenerator<IMovableAgent>
{
    /**
     * environment reference
     */
    private final IEnvironment m_environment;
    /**
     * abstract distribution
     */
    private final AbstractRealDistribution m_distribution;

    /**
     * ctor
     *
     * @param p_stream input asl stream
     * @param p_actions action set
     * @param p_aggregation aggregation set
     * @param p_environment environment
     * @throws Exception on any error
     */
    public CPedestrianGenerator( final InputStream p_stream, final Set<IAction> p_actions, final IAggregation p_aggregation, final IEnvironment p_environment )
        throws Exception
    {
        super( p_stream, p_actions, p_aggregation, Collections.emptySet(), IVariableBuilder.EMPTY );
        m_environment = p_environment;
        m_distribution = new NormalDistribution( 0.5, 0.2 );
    }

    @Override
    public CPedestrian generatesingle( final Object... p_data )
    {
        return m_distribution.sample() < m_distribution.getNumericalMean()
            ? null
            : new CPedestrian(
                m_environment,
                m_configuration
            );
    }

}
