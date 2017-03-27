package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import com.badlogic.gdx.graphics.Texture;


/**
 * traffic light enumeration interface
 */
public interface IETrafficLightColor
{
    /**
     * set texture
     */
    void setTexture( Texture p_texture );

    /**
     * get texture of the sprite
     * @return texture
     */
    Texture getTexture();

    /**
     * returns the path of the light texture
     * @return
     */
    String path();

    IETrafficLightColor next();

}
