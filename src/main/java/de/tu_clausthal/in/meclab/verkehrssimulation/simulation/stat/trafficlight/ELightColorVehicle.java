package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import com.badlogic.gdx.graphics.Texture;
import de.tu_clausthal.in.meclab.verkehrssimulation.CCommon;

import java.text.MessageFormat;
import java.util.Locale;


/**
 * vehicle traffic light enumeration
 * @bug replace texture for jetty
 */
public enum ELightColorVehicle implements ITrafficLightColor
{
    RED, REDYELLOW, GREEN, YELLOW;

    /**
     * texture of the sprite
     */
    private Texture m_texture;

    @Override
    public void setTexture( final Texture p_texture )
    {
        m_texture = p_texture;
    }



    @Override
    public Texture getTexture()
    {
        return m_texture;
    }

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
            case RED : return REDYELLOW;
            case REDYELLOW: return GREEN;
            case GREEN : return YELLOW;
            case YELLOW : return RED;

            default:
                return RED;
        }
    }
}
