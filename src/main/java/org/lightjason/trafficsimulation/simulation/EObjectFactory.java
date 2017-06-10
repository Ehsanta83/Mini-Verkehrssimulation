/*
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason TrafficSimulation                              #
 * # Copyright (c) 2016-17, LightJason (info@lightjason.org)                            #
 * # This program is free software: you can redistribute it and/or modify               #
 * # it under the terms of the GNU Lesser General Public License as                     #
 * # published by the Free Software Foundation, either version 3 of the                 #
 * # License, or (at your option) any later version.                                    #
 * #                                                                                    #
 * # This program is distributed in the hope that it will be useful,                    #
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of                     #
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                      #
 * # GNU Lesser General Public License for more details.                                #
 * #                                                                                    #
 * # You should have received a copy of the GNU Lesser General Public License           #
 * # along with this program. If not, see http://www.gnu.org/licenses/                  #
 * ######################################################################################
 * @endcond
 */

package org.lightjason.trafficsimulation.simulation;

import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.generator.IAgentGenerator;
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
     * @param p_environment environment reference
     * @return agent generator
     *
     * @throws Exception on any error
     */
    public final IAgentGenerator<? extends IObject<?>> generate( final InputStream p_stream, final Stream<IAction> p_actions, final IEnvironment p_environment
    ) throws Exception
    {
        switch ( this )
        {
            case ENVIRONMENT:
                return new CEnvironment.CGenerator( p_stream, p_actions );

            case AREA:
                return new CArea.CGenerator( p_stream, p_actions, p_environment );

            case PEDESTRIAN:
                return new CPedestrian.CGenerator( p_stream, p_actions, p_environment );

            case PEDESTRIAN_TRAFFICLIGHT:
                return new CTrafficLightPedestrian.CGenerator( p_stream, p_actions, p_environment );

            case VEHICLE:
                return new CVehicle.CGenerator( p_stream, p_actions, p_environment );

            case VEHICLE_TRAFFICLIGHT:
                return new CTrafficLightVehicle.CGenerator( p_stream, p_actions, p_environment );

            default:
                throw new RuntimeException( MessageFormat.format( "no generator [{0}] found", this ) );
        }
    }

    /**
     * generator
     *
     * @param p_stream steam
     * @param p_actions actions
     * @return agent generator
     * @throws Exception on any error
     */
    public final IAgentGenerator<? extends IObject<?>> generate( final InputStream p_stream, final Stream<IAction> p_actions ) throws Exception
    {
        return this.generate( p_stream, p_actions, null );
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
