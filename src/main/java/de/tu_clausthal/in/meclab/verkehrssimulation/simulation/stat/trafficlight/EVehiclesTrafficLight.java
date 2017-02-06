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

    /**
     * initialize textures
     */
    public static final void initializeTextures()
    {
        RED.m_texture = new Texture( Gdx.files.internal( CCommon.PACKAGEPATH + "trafficlights/tl_red.png" ) );
        REDYELLOW.m_texture = new Texture( Gdx.files.internal( CCommon.PACKAGEPATH + "trafficlights/tl_redyellow.png" ) );
        GREEN.m_texture = new Texture( Gdx.files.internal( CCommon.PACKAGEPATH + "trafficlights/tl_green.png" ) );
        YELLOW.m_texture = new Texture( Gdx.files.internal( CCommon.PACKAGEPATH + "trafficlights/tl_yellow.png" ) );
    }

    /**
     * dispose textures
     */
    public static final void disposeTextures()
    {
        RED.m_texture.dispose();
        REDYELLOW.m_texture.dispose();
        GREEN.m_texture.dispose();
        YELLOW.m_texture.dispose();
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
