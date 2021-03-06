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

package org.lightjason.trafficsimulation.runtime;

import org.lightjason.agentspeak.agent.IAgent;

import java.util.stream.Stream;


/**
 * a single experiment run
 */
public interface IExperiment
{

    /**
     * returns a stream of the agents
     *
     * @return agent stream
     */
    Stream<IAgent<?>> agents();

    /**
     * maximum simulation steps
     *
     * @return simulation steps
     */
    int simulationsteps();

    /**
     * returns tha simulation statistic
     *
     * @return statistic
     */
    IStatistic statistic();

    /**
     * execute agents in parallel
     *
     * @return boolean flag for parallel execution
     */
    boolean parallel();

}
