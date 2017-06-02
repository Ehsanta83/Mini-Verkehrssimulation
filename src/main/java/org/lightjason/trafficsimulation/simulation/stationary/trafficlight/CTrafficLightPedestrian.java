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

package org.lightjason.trafficsimulation.simulation.stationary.trafficlight;

import cern.colt.matrix.DoubleMatrix1D;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.simulation.IObject;
import org.lightjason.trafficsimulation.simulation.bounding.IBoundingBox;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;


/**
 * pedestrian traffic light class
 */
public final class CTrafficLightPedestrian extends IBaseTrafficLight<CTrafficLightPedestrian, ELightColorPedestrian>
{
    private static final String FUNCTOR = "pedestrianlight";
    private static final AtomicLong COUNTER = new AtomicLong();

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment environment reference
     * @param p_name name
     * @param p_position position
     * @param p_rotation rotation
     * @param p_radius radius
     */
    private CTrafficLightPedestrian(
        final IAgentConfiguration<CTrafficLightPedestrian> p_configuration,
        final IEnvironment p_environment,
        final String p_name,
        final DoubleMatrix1D p_position,
        final int p_rotation,
        final double p_radius
    )
    {
        super( p_configuration, p_environment, FUNCTOR, p_name, ELightColorPedestrian.class, p_position, p_rotation, p_radius );
    }


    @Override
    protected final Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object )
    {
        return Stream.of();
    }


    /**
     * generator of pedestrian traffic lights
     */
    public static final class CGenerator extends IGenerator<CTrafficLightPedestrian>
    {

        public CGenerator( final InputStream p_stream, final Stream<IAction> p_actions,
                           final IAggregation p_aggregation,
                           final IEnvironment p_environment,
                           final Object... p_arguments
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, CTrafficLightPedestrian.class, p_environment );
        }

        @Override
        protected final Pair<CTrafficLightPedestrian, Stream<String>> generate( final IEnvironment p_environment, final DoubleMatrix1D p_position,
                                                                                final int p_rotation, final double p_radius )
        {
            return new ImmutablePair<>(
                                        new CTrafficLightPedestrian(
                                                                     m_configuration,
                                                                     p_environment,
                                                                     MessageFormat.format( "{0} {1}", FUNCTOR, COUNTER.getAndIncrement() ),
                                                                     p_position,
                                                                     p_rotation,
                                                                     p_radius
                                        ),

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
