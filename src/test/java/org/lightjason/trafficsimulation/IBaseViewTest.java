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

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Stream;


public abstract class IBaseViewTest extends IBaseTest
{

    protected CScreen execute( final int p_weight, final int p_height )
    {
        // force-exit must be disabled for avoid error exiting
        final LwjglApplicationConfiguration l_config = new LwjglApplicationConfiguration();

        l_config.forceExit = false;
        l_config.width = p_weight;
        l_config.height = p_height;

        // open window
        final CScreen l_screen = new CScreen();
        new LwjglApplication( l_screen, l_config );
        return l_screen;
    }



    /**
     * interface for visualization
     */
    protected interface ISprite
    {

        /**
         * initialize the sprite
         *
         * @param p_width world width
         * @param p_height world height
         * @param p_unit unit scaling
         * @return self reference
         */
        ISprite spriteinitialize( final int p_width, final int p_height, float p_unit );

        /**
         * returns sprite object
         *
         * @return sprite
         */
        Sprite sprite();
    }


    /**
     * screen class
     */
    private static final class CScreen extends ApplicationAdapter implements InputProcessor
    {
        /**
         * drag speed
         */
        private final float m_dragspeed;
        /**
         * zoom speed
         */
        private final float m_zoomspeed;
        /**
         * last camera position
         */
        private final Vector3 m_lastTouch = new Vector3();
        /**
         * camera definition
         */
        private OrthographicCamera m_camera;
        /**
         * sprite batch painting
         */
        private SpriteBatch m_spritebatch;
        /**
         * renderer
         */
        private OrthogonalTiledMapRenderer m_render;
        /**
         * set with sprites
         */
        private final Set<ISprite> m_sprites = new ConcurrentSkipListSet<>();

        /**
         * ctor
         */
        CScreen()
        {
            this( 5, 100 );
        }

        /**
         * ctor
         *
         * @param p_zoomspeed zoom speed
         * @param p_dragspeed drag speed
         */
        CScreen( final Number p_zoomspeed, final Number p_dragspeed )
        {
            m_dragspeed = p_dragspeed.floatValue();
            m_zoomspeed = p_zoomspeed.floatValue();
        }

        /**
         * adds sprites
         *
         * @param p_sprites sprites
         * @return self reference
         */
        public final CScreen spriteadd( final ISprite... p_sprites )
        {
            return this.spriteadd( Arrays.stream( p_sprites ) );
        }

        /**
         * removes sprite
         *
         * @param p_sprites sprites
         * @return self reference
         */
        public final CScreen spriteremove( final ISprite... p_sprites  )
        {
            return this.spriteremove( Arrays.stream( p_sprites ) );
        }

        /**
         * add sprite stream
         *
         * @param p_sprites sprite stream
         * @return self reference
         */
        public final CScreen spriteadd( final Stream<? extends ISprite> p_sprites )
        {
            p_sprites.forEach( m_sprites::add );
            return this;
        }

        /**
         * removes sprite
         *
         * @param p_sprites sprite stream
         * @return self reference
         */
        public final CScreen spriteremove( final Stream<? extends ISprite> p_sprites )
        {
            p_sprites.forEach( m_sprites::remove );
            return this;
        }


        @Override
        public final void create()
        {
            // create orthogonal camera perspective
            final float l_unit = 1.0f / m_environment.cellsize();

            // create execution structure for painting
            m_spritebatch = new SpriteBatch();

            // create environment view and put all objects in it
            m_render = new OrthogonalTiledMapRenderer( m_environment.map(), l_unit, m_spritebatch );

            m_camera = new OrthographicCamera( m_environment.column(), m_environment.row() );
            m_camera.setToOrtho( false, m_environment.column() * l_unit, m_environment.row() * l_unit );
            m_camera.position.set( m_environment.column() / 2f, m_environment.row() / 2f, 0 );
            m_camera.zoom = m_environment.cellsize();

            // create sprites and particle systems
            m_sprites.forEach( i -> i.spriteinitialize( m_environment.row(), m_environment.column(), m_environment.cellsize(), l_unit ) );
            m_render.setView( m_camera );

            // set input processor
            Gdx.input.setInputProcessor( this );
        }

        @Override
        public final void render()
        {
            // camera update must be the first for reaction on input device events
            m_camera.update();

            // create black background
            Gdx.gl.glClearColor( 0, 0, 0, 1 );
            Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

            // environment tilemap painting
            m_render.setView( m_camera );
            m_render.render();


            // object sprite painting
            m_spritebatch.setProjectionMatrix( m_camera.combined );
            m_spritebatch.begin();

            m_sprites.forEach( i -> i.sprite().draw( m_spritebatch ) );

            m_spritebatch.end();
        }

        @Override
        public final boolean keyDown( final int p_key )
        {
            return false;
        }

        @Override
        public final boolean keyUp( final int p_key )
        {
            return false;
        }

        @Override
        public final boolean keyTyped( final char p_char )
        {
            return false;
        }

        @Override
        public final boolean touchDown( final int p_screenx, final int p_screeny, final int p_pointer, final int p_button )
        {
            m_lastTouch.set( p_screenx, p_screeny, 0 );
            return false;
        }

        @Override
        public final boolean touchUp( final int p_xposition, final int p_yposition, final int p_pointer, final int p_button )
        {
            return false;
        }

        @Override
        public final boolean touchDragged( final int p_screenx, final int p_screeny, final int p_pointer )
        {
            m_camera.translate(
                new Vector3().set( p_screenx, p_screeny, 0 )
                             .sub( m_lastTouch )
                             .scl( -m_dragspeed, m_dragspeed, 0 )
                             .scl( m_camera.zoom )
            );
            m_lastTouch.set( p_screenx, p_screeny, 0 );
            return false;
        }

        @Override
        public final boolean mouseMoved( final int p_xposition, final int p_yposition )
        {
            return false;
        }

        @Override
        public final boolean scrolled( final int p_amount )
        {
            m_camera.zoom *= p_amount > 0
                             ? 1 + m_zoomspeed
                             : 1 - m_zoomspeed
            return false;
        }
    }

}
