package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import de.tu_clausthal.in.meclab.verkehrssimulation.CCommon;

/**
 * pedestrian traffic light enumeration
 */
public enum EPedestrianTrafficLight implements IETrafficLight
{
    RED, GREEN;
    /**
     * texture of the sprite
     */
    private Texture m_texture;

    /**
     * initialize textures
     */
    public static final void initializeTextures()
    {
        //ToDo: later change to pedestrian traffic light
        RED.m_texture = new Texture( Gdx.files.internal( CCommon.PACKAGEPATH + "trafficlights/tl_red.png" ) );
        GREEN.m_texture = new Texture( Gdx.files.internal( CCommon.PACKAGEPATH + "trafficlights/tl_green.png" ) );
    }

    @Override
    public Texture getTexture()
    {
        return m_texture;
    }

    @Override
    public EPedestrianTrafficLight call() throws Exception
    {
        switch ( this )
        {
            case RED : return GREEN;
            case GREEN : return RED;

            default:
                return RED;
        }
    }
}
