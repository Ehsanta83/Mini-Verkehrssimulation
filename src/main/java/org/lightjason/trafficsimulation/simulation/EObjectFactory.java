package org.lightjason.trafficsimulation.simulation;

import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.generator.IAgentGenerator;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.simulation.environment.CEnvironment;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;
import org.lightjason.trafficsimulation.simulation.movable.CPedestrian;
import org.lightjason.trafficsimulation.simulation.movable.CVehicle;
import org.lightjason.trafficsimulation.simulation.stationary.trafficlight.CTrafficLightPedestrian;
import org.lightjason.trafficsimulation.simulation.stationary.trafficlight.CTrafficLightVehicle;
import org.lightjason.trafficsimulation.simulation.virtual.CIntersection;
import org.lightjason.trafficsimulation.simulation.virtual.CLane;
import org.lightjason.trafficsimulation.simulation.virtual.CSidewalk;
import org.lightjason.trafficsimulation.simulation.virtual.CVehiclesWay;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.stream.Stream;


/**
 * class for generating object-generators
 *
 * @todo fix documentation
 */
public enum EObjectFactory
{
    ENVIRONMENT,

    INTERSECTION,
    LANE,
    SIDEWALK,
    VEHICLEWAY,

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
    public final IAgentGenerator<? extends IObject<?>> generate( final InputStream p_stream, final Stream<IAction> p_actions,
                                                                 final IAggregation p_aggregation, final IEnvironment p_environment
    ) throws Exception
    {
        switch ( this )
        {
            case ENVIRONMENT:
                return new CEnvironment.CGenerator( p_stream, p_actions, p_aggregation );


            case INTERSECTION:
                return new CIntersection.CGenerator( p_stream, p_actions, p_aggregation, p_environment );

            case LANE:
                return new CLane.CGenerator( p_stream, p_actions, p_aggregation, p_environment );

            case SIDEWALK:
                return new CSidewalk.CGenerator( p_stream, p_actions, p_aggregation, p_environment );

            case VEHICLEWAY:
                return new CVehiclesWay.CGenerator( p_stream, p_actions, p_aggregation, p_environment );


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
    public final IAgentGenerator<? extends IObject<?>> generate( final InputStream p_stream, final Stream<IAction> p_actions,
                                                                 final IAggregation p_aggregation ) throws Exception
    {
        return this.generate( p_stream, p_actions, p_aggregation, null );
    }

}
