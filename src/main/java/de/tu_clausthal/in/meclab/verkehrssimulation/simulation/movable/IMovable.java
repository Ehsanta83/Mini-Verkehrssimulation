package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable;

import cern.colt.matrix.DoubleMatrix1D;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;



/**
 * agent class for modelling individual behaviours
 */
@IAgentAction
public abstract class IMovable<T extends IMovable<?>> extends IBaseAgent<T> implements IObject<T>
{
    /**
     * sprite
     */
    protected Sprite m_sprite;
    /**
     * reference to the environment
     */
    protected final IEnvironment<?> m_environment;
    /**
     * current position of the agent
     */
    protected final DoubleMatrix1D m_position;
    /**
     * rotation of the agent
     */
    protected final int m_rotation;
    /**
     * route
     */
    private final List<DoubleMatrix1D> m_route = Collections.synchronizedList( new LinkedList<>() );

    /**
     * ctor
     *
     * @param p_environment environment
     * @param p_agentconfiguration agent configuration
     * @param p_position initialize position
     */
    protected IMovable( final IAgentConfiguration<T> p_agentconfiguration, final IEnvironment p_environment, final DoubleMatrix1D p_position, final int p_rotation )
    {
        super( p_agentconfiguration );

        m_position = p_position;
        m_environment = p_environment;
        m_rotation = p_rotation;
    }

}
