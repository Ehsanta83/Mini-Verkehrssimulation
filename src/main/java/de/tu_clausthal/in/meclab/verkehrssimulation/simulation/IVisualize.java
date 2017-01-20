
package de.tu_clausthal.in.meclab.verkehrssimulation.simulation;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * visualize interface
 *
 * @author Ehsan Tatasadi
 */
public interface IVisualize
{
    /**
     * return the sprite
     *
     * @return sprite object
     */
    Sprite sprite();

    /**
     * initialize sprite
     */
    void spriteInitialize();
}
