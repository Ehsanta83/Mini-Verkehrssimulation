package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stationary.trafficlight;

import com.badlogic.gdx.graphics.Texture;


/**
 * traffic light enumeration interface
 *
 * @bug remove texture for jetty
 */
public interface ITrafficLightColor
{
    /**
     * get texture of the sprite
     *
     * @return texture
     */
    @Deprecated
    Texture getTexture();

    /**
     * set texture
     */
    @Deprecated
    void setTexture( Texture p_texture );

    /**
     * returns the path of the light texture
     *
     * @return
     */
    String path();

    /**
     * changes the color
     *
     * @return new color reference
     */
    ITrafficLightColor next();

}
