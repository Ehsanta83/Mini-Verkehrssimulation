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

package org.lightjason.trafficsimulation.simulation.virtual;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.dyn4j.geometry.Geometry;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.CTrigger;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.ITrigger;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.simulation.IBaseObject;
import org.lightjason.trafficsimulation.simulation.IObject;
import org.lightjason.trafficsimulation.simulation.environment.EDirection;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;
import org.lightjason.trafficsimulation.simulation.movable.IMoveable;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * area class
 * @todo change EDirection
 */
public final class CArea extends IBaseObject<CArea> implements IVirtual<CArea>
{
    /**
     * functor
     */
    private static final String FUNCTOR = "area";
    /**
     * counter
     */
    private static final AtomicLong COUNTER = new AtomicLong();
    /**
     * length of the area
     */
    private final int m_length;
    /**
     * width of the area
     */
    private final int m_width;
    /**
     * if the area is passable
     */
    private final boolean m_passable;
    /**
     * the type of area
     */
    private final String m_type;
    /**
     * in which direction one can move in this area
     */
    private final Stream<EDirection> m_directions;
    /**
     * a set of the physical agents in the area
     */
    private final Set<IBaseObject<?>> m_physical = new HashSet<>();

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment   environment reference
     * @param p_name          name of the object
     * @param p_position mid point of the area
     * @param p_length length of the area
     * @param p_width width of the area
     * @param p_passable passable
     * @param p_type type
     * @param p_directions directions
     * @todo did I calculate the bounding box right?
     */
    private CArea( final IAgentConfiguration<CArea> p_configuration,
                   final IEnvironment p_environment,
                   final String p_name,
                   final DoubleMatrix1D p_position,
                   final int p_length,
                   final int p_width,
                   final boolean p_passable,
                   final String p_type,
                   final List<CRawTerm<?>> p_directions )
    {
        super( p_configuration, p_environment, FUNCTOR, p_name, p_position, Geometry.createRectangle( p_length, p_width ) );
        m_length = p_length;
        m_width = p_width;
        m_passable = p_passable;
        m_type = p_type;
        m_directions = p_directions.stream().map( i -> EDirection.from( i.toString() ) );
    }

    @Override
    protected Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object )
    {
        return Stream.of(
            CLiteral.from( "passable", CRawTerm.from( m_passable ) ),
            CLiteral.from( "type", CRawTerm.from( m_type.toString().toLowerCase() ) ),
            CLiteral.from( "directions",  m_directions.map( i -> CRawTerm.from( i.toString().toLowerCase() ) ) )
        );
    }

    /**
     * check if a position is inside the area
     *
     * @param p_position position
     * @return boolean
     */
    public boolean isInside( final DoubleMatrix1D p_position )
    {
        return ( Math.abs( m_position.get( 0 ) - p_position.get( 0 ) + p_position.get( 2 ) / 2 ) < m_position.get( 2 ) )
            && ( Math.abs( m_position.get( 1 ) - p_position.get( 1 ) + p_position.get( 3 ) / 2 ) < m_position.get( 3 ) );
    }

    /**
     * add a physical agent to the area
     *
     * @param p_physical physical agent
     */
    public void addPhysical( final IBaseObject<?> p_physical )
    {
        if ( this.isInside( p_physical.position() ) )
        {
            m_physical.add( p_physical );
            this.trigger( CTrigger.from( ITrigger.EType.ADDGOAL, CLiteral.from( "addphysical", CRawTerm.from( p_physical ) ) ) );
        }
    }

    @Override
    public CArea call() throws Exception
    {
        m_physical.removeAll(
            m_physical.stream()
                .filter( i -> !this.isInside( i.position() ) )
                .peek( i -> this.trigger( CTrigger.from( ITrigger.EType.ADDGOAL, CLiteral.from( "removephysical", CRawTerm.from( i ) ) ) ) )
                .collect( Collectors.toSet() )
        );
        return super.call();
    }

    /**
     * get area type
     * @return type
     */
    public String type()
    {
        return m_type;
    }

    /**
     * generator class
     *
     * @bug environment position setter must be refactored
     */
    public static final class CGenerator extends IBaseGenerator<CArea>
    {
        /**
         * ctor
         *
         * @param p_stream stream
         * @param p_actions actions
         * @param p_aggregation aggregation
         * @param p_environment environment
         * @throws Exception on any error
         */
        public CGenerator( final InputStream p_stream,
                           final Stream<IAction> p_actions,
                           final IAggregation p_aggregation,
                           final IEnvironment p_environment
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, CArea.class, p_environment );
        }

        @Override
        @SuppressWarnings( "unchecked" )
        protected final Pair<CArea, Stream<String>> generate( final Object... p_data ) throws RuntimeException
        {
            final CArea l_area = new CArea(
                m_configuration,
                m_environment,
                MessageFormat.format( "{0} {1}", FUNCTOR, COUNTER.getAndIncrement() ),
                new DenseDoubleMatrix1D( ( (List<CRawTerm<?>>) p_data[0] ).stream().mapToDouble( i -> Double.valueOf( i.toString() ) ).toArray() ),
                (int) p_data[1],
                (int) p_data[2],
                (boolean) p_data[3],
                (String) p_data[4],
                (List<CRawTerm<?>>) p_data[5]
            );

            //m_environment.positioningAnArea( l_area );

            return new ImmutablePair<>( l_area, Stream.of( FUNCTOR ) );
        }

        /**
         * reset the object counter
         */
        public static void resetcount()
        {
            COUNTER.set( 0 );
        }
    }
}
