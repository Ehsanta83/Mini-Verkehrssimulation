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

import java.util.concurrent.Callable;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * runtime class for execution an experiment
 */
public final class CRuntime
{
    /**
     * singleton instance
     */
    public static final CRuntime INSTANCE = new CRuntime();

    /**
     * private ctor
     */
    private CRuntime()
    {
    }


    /**
     * execute an experiment
     *
     * @param p_experiment experiment
     * @return experiment
     * @todo execution should be terminate by an agent call, not only on simulaiton steps
     */
    public final IExperiment execute( final IExperiment p_experiment )
    {
        IntStream.range( 0, p_experiment.simulationsteps() )
            .forEach( i ->
            {
                parallel( p_experiment.agents(), p_experiment.parallel() ).forEach( CRuntime::executewithlogging );
            } );

        return p_experiment;
    }


    /**
     * executes a stream in parallel
     *
     * @param p_stream object stream
     * @param p_parallel parallel execution
     * @return stream
     */
    private static <T> Stream<T> parallel( final Stream<T> p_stream, final boolean p_parallel )
    {
        return p_parallel ? p_stream.parallel() : p_stream;
    }

    /**
     * executs a callable object and logs the errors
     *
     * @param p_callable callable object
     * @bug log is missing
     */
    private static void executewithlogging( final Callable<?> p_callable )
    {
        try
        {
            p_callable.call();
        }
        catch ( final Exception l_exception )
        {
            l_exception.printStackTrace();
        }
    }

}
