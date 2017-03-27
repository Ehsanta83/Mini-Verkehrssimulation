package de.tu_clausthal.in.meclab.verkehrssimulation.simulation;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight.CTrafficLightPedestrianGenerator;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight.CTrafficLightVehicleGenerator;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.generator.IAgentGenerator;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Set;


/**
 * class for generating object-generators
 */
public enum EObjectFactory
{
    TRAFFICLIGHT_PEDESTRIAN,
    TRAFFICLIGHT_VEHICLE;

    /**
     * generates a agent generator
     *
     * @param p_stream asl input stream
     * @param p_actions action list
     * @param p_aggregation
     * @param p_environment environment reference
     * @return agent generator
     * @throws Exception on any error
     */
    public final IAgentGenerator<?> generate( final InputStream p_stream, final Set<IAction> p_actions,
                                              final IAggregation p_aggregation, final IEnvironment<?> p_environment ) throws Exception
    {
        switch ( this )
        {
            case TRAFFICLIGHT_PEDESTRIAN:
                return new CTrafficLightPedestrianGenerator( p_stream, p_actions, p_aggregation, p_environment );

            case TRAFFICLIGHT_VEHICLE:
                return new CTrafficLightVehicleGenerator( p_stream, p_actions, p_aggregation, p_environment );

            default: throw new RuntimeException( MessageFormat.format( "no generator [{0}] found", this ) );
        }
    }


}
