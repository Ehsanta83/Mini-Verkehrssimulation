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
import org.lightjason.trafficsimulation.simulation.virtual.CArea;

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

    AREA,

    VEHICLE,
    VEHICLE_TRAFFICLIGHT,

    PEDESTRIAN,
    PEDESTRIAN_TRAFFICLIGHT;



    /**
     * generates a agent generator
     *
     * @param p_stream asl input stream
     * @param p_actions action list
     * @param p_aggregation aggregation
     * @param p_environment environment reference
     * @return agent generator
     *
     * @throws Exception on any error
     */
    public final IAgentGenerator<? extends IObject<?>> generate( final InputStream p_stream, final Stream<IAction> p_actions,
                                                                 final IAggregation p_aggregation, final IEnvironment p_environment,
                                                                 final Object... p_arguments
    ) throws Exception
    {
        switch ( this )
        {
            case ENVIRONMENT:
                return new CEnvironment.CGenerator( p_stream, p_actions, p_aggregation );

            case AREA:
                return new CArea.CGenerator( p_stream, p_actions, p_aggregation, p_environment, p_arguments );

            case PEDESTRIAN:
                return new CPedestrian.CGenerator( p_stream, p_actions, p_aggregation, p_environment, p_arguments );

            case PEDESTRIAN_TRAFFICLIGHT:
                return new CTrafficLightPedestrian.CGenerator( p_stream, p_actions, p_aggregation, p_environment, p_arguments );

            case VEHICLE:
                return new CVehicle.CGenerator( p_stream, p_actions, p_aggregation, p_environment, p_arguments );

            case VEHICLE_TRAFFICLIGHT:
                return new CTrafficLightVehicle.CGenerator( p_stream, p_actions, p_aggregation, p_environment, p_arguments );

            default:
                throw new RuntimeException( MessageFormat.format( "no generator [{0}] found", this ) );
        }
    }

    /**
     * generator
     *
     * @param p_stream steam
     * @param p_actions actions
     * @param p_aggregation aggregation
     * @return agent generator
     * @throws Exception on any error
     */
    public final IAgentGenerator<? extends IObject<?>> generate( final InputStream p_stream, final Stream<IAction> p_actions,
                                                                 final IAggregation p_aggregation ) throws Exception
    {
        return this.generate( p_stream, p_actions, p_aggregation, null );
    }


    /**
     * resets all generator counters
     */
    public static void resetcount()
    {
        CPedestrian.CGenerator.resetcount();
        CVehicle.CGenerator.resetcount();

        CTrafficLightPedestrian.CGenerator.resetcount();
        CTrafficLightVehicle.CGenerator.resetcount();
    }

}
