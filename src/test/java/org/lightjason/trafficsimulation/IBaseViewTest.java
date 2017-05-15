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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector3;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * test class with window
 */
public abstract class IBaseViewTest extends IBaseTest
{

    /**
     * executes the screen
     *
     * @param p_windowwidth window width
     * @param p_windowheight window height
     * @return screen reference
     */
    protected CScreen execute( final Number p_windowwidth, final Number p_windowheight )
    {
        return this.execute( 250, 250, 20, 5, 100 );
    }


    /**
     * executes the screen
     *
     * @param p_windowwidth window width
     * @param p_windowheight window height
     * @param p_cellrows number of cell rows
     * @param p_cellcolumns number of cell columns
     * @param p_cellsize cell size
     * @return screen reference
     */
    protected CScreen execute( final Number p_windowwidth, final Number p_windowheight,
                               final Number p_cellrows, final Number p_cellcolumns, final Number p_cellsize )
    {
        return this.execute( p_windowwidth, p_windowheight, p_cellrows, p_cellcolumns, p_cellsize, 5, 100 );
    }


    /**
     * executes the screen
     *
     * @param p_windowwidth window width
     * @param p_windowheight window height
     * @param p_cellrows number of cell rows
     * @param p_cellcolumns number of cell columns
     * @param p_cellsize cell size
     * @param p_zoomspeed zoom speed
     * @param p_dragspeed drag speed
     * @return screen reference
     */
    protected CScreen execute( final Number p_windowwidth, final Number p_windowheight,
                               final Number p_cellrows, final Number p_cellcolumns, final Number p_cellsize,
                               final Number p_zoomspeed, final Number p_dragspeed )
    {
        // force-exit must be disabled for avoid error exiting
        final LwjglApplicationConfiguration l_config = new LwjglApplicationConfiguration();

        l_config.forceExit = false;
        l_config.width = p_windowheight.intValue();
        l_config.height = p_windowheight.intValue();

        // open window
        final CScreen l_screen = new CScreen( p_cellrows, p_cellcolumns, p_cellsize, p_zoomspeed, p_dragspeed );
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
         * @param p_rows number of rows
         * @param p_columns number of columns
         * @param p_cellsize cellsize
         * @param p_unit unit scale
         * @return self reference
         */
        ISprite spriteinitialize( final int p_rows, final int p_columns, final int p_cellsize, final float p_unit );

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
         * cell rows
         */
        private final int m_cellrows;
        /**
         * cell columns
         */
        private final int m_cellcolumns;
        /**
         * cell size
         */
        private final int m_cellsize;
        /**
         * last camera position
         */
        private final Vector3 m_lasttouch = new Vector3();
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
         *
         * @param p_cellrows number of rows
         * @param p_cellcolumns number of columns
         * @param p_cellsize cell size
         * @param p_zoomspeed zoom speed
         * @param p_dragspeed drag speed
         */
        CScreen( final Number p_cellrows, final Number p_cellcolumns, final Number p_cellsize, final Number p_zoomspeed, final Number p_dragspeed )
        {
            m_dragspeed = p_dragspeed.floatValue();
            m_zoomspeed = p_zoomspeed.floatValue();

            m_cellrows = p_cellrows.intValue();
            m_cellcolumns = p_cellcolumns.intValue();
            m_cellsize = p_cellsize.intValue();
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

        /**
         * creates a tilemap
         *
         * @param p_row number of rows
         * @param p_column number of columns
         * @param p_cellsize cell size
         * @return tile map
         */
        private static TiledMap tilemap( final int p_row, final int p_column, final int p_cellsize )
        {
            // create background checkerboard with a tile map
            final Pixmap l_pixmap = new Pixmap( 2 * p_cellsize, p_cellsize, Pixmap.Format.RGBA8888 );
            l_pixmap.setColor( new Color( 0.8f, 0.1f, 0.1f, 0.5f ) );
            l_pixmap.fillRectangle( 0, 0, p_cellsize, p_cellsize );
            l_pixmap.setColor( new Color( 0.5f, 0.5f, 0.5f, 0.5f ) );
            l_pixmap.fillRectangle( p_cellsize, 0, p_cellsize, p_cellsize );

            final Texture l_texture = new Texture( l_pixmap );
            final TiledMapTile l_region1 = new StaticTiledMapTile( new TextureRegion( l_texture, 0, 0, p_cellsize, p_cellsize ) );
            final TiledMapTile l_region2 = new StaticTiledMapTile( new TextureRegion( l_texture, p_cellsize, 0, p_cellsize, p_cellsize ) );

            // create tilemap
            final TiledMap l_map = new TiledMap();
            final TiledMapTileLayer l_layer = new TiledMapTileLayer( p_column, p_row, p_cellsize, p_cellsize );
            l_map.getLayers().add( l_layer );

            IntStream
                .range( 0, p_column )
                .forEach( x ->
                {
                    IntStream
                        .range( 0, p_row )
                        .forEach( y ->
                        {
                            final TiledMapTileLayer.Cell l_cell = new TiledMapTileLayer.Cell();
                            l_layer.setCell( x, y, l_cell );
                            l_cell.setTile(
                                y % 2 != 0
                                ? x % 2 != 0 ? l_region1 : l_region2
                                : x % 2 != 0 ? l_region2 : l_region1
                            );
                        } );
                } );

            return l_map;
        }


        @Override
        public final void create()
        {
            // create orthogonal camera perspective
            final float l_unit = 1.0f / m_cellsize;

            // create execution structure for painting
            m_spritebatch = new SpriteBatch();

            // create environment view and put all objects in it
            m_render = new OrthogonalTiledMapRenderer( tilemap( m_cellrows, m_cellcolumns, m_cellsize ), l_unit, m_spritebatch );

            m_camera = new OrthographicCamera( m_cellcolumns, m_cellrows );
            m_camera.setToOrtho( false, m_cellcolumns * l_unit, m_cellrows * l_unit );
            m_camera.position.set( m_cellcolumns / 2f, m_cellrows / 2f, 0 );
            m_camera.zoom = m_cellsize;

            // create sprites and particle systems
            m_sprites.forEach( i -> i.spriteinitialize( m_cellrows, m_cellcolumns, m_cellsize, l_unit ) );
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
            m_lasttouch.set( p_screenx, p_screeny, 0 );
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
                             .sub( m_lasttouch )
                             .scl( -m_dragspeed, m_dragspeed, 0 )
                             .scl( m_camera.zoom )
            );
            m_lasttouch.set( p_screenx, p_screeny, 0 );
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
                             : 1 - m_zoomspeed;
            return false;
        }
    }

}
