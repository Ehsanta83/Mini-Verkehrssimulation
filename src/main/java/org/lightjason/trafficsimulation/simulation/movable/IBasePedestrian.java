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

package org.lightjason.trafficsimulation.simulation.movable;

import cern.colt.matrix.DoubleMatrix1D;
import org.dyn4j.geometry.Convex;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.trafficsimulation.simulation.environment.EDirection;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;

/**
 * pedestrian abstract class
 */
@IAgentAction
public abstract class IBasePedestrian<T extends IBasePedestrian<?>> extends IBaseMoveable<T>
{
    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment environment
     * @param p_functor functor
     * @param p_name name
     * @param p_position position
     * @param p_convex convex
     */
    protected IBasePedestrian( final IAgentConfiguration<T> p_configuration, final IEnvironment p_environment, final String p_functor,
                               final String p_name, final DoubleMatrix1D p_position, final Convex p_convex )
    {
        super( p_configuration, p_environment, p_functor, p_name, p_position, p_convex );
    }

    /**
     * move right to the goal direction
     */
    @IAgentActionFilter
    @IAgentActionName( name = "move/right" )
    protected final void moveright()
    {
        this.move( EDirection.RIGHT );
    }

    /**
     * move left to the goal
     */
    @IAgentActionFilter
    @IAgentActionName( name = "move/left" )
    protected final void moveleft()
    {
        this.move( EDirection.LEFT );
    }
}
