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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.lightjason.trafficsimulation.IBaseViewTest;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;
import org.lightjason.trafficsimulation.simulation.algorithm.routing.ERoutingFactory;

import java.util.concurrent.atomic.AtomicReference;


/**
 * test of driving a vehicle
 */
public final class TestCMoveableArea extends IBaseViewTest
{
    /**
     * screen reference
     */
    private static CScreen s_screen;
    /**
     * area generator
     */
    private ISprite<CPedestrian> m_pedestrian;

    /**
     * initialize pedestrian
     *
     * @throws Exception on initialize environment error
     */
    @Before
    public final void initialize() throws Exception
    {
        this.initializeenvironment( 250, 250, 50, ERoutingFactory.JPSPLUS.get() );
        m_pedestrian = new CPedestrianSprite(
                          this.generate(
                              "src/test/resources/pedestrian.asl",
                              EObjectFactory.PEDESTRIAN,
                              new DenseDoubleMatrix1D( new double[]{0, 0} )
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
        s_screen.spriteadd( m_pedestrian );
    }


    /**
     * test call
     *
     * @param p_args command-line arguments
     */
    public static void main( final String[] p_args )
    {
        s_screen = screen( 1600, 1200 );
        new TestCMoveableArea().invoketest();
    }


    /**
     * pedestrian sprite
     */
    private static final class CPedestrianSprite extends IBaseSprite<CPedestrian>
    {
        /**
         *
         */
        private static final AtomicReference<Texture> TEXTURE = new AtomicReference<>();

        /**
         * ctor
         *
         * @param p_wrapping wrapping object
         */
        CPedestrianSprite( final CPedestrian p_wrapping )
        {
            super( p_wrapping );
        }

        @Override
        public final ISprite<CPedestrian> call() throws Exception
        {
            m_wrapping.call();
            m_sprite.setCenter( (int) m_wrapping.position().get( 0 ), (int) m_wrapping.position().get( 1 ) );
            return this;
        }

        /**
         * creates the texture
         */
        private static void texture( final int p_cellsize )
        {
            final Pixmap l_pixmap = new Pixmap( p_cellsize, p_cellsize, Pixmap.Format.RGBA8888 );
            l_pixmap.setColor( Color.RED );
            l_pixmap.fillCircle( p_cellsize / 2, p_cellsize / 2, (int) ( 0.45 * p_cellsize ) );

            TEXTURE.compareAndSet( null, new Texture( l_pixmap ) );
        }

        @Override
        public ISprite<CPedestrian> spriteinitialize( final int p_rows, final int p_columns, final int p_cellsize, final float p_unit )
        {
            m_sprite = new Sprite( TEXTURE.get() );
            m_sprite.setSize( p_cellsize, p_cellsize );
            m_sprite.setOrigin( 1.5f / p_cellsize, 1.5f / p_cellsize );
            m_sprite.setScale( p_unit );

            return this;
        }

    }
}