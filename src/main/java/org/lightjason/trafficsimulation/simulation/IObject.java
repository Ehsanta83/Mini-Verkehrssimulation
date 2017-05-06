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

import cern.colt.matrix.DoubleMatrix1D;
import org.lightjason.agentspeak.agent.IAgent;
import org.lightjason.agentspeak.generator.IAgentGenerator;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.stream.Stream;


/**
 * object interface
 */
public interface IObject<T extends IAgent<?>> extends IAgent<T>
{

    /**
     * get literal of the object
     *
     * @param p_object objects
     * @return stream of literal
     */
    Stream<ILiteral> literal( final IObject<?>... p_object );

    /**
     * get literal of the object
     *
     * @param p_object objects
     * @return stream of literal
     */
    Stream<ILiteral> literal( final Stream<IObject<?>> p_object );

    /**
     * name of the object
     *
     * @return string name
     */
    String name();

    /**
     * position of the object
     * @return position
     */
    DoubleMatrix1D position();

    /**
     * generator interface
     *
     * @tparam T element generator
     */
    interface IGenerator<T extends IObject<?>> extends IAgentGenerator<T>
    {

    }

}
