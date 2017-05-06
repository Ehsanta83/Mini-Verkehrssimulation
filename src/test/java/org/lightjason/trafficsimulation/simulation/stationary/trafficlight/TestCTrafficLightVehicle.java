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

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.lightjason.trafficsimulation.IBaseTest;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;

import java.util.stream.Collectors;


/**
 * test vehicles traffic light
 */
public final class TestCTrafficLightVehicle extends IBaseTest
{

    private CTrafficLightVehicle m_vehiclelight;

    /**
     * initialize vehicle light
     */
    @Before
    public final void initialize()
    {
        m_vehiclelight = this.generate( "src/test/resources/vehiclelight.asl", EObjectFactory.VEHICLE_TRAFFICLIGHT, new DenseDoubleMatrix1D( new double[]{0, 0} ), 180 );
    }


    /**
     * pedestrian test
     *
     * @throws Exception on execution error
     */
    @Test
    public final void test() throws Exception
    {
        Assume.assumeNotNull( m_vehiclelight );

        System.out.println( m_vehiclelight.literal().collect( Collectors.toSet() ) );
        m_vehiclelight.call();
        System.out.println( m_vehiclelight.literal().collect( Collectors.toSet() ) );
    }

    /**
     * main method
     * @param p_args args
     *
     * @throws Exception on any error
     */
    public static void main( final String[] p_args ) throws Exception
    {
        new TestCTrafficLightVehicle().invoketest();
    }


}
