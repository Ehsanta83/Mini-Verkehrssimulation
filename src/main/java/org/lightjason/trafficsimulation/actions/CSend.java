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

package org.lightjason.trafficsimulation.actions;

import org.lightjason.agentspeak.action.IBaseAction;
import org.lightjason.agentspeak.agent.IAgent;
import org.lightjason.agentspeak.common.CPath;
import org.lightjason.agentspeak.common.IPath;
import org.lightjason.agentspeak.language.CCommon;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ITerm;
import org.lightjason.agentspeak.language.execution.IContext;
import org.lightjason.agentspeak.language.execution.fuzzy.CFuzzyValue;
import org.lightjason.agentspeak.language.execution.fuzzy.IFuzzyValue;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.CTrigger;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.ITrigger;
import org.lightjason.trafficsimulation.simulation.IObject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * send action
 */
public final class CSend extends IBaseAction
{
    /**
     * map with agent names and agent objects
     */
    private final Map<String, IAgent<?>> m_agents;

    /**
     * constructor
     *
     * @param p_agents map with agent names and objects
     */
    public CSend( final Map<String, IAgent<?>> p_agents )
    {
        m_agents = p_agents;
    }

    @Override
    public final IPath name()
    {
        return CPath.from( "message/send" );
    }

    @Override
    public final int minimalArgumentNumber()
    {
        return 1;
    }

    @Override
    public final IFuzzyValue<Boolean> execute( final IContext p_context, final boolean p_parallel, final List<ITerm> p_argument, final List<ITerm> p_return )
    {
        final List<ITerm> l_arguments = CCommon.flatcollection( p_argument ).collect( Collectors.toList() );
        if ( l_arguments.size() < 2 )
            return CFuzzyValue.from( false );

        final IAgent<?> l_receiver = m_agents.get( l_arguments.get( 0 ).<String>raw() );
        if ( l_receiver == null )
            return CFuzzyValue.from( false );

        final ITerm l_sender = CLiteral.from( "from", CRawTerm.from( p_context.agent().<IObject<?>>raw().name() ) );
        l_arguments.stream()
                   .skip( 1 )
                   .map( ITerm::raw )
                   .map( CRawTerm::from )
                   .map( i -> CTrigger.from(
                       ITrigger.EType.ADDGOAL,
                       CLiteral.from( "message/receive", CLiteral.from( "message", i ), l_sender )
                   ) )
                   .forEach( i -> l_receiver.trigger( i ) );

        return CFuzzyValue.from( true );
    }
}
