package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stationary.trafficlight;

import cern.colt.matrix.DoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.Set;


/**
 * generator of pedestrian traffic lights
 */
public final class CTrafficLightPedestrianGenerator extends IBaseTrafficLightGenerator<CTrafficLightPedestrian>
{

    public CTrafficLightPedestrianGenerator( final InputStream p_stream, final Set<IAction> p_actions,
                                             final IAggregation p_aggregation,
                                             final IEnvironment<?> p_environment
    ) throws Exception
    {
        super( p_stream, p_actions, p_aggregation, p_environment );
    }

    @Override
    protected final CTrafficLightPedestrian generate( final IEnvironment<?> p_environment, final DoubleMatrix1D p_position, final int p_rotation )
    {
        new CTrafficLightPedestrian( m_configuration, p_environment, p_position, p_rotation );
    }
}
