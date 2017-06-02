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
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.simulation.IBaseObject;
import org.lightjason.trafficsimulation.simulation.bounding.CCircleBoundingBox;
import org.lightjason.trafficsimulation.simulation.bounding.IBoundingBox;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;


/**
 * traffic light abstract class
 */
@IAgentAction
public abstract class IBaseTrafficLight<T extends IBaseTrafficLight<?, ?>, L extends Enum<?> & ITrafficLightColor<L>> extends IBaseObject<T> implements ITrafficLight<T>
{
    protected static final String GROUP = "trafficlight";
    /**
     * color of traffic light
     */
    private final AtomicReference<L> m_color;
    /**
     * rotation of the traffic light
     */
    private final int m_rotation;
    /**
     * radius of the object
     */
    private final double m_radius;


    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment environment reference
     * @param p_functor functor
     * @param p_light light class
     * @param p_position position
     * @param p_rotation rotation
     * @param p_radius radius
     */
    protected IBaseTrafficLight( final IAgentConfiguration<T> p_configuration, final IEnvironment p_environment,
                                 final String p_functor, final String p_name, final Class<L> p_light,
                                 final DoubleMatrix1D p_position, final int p_rotation, final double p_radius
    )
    {
        super( p_configuration, p_environment, p_functor, p_name, p_position, new CCircleBoundingBox( p_radius ) );
        m_rotation = p_rotation;
        m_color = new AtomicReference<L>( p_light.getEnumConstants()[0] );
        m_radius = p_radius;
    }

    @Override
    protected DoubleMatrix1D size()
    {
        return new DenseDoubleMatrix1D( new double[] {m_radius} );
    }


    /**
     * changes the color of the light
     */
    @IAgentActionFilter
    @IAgentActionName( name = "state/next" )
    private void nextstate()
    {
        m_color.set( m_color.get().next() );
        System.out.println( "---test---> " + m_color  );
    }


    /**
     * abstract generator for traffic lights
     *
     * @bug fix documentation
     */
    protected abstract static class IGenerator<T extends IBaseTrafficLight<?, ?>> extends IBaseGenerator<T>
    {


        /**
         * @param p_stream stream
         * @param p_actions action
         * @param p_aggregation aggregation
         * @param p_environment environment
         * @throws Exception on any error
         */
        public IGenerator( final InputStream p_stream, final Stream<IAction> p_actions,
                           final IAggregation p_aggregation, final Class<T> p_agentclass,
                           final IEnvironment p_environment
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, p_agentclass, p_environment );
        }

        @Override
        @SuppressWarnings( "unchecked" )
        protected final Pair<T, Stream<String>> generate( final Object... p_data )
        {
            return this.generate( m_environment, (DoubleMatrix1D) p_data[0],
                (int) p_data[1], (double) p_data[2] );
        }

        /**
         * generates the agent
         *
         * @param p_environment environment
         * @param p_position position
         * @param p_rotation rotation
         * @param p_radius radius
         * @return pair of IBaseTrafficLight and stream of strings,
         */
        protected abstract Pair<T, Stream<String>> generate( final IEnvironment p_environment, final DoubleMatrix1D p_position,
                                                             final int p_rotation, final double p_radius );

    }
}
