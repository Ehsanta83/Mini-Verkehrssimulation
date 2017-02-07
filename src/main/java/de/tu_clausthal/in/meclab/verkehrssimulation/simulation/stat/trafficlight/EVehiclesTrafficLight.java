package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import com.badlogic.gdx.graphics.Texture;
import de.tu_clausthal.in.meclab.verkehrssimulation.CCommon;

/**
 * vehicle traffic light enumeration
 */
public enum EVehiclesTrafficLight implements IETrafficLight
{
    RED, REDYELLOW, GREEN, YELLOW;

    public static final String TEXTURE_FILE_NAME = CCommon.PACKAGEPATH + "trafficlights/vehicles_{0}.png";

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
    public EVehiclesTrafficLight call() throws Exception
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
