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

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.lightjason.trafficsimulation.IBaseViewTest;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;
import org.lightjason.trafficsimulation.simulation.algorithm.routing.ERoutingFactory;
import org.lightjason.trafficsimulation.ui.CAreaSprite;
import org.lightjason.trafficsimulation.ui.CPedestrianSprite;
import org.lightjason.trafficsimulation.ui.CVehicleSprite;

import java.util.stream.Stream;


/**
 * test area
 */
public final class TestCArea extends IBaseViewTest
{
    /**
     * initilize
     *
     * @throws Exception on initialize environment error
     */
    @Before
    public final void initialize() throws Exception
    {
        this.initializeenvironment( ENVWIDTH, ENVHEIGHT, ENVCELL, ERoutingFactory.JPSPLUS.get() );
        m_environment.call();
        m_vehicles.add(
            new CVehicleSprite(
                this.generate(
                    "src/test/resources/vehicle.asl",
                    EObjectFactory.VEHICLE,
                    new DenseDoubleMatrix1D( new double[]{1, 25} ),
                    2
                )
            )
        );
        m_environment.areas().forEach( ( p_name, p_area ) ->
        {
            m_areas.add( new CAreaSprite( p_area ) );
        }
        );
    }

    /**
     * test
     */
    @Test
    public final void test()
    {
        Assume.assumeNotNull( s_screen );
        Stream.concat(  m_areas.stream(), Stream.concat( m_pedestrians.stream(), m_vehicles.stream() ) ).forEach( s_screen::spriteadd );
        callagents( 100, 1000 );
    }

    /**
     * test call
     *
     * @param p_args command-line arguments
     */
    public static void main( final String[] p_args )
    {
        s_screen = screen( WINDOWWIDTH, WINDOWHEIGHT, ENVWIDTH, ENVHEIGHT, ENVCELL );
        new TestCArea().invoketest();
    }

}
