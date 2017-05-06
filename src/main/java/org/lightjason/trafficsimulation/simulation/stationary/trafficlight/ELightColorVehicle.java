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

package org.lightjason.trafficsimulation.simulation.stationary.trafficlight;

import org.lightjason.trafficsimulation.CCommon;

import java.text.MessageFormat;
import java.util.Locale;


/**
 * vehicle traffic light enumeration
 *
 * @bug replace texture for jetty
 */
public enum ELightColorVehicle implements ITrafficLightColor<ELightColorVehicle>
{
    RED,
    REDYELLOW,
    GREEN,
    YELLOW;


    @Override
    public String path()
    {
        return MessageFormat.format( CCommon.PACKAGEPATH + "trafficlights/vehicles_{0}.png", this.name().toLowerCase( Locale.ROOT ) );
    }

    @Override
    public ELightColorVehicle next()
    {
        switch ( this )
        {
            case RED:
                return REDYELLOW;
            case REDYELLOW:
                return GREEN;
            case GREEN:
                return YELLOW;
            case YELLOW:
                return RED;

            default:
                return RED;
        }
    }
}
