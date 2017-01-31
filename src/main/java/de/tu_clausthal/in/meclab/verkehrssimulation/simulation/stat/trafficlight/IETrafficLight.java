package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import com.badlogic.gdx.graphics.Texture;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IExecutable;

/**
 * traffic light enumeration interface
 */
public interface IETrafficLight extends IExecutable
{
    /**
     * get texture of the sprite
     * @return texture
     */
    Texture getTexture();
}
