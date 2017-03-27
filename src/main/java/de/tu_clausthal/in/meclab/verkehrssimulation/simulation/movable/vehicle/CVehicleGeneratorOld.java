package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IBaseMoveable;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import org.lightjason.agentspeak.language.execution.IVariableBuilder;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.Collections;
import java.util.Random;
import java.util.Set;


/**
 * agent generator for dynamic / moving agents
 * @deprecated remove also from test
 */
@Deprecated
public class CVehicleGeneratorOld extends IBaseAgentGenerator<IBaseMoveable<?>>
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
    public CVehicleGeneratorOld( final IEnvironment p_environment, final InputStream p_stream,
                                 final Set<IAction> p_actions, final IAggregation p_aggregation
    ) throws Exception
    {
        super( p_stream, p_actions, p_aggregation, Collections.emptySet(), IVariableBuilder.EMPTY );
        m_environment = p_environment;
    }

    @Override
    public CVehicle generatesingle( final Object... p_data )
    {
        return null;
    }
}
