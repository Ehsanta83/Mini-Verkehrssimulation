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
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.dyn4j.geometry.Geometry;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.trafficsimulation.simulation.IObject;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;


/**
 * pedestrian class
 */
public final class CPedestrian extends IBasePedestrian<CPedestrian>
{
    /**
     * functor
     */
    private static final String FUNCTOR = "pedestrian";
    /**
     * counter
     */
    private static final AtomicLong COUNTER = new AtomicLong();
    /**
     * radius
     */
    private final double m_radius;

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment environment
     * @param p_name name
     * @param p_position position
     * @param p_radius radius
     */
    private CPedestrian(
        final IAgentConfiguration<CPedestrian> p_configuration,
        final IEnvironment p_environment,
        final String p_name,
        final DoubleMatrix1D p_position,
        final double p_radius
    )
    {
        super( p_configuration, p_environment, FUNCTOR, p_name, p_position, Geometry.createCircle( p_radius ) );
        m_radius = p_radius;
    }


    @Override
    protected final Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object )
    {
        return Stream.of();
    }

    @Override
    public void resizeconvex( int p_percent )
    {
        if ( m_convex.get().getRadius() * p_percent / 100 < m_radius )
            throw new RuntimeException( "The bounding box cannot be smaller than the object." );
        m_convex.set( Geometry.createCircle( m_convex.get().getRadius() * p_percent / 100 ) );
    }

    /**
     * generator class
     */
    public static final class CGenerator extends IGenerator<CPedestrian>
    {

        /**
         * ctor
         *
         * @param p_stream stream
         * @param p_actions actions
         * @param p_environment environment
         * @throws Exception on any error
         */
        public CGenerator( final InputStream p_stream, final Stream<IAction> p_actions, final IEnvironment p_environment ) throws Exception
        {
            super( p_stream, p_actions, CPedestrian.class, p_environment );
        }

        @Override
        @SuppressWarnings( "unchecked" )
        protected final Pair<CPedestrian, Stream<String>> generate( final Object... p_data )
        {
            final CPedestrian l_pedestrian = new CPedestrian(
                m_configuration,
                m_environment,
                MessageFormat.format( "{0} {1}", FUNCTOR, COUNTER.getAndIncrement() ),
                (DoubleMatrix1D) p_data[0],
                (double) p_data[1]
            );
            m_environment.addobject( l_pedestrian );
            return new ImmutablePair<>(
                l_pedestrian,
                Stream.of( FUNCTOR, GROUP )
            );
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
