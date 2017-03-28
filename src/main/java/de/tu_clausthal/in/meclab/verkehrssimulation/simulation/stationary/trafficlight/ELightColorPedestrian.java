package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stationary.trafficlight;

import de.tu_clausthal.in.meclab.verkehrssimulation.CCommon;

import java.text.MessageFormat;
import java.util.Locale;


/**
 * pedestrian traffic light enumeration
 *
 * @bug replace texture for jetty
 */
public enum ELightColorPedestrian implements ITrafficLightColor
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
