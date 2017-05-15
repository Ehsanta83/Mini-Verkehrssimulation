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

package org.lightjason.trafficsimulation.simulation.environment;

import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.common.CCommon;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.simulation.algorithm.routing.IRouting;
import org.lightjason.trafficsimulation.ui.CHTTPServer;

import java.io.InputStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * environment agent generator
 *
 * @bug fix docu
 */
public abstract class IBaseEnvironmentGenerator extends IBaseAgentGenerator<IEnvironment>
{
    public IBaseEnvironmentGenerator( final InputStream p_stream, final Stream<IAction> p_actions,
                                      final IAggregation p_aggregation, final Class<? extends IEnvironment> p_environmentclass
    ) throws Exception
    {
        super( p_stream, Stream.concat( p_actions, CCommon.actionsFromAgentClass( p_environmentclass ) ).collect( Collectors.toSet() ), p_aggregation );
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public final IEnvironment generatesingle( final Object... p_data )
    {
        return CHTTPServer.register(
                    this.generate(
                        m_configuration,
                        ( (Number) p_data[0] ).intValue(), ( (Number) p_data[1] ).intValue(), ( (Number) p_data[2] ).doubleValue(),
                        (IRouting) p_data[3]
                    )
        );
    }

    /**
     * generate
     *
     * @param p_configuration configuration
     * @param p_rows rows
     * @param p_columns columns
     * @param p_cellsize cellsize
     * @param p_routing routing
     * @return IEnvironment
     */
    protected abstract IEnvironment generate( final IAgentConfiguration<IEnvironment> p_configuration,
                                              final int p_rows, final int p_columns, final double p_cellsize,
                                              final IRouting p_routing
    );

}
