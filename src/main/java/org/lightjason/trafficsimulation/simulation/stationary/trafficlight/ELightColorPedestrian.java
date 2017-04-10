package org.lightjason.trafficsimulation.simulation.stationary.trafficlight;

import org.lightjason.trafficsimulation.CCommon;

import java.text.MessageFormat;
import java.util.Locale;


/**
 * pedestrian traffic light enumeration
 *
 * @bug replace texture for jetty
 */
public enum ELightColorPedestrian implements ITrafficLightColor<ELightColorPedestrian>
{
    RED,
    GREEN;

    @Override
    public String path()
    {
        return MessageFormat.format( CCommon.PACKAGEPATH + "trafficlights/vehicles_{0}.png", this.name().toLowerCase( Locale.ROOT ) );
    }

    @Override
    public ELightColorPedestrian next()
    {
        switch ( this )
        {
            case RED:
                return GREEN;
            case GREEN:
                return RED;

            default:
                return RED;
        }
    }
}
