
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
    void spriteInitialize( final int p_xPosition, final int p_yPosition, final int p_rotation );
}
