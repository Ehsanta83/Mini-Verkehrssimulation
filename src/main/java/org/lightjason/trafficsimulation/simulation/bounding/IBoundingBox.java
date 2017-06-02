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


package org.lightjason.trafficsimulation.simulation.bounding;

import cern.colt.matrix.DoubleMatrix1D;

/**
 * interface for bounding box
 *
 * @todo how can we change the bounding box or its size in the runtime?
 */
public interface IBoundingBox
{
    /**
     * if the bounding box intersect with another one
     *
     * @param p_boundingbox bounding box
     * @return if intersects
     */
    boolean intersects( final IBoundingBox p_boundingbox );

    /**
     * resize the bounding box
     *
     * @param p_objectsize the size of the object
     * @param p_percent percent of the resize
     */
    void resize( final DoubleMatrix1D p_objectsize, final int p_percent );
}
