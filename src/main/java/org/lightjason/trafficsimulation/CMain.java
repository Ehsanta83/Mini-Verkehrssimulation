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

package org.lightjason.trafficsimulation;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.agent.IAgent;
import org.lightjason.trafficsimulation.actions.CBroadcast;
import org.lightjason.trafficsimulation.actions.CSend;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;
import org.lightjason.trafficsimulation.simulation.algorithm.routing.CJPSPlus;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;
import org.lightjason.trafficsimulation.ui.CHTTPServer;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.LogManager;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * main desktop application
 *
 * @bug clean-up
 */
public final class CMain
{
    static
    {
        LogManager.getLogManager().reset();
    }


    /**
     * constructor
     */
    private CMain()
    {
    }

    /**
     * main method
     *
     * @param p_args arguments
     * @throws Exception on URI syntax error
     */
    public static void main( final String[] p_args ) throws Exception
    {
        // --- define CLI options ------------------------------------------------------------------------------------------------------------------------------

        final Options l_clioptions = new Options();
        l_clioptions.addOption( "help", false, "shows this information" );
        l_clioptions.addOption( "generateconfig", false, "generate default configuration" );
        l_clioptions.addOption( "config", true, "path to configuration directory (default: <user home>/.asimov/configuration.yaml)" );

        final CommandLine l_cli;
        try
        {
            l_cli = new DefaultParser().parse( l_clioptions, p_args );
        }
        catch ( final Exception l_exception )
        {
            System.err.println( "command-line arguments parsing error" );
            System.exit( -1 );
            return;
        }



        // --- process CLI arguments and initialize configuration ----------------------------------------------------------------------------------------------

        if ( l_cli.hasOption( "help" ) )
        {
            new HelpFormatter().printHelp( new java.io.File( CMain.class.getProtectionDomain().getCodeSource().getLocation().getPath() ).getName(), l_clioptions );
            return;
        }

        if ( l_cli.hasOption( "generateconfig" ) )
        {
            System.out.println( MessageFormat.format( "default configuration was created under [{0}]", CConfiguration.createdefault() ) );
            return;
        }

        // load configuration and start the http server (if possible)
        CConfiguration.INSTANCE.loadfile( l_cli.getOptionValue( "config", "" ) );

        // initialize server
        CHTTPServer.initialize();

        // --- add test agents -----------------------------------------------------------
        final Map<String, IAgent<?>> l_agents = new ConcurrentHashMap<>();

        final Set<IAction> l_actions = Stream.concat(
            Stream.of(
                new CSend( l_agents ),
                new CBroadcast( l_agents )
            ),
            org.lightjason.agentspeak.common.CCommon.actionsFromPackage()
        ).collect( Collectors.toSet() );


        final IEnvironment l_env = EObjectFactory.ENVIRONMENT.generate(
            CMain.class.getResourceAsStream( "asl/environment.asl" ),
            l_actions.stream()
        ).generatesingle( 25, 25, 2.5, new CJPSPlus() ).<IEnvironment>raw();

        l_env.beliefbase().stream().forEach( System.out::println );
        l_env.call().call();


        EObjectFactory.PEDESTRIAN.generate(

            CMain.class.getResourceAsStream( "asl/pedestrian.asl" ),

            l_actions.stream(),

            l_env

        ).generatemultiple( 3, new DenseDoubleMatrix1D( 2 ) ).forEach( i ->
        {
        } );
        // -------------------------------------------------------------------------------


        // execute server
        CHTTPServer.execute();
    }


}
