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
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.CCommon;
import org.lightjason.trafficsimulation.simulation.IBaseObject;
import org.lightjason.trafficsimulation.simulation.environment.EDirection;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;
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
     * ctor
     *
     * @param p_configuration agent configuration
     */
    protected IBaseMoveable( final IAgentConfiguration<T> p_configuration, final IEnvironment p_environment,
                             final String p_functor, final String p_name, final DoubleMatrix1D p_position,
                             final DoubleMatrix1D p_size )
    {
        super( p_configuration, p_environment, p_functor, p_name, p_position );
    }

    /**
     * get a stream of cells from position
     * @param p_position position
     * @return cells
     * @todo can it be optimized with refactoring inttuple with automatic cast?
     */
    private static Stream<Pair<Integer, Integer>> cells( final DoubleMatrix1D p_position )
    {
        return CCommon.inttupelstream(
            (int) ( p_position.get( 0 ) - p_position.get( 2 ) ),
            (int) ( p_position.get( 0 ) + p_position.get( 2 ) ),
            (int) ( p_position.get( 1 ) - p_position.get( 2 ) ),
            (int) ( p_position.get( 1 ) + p_position.get( 2 ) )
        );
    }

    /**
     * moveable
     * @todo should be static ?
     */
    @Override
    public boolean moveable( final ObjectMatrix2D p_grid, final DoubleMatrix1D p_newposition )
    {
        return cells( p_newposition )
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
        cells( this.position() ).forEach( i -> p_grid.setQuick( i.getLeft(), i.getRight(), null ) );
        cells( p_newposition ).forEach( i -> p_grid.setQuick( i.getLeft(), i.getRight(), this ) );
        this.position().setQuick( 0, p_newposition.getQuick( 0 ) );
        this.position().setQuick( 1, p_newposition.getQuick( 1 ) );
    }

    /**
     * move forward into new position
     */
    @IAgentActionFilter
    @IAgentActionName( name = "move/forward" )
    protected final void moveforward( final Object... p_data )
    {
        //final List<CRawTerm<?>> p_newposition, final int p_speed
        System.out.println( "Hiiiiiiiiiiii" );
        this.move( EDirection.FORWARD, new DenseDoubleMatrix1D( ( (List<CRawTerm<?>>) p_data[0] ).stream().mapToDouble( i -> Double.valueOf( i.toString() ) ).toArray() ), (int) p_data[1] );
        System.out.println( "Hiiiiiiiiiiii" );
    }

    /**
     * helper method for moving
     *
     * @param p_direction direction
     */
    private void move( final EDirection p_direction, final DoubleMatrix1D p_newposition, final int p_speed )
    {
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
