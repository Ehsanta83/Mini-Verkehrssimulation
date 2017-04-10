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
