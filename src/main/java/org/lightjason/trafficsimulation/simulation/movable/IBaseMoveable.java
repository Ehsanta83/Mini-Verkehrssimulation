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
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.CCommon;
import org.lightjason.trafficsimulation.simulation.IBaseObject;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;

import java.io.InputStream;
import java.util.stream.Stream;



/**
 * moveable object
 *
 * @todo implement moving
 * @todo can we create a main generate method for vehicles and pedestrian?
 */
public abstract class IBaseMoveable<T extends IBaseMoveable<?>> extends IBaseObject<T> implements IMoveable<T>
{
    protected static final String GROUP = "moveable";

    protected final DoubleMatrix1D m_size;

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     */
    protected IBaseMoveable( final IAgentConfiguration<T> p_configuration, final IEnvironment p_environment,
                             final String p_functor, final String p_name, final DoubleMatrix1D p_position,
                             final DoubleMatrix1D p_size )
    {
        super( p_configuration, p_environment, p_functor, p_name, p_position );
        m_size = p_size;
    }

    /**
     * get a stream of cells from position
     * @param p_position position
     * @return cells
     * @todo can it be optimized with refactoring inttuple with automatic cast?
     * @todo should be static?
     */
    private Stream<Pair<Integer, Integer>> cells( final DoubleMatrix1D p_position )
    {
        return CCommon.inttupelstream(
            (int) ( p_position.get( 0 ) - p_position.get( 2 ) / 2 ),
            (int) ( p_position.get( 0 ) + p_position.get( 2 ) / 2 ),
            (int) ( p_position.get( 1 ) - p_position.get( 3 ) / 2 ),
            (int) ( p_position.get( 1 ) + p_position.get( 3 ) / 2 )
        );
    }

    /**
     * moveable
     * @todo should be static ?
     */
    @Override
    public boolean moveable( final ObjectMatrix2D p_grid, final DoubleMatrix1D p_newposition )
    {
        return this.cells( p_newposition )
            .noneMatch( i -> ( p_grid.getQuick( i.getLeft(), i.getRight() ) != null ) && ( p_grid.getQuick( i.getLeft(), i.getRight() ) != this )
        );
    }

    /**
     * move
     * @todo should be static ?
     */
    @Override
    public void move( final ObjectMatrix2D p_grid, final DoubleMatrix1D p_newposition )
    {
        this.cells( this.position() ).forEach( i -> p_grid.setQuick( i.getLeft(), i.getRight(), null ) );
        this.cells( p_newposition ).forEach( i -> p_grid.setQuick( i.getLeft(), i.getRight(), this ) );
        this.position().setQuick( 0, p_newposition.getQuick( 0 ) );
        this.position().setQuick( 1, p_newposition.getQuick( 1 ) );
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
