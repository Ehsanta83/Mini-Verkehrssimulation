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

import cern.colt.matrix.impl.DenseDoubleMatrix1D;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * class for generating bounding box
 */
public enum EBoundingBoxFactory
{
    CIRCLE;

    /**
     * generate a bounding box
     *
     * @return the bounding box
     */
    public final IBoundingBox generate( final double... p_arguments )
    {
        switch ( this )
        {
            case CIRCLE:
                return new CCircleBoundingBox(
                    new DenseDoubleMatrix1D( new double[]{p_arguments[0], p_arguments[1]} ),
                    p_arguments[2] );

            default:
                throw new RuntimeException( MessageFormat.format( "no generator [{0}] found", this ) );
        }
    }

    /**
     * get the factory from a name
     *
     * @param p_name name
     * @return the factory
     */
    public static EBoundingBoxFactory from( final String p_name )
    {
        return EBoundingBoxFactory.valueOf( p_name.toUpperCase( Locale.ROOT ) );
    }
}
