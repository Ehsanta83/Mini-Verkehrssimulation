package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

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
     * constructor
     */
    private EPedestrianTrafficLight()
    {
        if ( m_texture == null )
        {
            //ToDO: later change to pedestrian traffic light texture
            m_texture = new Texture( Gdx.files.internal( "trafficlights/tl_" + this.toString().toLowerCase() + ".png" ) );
        }
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
