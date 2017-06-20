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

package org.lightjason.trafficsimulation.ui;

import cern.colt.matrix.DoubleMatrix1D;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.trafficsimulation.simulation.movable.CVehicle;

import java.util.concurrent.atomic.AtomicReference;

/**
 * vehicle sprite
 */
public final class CVehicleSprite extends IBaseSprite<CVehicle>
{
    /**
     * texture
     */
    private final AtomicReference<Texture> m_texture = new AtomicReference<>();

    /**
     * ctor
     *
     * @param p_wrapping wrapping object
     */
    public CVehicleSprite( final CVehicle p_wrapping )
    {
        super( p_wrapping );
    }

    @Override
    public final ISprite<CVehicle> call() throws Exception
    {
        m_wrapping.call();
        this.spriteposition( m_sprite.get(), m_wrapping.position() );
        return this;
    }

    /**
     * creates the texture
     *
     * @return texture initialize
     * @todo what if the agent has more than one cell? maybe using the radius of the agent?
     */
    private Texture texture( final int p_cellsize )
    {
        final Pixmap l_pixmap = new Pixmap( p_cellsize * 2, p_cellsize, Pixmap.Format.RGBA8888 );
        l_pixmap.setColor( Color.GREEN );
        l_pixmap.fillRectangle( 0, 0, p_cellsize * 2, p_cellsize );

        final Texture l_texture = new Texture( l_pixmap );
        l_pixmap.dispose();
        return l_texture;
    }

    @Override
    public final synchronized ISprite<CVehicle> spriteinitialize( final int p_rows, final int p_columns, final int p_cellsize, final float p_unit )
    {
        if ( ( !m_texture.compareAndSet( null, this.texture( p_cellsize ) ) )
            || ( !m_sprite.compareAndSet( null, new Sprite( m_texture.get() ) ) )
            )
            return this;

        m_sprite.get().setSize( p_cellsize, p_cellsize );
        m_sprite.get().setOrigin( 0, 0 );
        m_sprite.get().setScale( p_unit );

        this.spriteposition( m_sprite.get(), m_wrapping.position() );
        return this;
    }

    /**
     *  sets the position of the sprite
     * @param p_sprite sprite
     * @param p_position position
     */
    private void spriteposition( final Sprite p_sprite, final DoubleMatrix1D p_position )
    {
        if ( p_sprite == null )
            return;
        p_sprite.setPosition(
            (int) p_position.getQuick( 0 ),
            (int) p_position.getQuick( 1 )
        );
    }

}
