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
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.lightjason.trafficsimulation.IBaseViewTest;
import org.lightjason.trafficsimulation.ui.CPedestrianSprite;
import org.lightjason.trafficsimulation.ui.CVehicleSprite;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;
import org.lightjason.trafficsimulation.simulation.algorithm.routing.ERoutingFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * test of driving a vehicle
 */
public final class TestCViewMoveable extends IBaseViewTest
{
    /**
     * window screen width
     */
    private static final int WINDOWWIDTH = 1600;
    /**
     * window screen height
     */
    private static final int WINDOWHEIGHT = 1200;
    /**
     * grid width (column)
     */
    private static final int ENVWIDTH = 50;
    /**
     * grid height (rows)
     */
    private static final int ENVHEIGHT = 50;
    /**
     * cell size
     */
    private static final int ENVCELL = 10;
    /**
     * screen reference
     */
    private static CScreen s_screen;
    /**
     * pedestrians
     */
    private List<CPedestrianSprite> m_pedestrians = new ArrayList<>();

    /**
     * vehicles
     */
    private List<CVehicleSprite> m_vehicles = new ArrayList<>();

    /**
     * initialize pedestrian
     *
     * @throws Exception on initialize environment error
     * @bug fix collision box
     */
    @Before
    public final void initialize() throws Exception
    {
        this.initializeenvironment( ENVWIDTH, ENVHEIGHT, ENVCELL, ERoutingFactory.JPSPLUS.get() );
        IntStream.range( 0, 25 ).forEach( i ->
            m_pedestrians.add(
                new CPedestrianSprite(
                    this.generate(
                        "src/test/resources/pedestrian.asl",
                        EObjectFactory.PEDESTRIAN,
                        new DenseDoubleMatrix1D( new double[]{Math.round( Math.random() * 50 ), Math.round( Math.random() * 50 )} ),
                        0.5
                    )
                )
            )
        );
        IntStream.range( 0, 10 ).forEach( i ->
            m_vehicles.add(
                new CVehicleSprite(
                    this.generate(
                        "src/test/resources/vehicle.asl",
                        EObjectFactory.VEHICLE,
                        new DenseDoubleMatrix1D( new double[]{Math.round( Math.random() * 50 ), Math.round( Math.random() * 50 )} ),
                        2
                    )
                )
            )
        );

    }

    /**
     * show moving
     */
    @Test
    public final void showmoving()
    {
        Assume.assumeNotNull( s_screen );
        Stream.concat( m_pedestrians.stream(), m_vehicles.stream() ).forEach( s_screen::spriteadd );
        IntStream.rangeClosed( 0, 100 ).forEach(  i ->
        {
            Stream.concat( m_pedestrians.stream(), m_vehicles.stream() ).forEach( j ->
            {
                try
                {

                    j.call();
                    System.out.println( j.raw().position() );


                }
                catch ( final Exception l_exception )
                {
                    l_exception.printStackTrace();
                    Assert.assertTrue( false );
                }
            }
            );
            try
            {
                Thread.sleep( 500 );
            }
            catch ( final InterruptedException l_exception )
            {
                l_exception.printStackTrace();
            }
        }
        );


    }


    /**
     * test call
     *
     * @param p_args command-line arguments
     */
    public static void main( final String[] p_args )
    {
        s_screen = screen( WINDOWWIDTH, WINDOWHEIGHT, ENVWIDTH, ENVHEIGHT, ENVCELL );
        new TestCViewMoveable().invoketest();
    }


}
