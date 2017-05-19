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
import cern.colt.matrix.ObjectMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.CCommon;
import org.lightjason.trafficsimulation.simulation.IBaseObject;
import org.lightjason.trafficsimulation.simulation.environment.EDirection;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;

import java.io.InputStream;
import java.util.stream.Stream;



/**
 * moveable object
 *
 * @todo implement moving
 * @todo can we create a main generate method for vehicles and pedestrian?
 */
@IAgentAction
public abstract class IBaseMoveable<T extends IBaseMoveable<?>> extends IBaseObject<T> implements IMoveable<T>
{
    protected static final String GROUP = "moveable";
    /**
     * radius of the object for the circle bounding box
     */
    protected final double m_radius;

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     */
    protected IBaseMoveable( final IAgentConfiguration<T> p_configuration, final IEnvironment p_environment,
                             final String p_functor, final String p_name, final DoubleMatrix1D p_position,
                             final double p_radius )
    {
        super( p_configuration, p_environment, p_functor, p_name, p_position );
        m_radius = p_radius;
    }

    @Override
    public final double radius()
    {
        return m_radius;
    }

    /**
     * get a stream of cells from position
     * @param p_position position
     * @return cells
     * @todo can it be optimized with refactoring inttuple with automatic cast?
     */
    public static Stream<Pair<Integer, Integer>> cells( final IMoveable<?> p_moveable, final DoubleMatrix1D p_position )
    {
        // the middle of the cell is calculated with +0.5
        return CCommon.inttupelstream(
            (int) ( p_position.get( 0 ) + 0.5 - p_moveable.radius() ),
            (int) ( p_position.get( 0 ) + 0.5 + p_moveable.radius() ),
            (int) ( p_position.get( 1 ) + 0.5 - p_moveable.radius() ),
            (int) ( p_position.get( 1 ) + 0.5 + p_moveable.radius() )
        );
    }

    /**
     * move forward into new position
     * @todo a better way to prevent casting?
     */
    @IAgentActionFilter
    @IAgentActionName( name = "move/forward" )
    protected final void moveforward( final Number p_xposition, final Number p_yposition, final Number p_speed )
    {
        this.move( EDirection.FORWARD,
            new DenseDoubleMatrix1D( new double[]{p_xposition.doubleValue(), p_yposition.doubleValue()} ),
            p_speed.intValue() );
    }

    /**
     * helper method for moving
     *
     * @param p_direction direction
     */
    private void move( final EDirection p_direction, final DoubleMatrix1D p_newposition, final int p_speed )
    {
        if ( p_newposition.equals( m_position ) )
            return;
        m_environment.move( this, p_direction.position( m_position, p_newposition, p_speed ), p_direction );
    }


    /**
     * generator
     * @param <T> IMoveable
     */
    protected abstract static class IGenerator<T extends IMoveable<?>> extends IBaseGenerator<T>
    {


        /**
         * @param p_stream stream
         * @param p_actions action
         * @param p_aggregation aggregation
         * @param p_environment environment
         * @throws Exception on any error
         */
        protected IGenerator( final InputStream p_stream, final Stream<IAction> p_actions,
                              final IAggregation p_aggregation, final Class<T> p_agentclass,
                              final IEnvironment p_environment
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, p_agentclass, p_environment );
        }

    }
}
