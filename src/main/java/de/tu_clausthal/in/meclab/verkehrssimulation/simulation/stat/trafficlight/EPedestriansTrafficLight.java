package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import com.badlogic.gdx.graphics.Texture;

/**
 * pedestrian traffic light enumeration
 */
public enum EPedestriansTrafficLight implements IETrafficLight
{
    RED, GREEN;
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
    public EPedestriansTrafficLight call() throws Exception
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
