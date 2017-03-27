
package de.tu_clausthal.in.meclab.verkehrssimulation.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * visualize interface
 *
 * @deprecated remove for jetty
 */
@Deprecated
public interface ISprite extends IVisualize
{
    /**
     * returns the sprite
     *
     * @return sprite object
     */
    Sprite sprite();

    /**
     * sprite initialize for correct painting initialization
     *
     */
    void spriteinitialize( final float p_unit );
}
