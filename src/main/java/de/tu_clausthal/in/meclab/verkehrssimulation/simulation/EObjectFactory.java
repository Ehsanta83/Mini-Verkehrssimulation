package de.tu_clausthal.in.meclab.verkehrssimulation.simulation;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.CEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.CPedestrian;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.CVehicle;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stationary.trafficlight.CTrafficLightPedestrian;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stationary.trafficlight.CTrafficLightVehicle;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.generator.IAgentGenerator;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Set;


/**
 * class for generating object-generators
 *
 * @todo fix documentation
 */
public enum EObjectFactory
{
    ENVIRONMENT,
    VEHICLE,
    VEHICLE_TRAFFICLIGHT,
    PEDESTRIAN,
    PEDESTRIAN_TRAFFICLIGHT;

    /**
     * generates a agent generator
     *
     * @param p_stream asl input stream
     * @param p_actions action list
     * @param p_aggregation
     * @param p_environment environment reference
     * @return agent generator
     *
     * @throws Exception on any error
     */
    public final IAgentGenerator<? extends IObject<?>> generate( final InputStream p_stream, final Set<IAction> p_actions,
                                                                 final IAggregation p_aggregation, final IEnvironment p_environment
    ) throws Exception
    {
        switch ( this )
        {
            case ENVIRONMENT:
                return new CEnvironment.CGenerator( p_stream, p_actions, p_aggregation );


            case PEDESTRIAN:
                return new CPedestrian.CGenerator( p_stream, p_actions, p_aggregation, p_environment );

            case PEDESTRIAN_TRAFFICLIGHT:
                return new CTrafficLightPedestrian.CGenerator( p_stream, p_actions, p_aggregation, p_environment );


            case VEHICLE:
                return new CVehicle.CGenerator( p_stream, p_actions, p_aggregation, p_environment );

            case VEHICLE_TRAFFICLIGHT:
                return new CTrafficLightVehicle.CGenerator( p_stream, p_actions, p_aggregation, p_environment );


            default:
                throw new RuntimeException( MessageFormat.format( "no generator [{0}] found", this ) );
        }
    }

    /**
     *
     * @param p_stream
     * @param p_actions
     * @param p_aggregation
     * @return
     * @throws Exception on any error
     */
    public final IAgentGenerator<? extends IObject<?>> generate( final InputStream p_stream, final Set<IAction> p_actions,
                                                                 final IAggregation p_aggregation ) throws Exception
    {
        return this.generate( p_stream, p_actions, p_aggregation, null );
    }

}
