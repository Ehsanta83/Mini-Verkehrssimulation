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

package org.lightjason.trafficsimulation.gdx;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

/**
 * sprite wrapper object
 */
public abstract class IBaseSprite<T> implements ISprite<T>, Callable<ISprite<T>>
{
    /**
     * sprite object
     */
    protected AtomicReference<Sprite> m_sprite = new AtomicReference<>();
    /**
     * wrapping object
     */
    protected final T m_wrapping;

    /**
     * ctor
     *
     * @param p_wrapping wrapping object
     */
    protected IBaseSprite( final T p_wrapping )
    {
        m_wrapping = p_wrapping;
    }


    @Override
    public final Sprite sprite()
    {
        return m_sprite.get();
    }

    @Override
    public final T raw()
    {
        return m_wrapping;
    }
}
