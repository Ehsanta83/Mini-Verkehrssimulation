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

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * interface for visualization
 */
public interface ISprite<T>
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
    ISprite<T> spriteinitialize( final int p_rows, final int p_columns, final int p_cellsize, final float p_unit );

    /**
     * returns sprite object
     *
     * @return sprite
     */
    Sprite sprite();

    /**
     * returns the wrap data structure
     *
     * @return inner object
     */
    T raw();
}
