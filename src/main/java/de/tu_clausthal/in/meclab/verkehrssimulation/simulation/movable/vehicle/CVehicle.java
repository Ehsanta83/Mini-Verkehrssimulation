package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle;

import cern.colt.matrix.DoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IAgent;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IBaseAgent;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;

/**
 * vehicle class
 */
public class CVehicle extends IBaseAgent
{

    /**
     * ctor
     *
     * @param p_environment        environment
     * @param p_agentconfiguration agent configuration
     * @param p_position           initialize position
     */
    protected CVehicle( final IEnvironment p_environment, final IAgentConfiguration<IAgent> p_agentconfiguration, final DoubleMatrix1D p_position)
    {
        super( p_environment, p_agentconfiguration, p_position );
    }

    @Override
    public void spriteinitialize( float p_unit )
    {

    }

    @Override
    protected int speed()
    {
        return 0;
    }

    @Override
    protected double nearby()
    {
        return 0;
    }
}
