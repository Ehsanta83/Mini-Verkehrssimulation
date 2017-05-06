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

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.lightjason.trafficsimulation.IBaseTest;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;
import org.lightjason.trafficsimulation.simulation.movable.CPedestrian;


/**
 * test environment
 *
 * @todo create test cases
 */
public final class TestCEnvironment extends IBaseTest
{
    /**
     * pedestrian
     */
    private CPedestrian m_pedestrian;

    /**
     * initialize
     *
     * @throws Exception on any error
     */
    @Before
    public final void initilize() throws Exception
    {
        this.generate( "src/test/resources/pedestrian.asl", EObjectFactory.PEDESTRIAN );
    }

    /**
     * environment test
     *
     * @throws Exception on execution error
     */
    @Test
    @Ignore
    public final void testEnvironmentCall() throws Exception
    {
        m_environment.call();
    }

    /**
     * test moving pedestrian
     */
    @Test
    @Ignore
    public final void testMovingPedestrian()
    {
        m_environment.move( m_pedestrian, new DenseDoubleMatrix1D( new double[]{1, 2, 1, 2} ) );
    }

    /**
     * test call
     *
     * @param p_args command-line arguments
     */
    public static void main( final String[] p_args )
    {
        new TestCEnvironment().invoketest();
    }



}
