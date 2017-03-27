package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import cern.colt.matrix.DoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.Set;


/**
 * abstract generator for traffic lights
 *
 * @bug fix documentation
 */
public abstract class IBaseTrafficLightGenerator<T extends IBaseTrafficLight<?, ?>>  extends IBaseAgentGenerator<T>
{
    private final IEnvironment<?> m_environment;

    protected IBaseTrafficLightGenerator( final InputStream p_stream, final Set<IAction> p_actions,
                                          final IAggregation p_aggregation, final IEnvironment<?> p_environment
    ) throws Exception
    {
        super( p_stream, p_actions, p_aggregation );
        m_environment = p_environment;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public final T generatesingle( final Object... p_data )
    {
        return this.generate( m_environment, (DoubleMatrix1D) p_data[0], (int) p_data[1] );
    }

    /**
     * generates the agent
     *
     * @param p_environment
     * @param p_position
     * @param p_rotation
     * @return
     */
    protected abstract T generate( final IEnvironment<?> p_environment, final DoubleMatrix1D p_position, final int p_rotation );

}
