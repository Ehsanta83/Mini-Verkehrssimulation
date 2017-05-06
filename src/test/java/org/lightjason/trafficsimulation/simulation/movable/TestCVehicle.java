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
import org.lightjason.trafficsimulation.IBaseTest;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

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
        this.initializeenvironment();
        m_vehicle = this.generate( "src/test/resources/vehicle.asl", EObjectFactory.VEHICLE, new DenseDoubleMatrix1D( new double[]{0, 0} ) );
    }


    /**
     * pedestrian test
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
     * main method
     *
     * @param p_args args
     */
    public static void main( final String[] p_args )
    {
        new TestCVehicle().invoketest();
    }

}
