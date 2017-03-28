package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable;

import cern.colt.matrix.DoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IBaseObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;



/**
 * moveable object
 *
 * @todo implement moving
 */
public abstract class IBaseMoveable<T extends IBaseMoveable<?>> extends IBaseObject<T> implements IMoveable<T>
{
    /**
     * current position of the agent
     */
    private final DoubleMatrix1D m_position;


    /**
     * ctor
     *
     * @param p_configuration agent configuration
     */
    protected IBaseMoveable( final IAgentConfiguration<T> p_configuration, final IEnvironment p_environment, final String p_literalfunctor,
                             final DoubleMatrix1D p_position
    )
    {
        super( p_configuration, p_environment, p_literalfunctor );
        m_position = p_position;
    }

}
