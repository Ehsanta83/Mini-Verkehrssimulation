package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import de.tu_clausthal.in.meclab.verkehrssimulation.CCommon;

/**
 * vehicle traffic light enumeration
 */
public enum EVehiclesTrafficLight implements IETrafficLight
{
    RED, REDYELLOW, GREEN, YELLOW;

    /**
     * texture of the sprite
     */
    private Texture m_texture;

    @Override
    public Texture getTexture()
    {
        if ( m_texture == null )
        {
            m_texture = new Texture( Gdx.files.internal( CCommon.PACKAGEPATH + "trafficlights/tl_" + this.toString().toLowerCase() + ".png" ) );
        }
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
