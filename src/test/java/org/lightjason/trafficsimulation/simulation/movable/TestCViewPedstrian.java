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

import cern.colt.matrix.DoubleMatrix1D;
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
public final class TestCViewPedstrian extends IBaseViewTest
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
        this.initializeenvironment( 50, 10, 10, ERoutingFactory.JPSPLUS.get() );
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
        s_screen = screen( 1600, 1200, 50, 50, 10 );
        new TestCViewPedstrian().invoketest();
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
        private CPedestrianSprite( final CPedestrian p_wrapping )
        {
            super( p_wrapping );
        }

        @Override
        public final ISprite<CPedestrian> call() throws Exception
        {
            m_wrapping.call();
            spriteposition( m_sprite.get(), m_wrapping.position() );
            return this;
        }

        /**
         * creates the texture
         *
         * @return texture initialize
         */
        private static Texture texture( final int p_cellsize )
        {
            final Pixmap l_pixmap = new Pixmap( p_cellsize, p_cellsize, Pixmap.Format.RGBA8888 );
            l_pixmap.setColor( Color.RED );
            l_pixmap.fillCircle( p_cellsize / 2, p_cellsize / 2, (int)( 0.95 * p_cellsize / 2 ) );


            final Texture l_texture = new Texture( l_pixmap );
            l_pixmap.dispose();
            return l_texture;
        }

        @Override
        public final synchronized ISprite<CPedestrian> spriteinitialize( final int p_rows, final int p_columns, final int p_cellsize, final float p_unit )
        {
            if ( ( !TEXTURE.compareAndSet( null, texture( p_cellsize ) ) )
                 || ( !m_sprite.compareAndSet( null, new Sprite( TEXTURE.get() ) ) )
                )
                return this;

            m_sprite.get().setSize( p_cellsize, p_cellsize );
            m_sprite.get().setOrigin( 0, 0 );
            m_sprite.get().setScale( p_unit );

            spriteposition( m_sprite.get(), m_wrapping.position() );
            return this;
        }

        /**
         *  sets the position of the sprite
         * @param p_sprite sprite
         * @param p_position position
         */
        private static void spriteposition( final Sprite p_sprite, final DoubleMatrix1D p_position )
        {
            p_sprite.setPosition(
                (int) p_position.getQuick( 0 ),
                (int) p_position.getQuick( 1 )
            );
        }

    }
}
