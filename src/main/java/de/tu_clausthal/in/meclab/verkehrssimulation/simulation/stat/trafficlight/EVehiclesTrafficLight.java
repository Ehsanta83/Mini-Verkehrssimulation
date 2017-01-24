package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * vehicle traffic light enumeration
 *
 * @author Ehsan Tatasadi
 */
public enum EVehiclesTrafficLight implements IETrafficLight
{
    RED, REDYELLOW, GREEN, YELLOW;

    /**
     * texture of the sprite
     */
    private Texture m_texture;

    /**
     * constructor
     */
    private EVehiclesTrafficLight()
    {
        if ( m_texture == null )
        {
            m_texture = new Texture( Gdx.files.internal( "trafficlights/tl_" + this.toString().toLowerCase() + ".png" ) );
        }
    }

    @Override
    public Texture getTexture()
    {
        return m_texture;
    }

    @Override
    public Object call() throws Exception
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
