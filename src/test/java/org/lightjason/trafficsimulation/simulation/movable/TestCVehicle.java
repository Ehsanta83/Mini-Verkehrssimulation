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

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.lightjason.trafficsimulation.IBaseTest;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;
import org.lightjason.trafficsimulation.simulation.algorithm.routing.ERoutingFactory;

import java.util.stream.Collectors;


/**
 * test vehicle
 *
 * @todo create test cases
 */
public final class TestCVehicle extends IBaseTest
{

    private CVehicle m_vehicle;

    /**
     * initialize vehicle
     *
     * @throws Exception on any error
     */
    @Before
    public final void initialize() throws Exception
    {
        this.initializeenvironment( 150, 150, 50, ERoutingFactory.JPSPLUS.get() );
        m_vehicle = this.generate( "src/test/resources/vehicle.asl", EObjectFactory.VEHICLE, new DenseDoubleMatrix1D( new double[]{35, 1, 1, 2} ) );
    }


    /**
     * vehicle test
     */
    @Test
    public final void test()
    {
        Assume.assumeNotNull( m_vehicle );

        System.out.println(
            executeagent( m_vehicle ).literal().collect( Collectors.toSet() )
        );
    }

    /**
     * test moving a vehicle
     */
    @Test
    public final void testMoving()
    {
        System.out.println( m_vehicle.position() );
        m_environment.move( m_vehicle, new DenseDoubleMatrix1D( new double[]{35, 2, 1, 2} ) );
        System.out.println( m_vehicle.position() );
    }

    /**
     * main method
     *
     * @param p_args args
     */
    public static void main( final String[] p_args )
    {
        new TestCVehicle().invoketest();
    }

}
