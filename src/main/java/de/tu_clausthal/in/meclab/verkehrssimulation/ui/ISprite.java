
package de.tu_clausthal.in.meclab.verkehrssimulation.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * visualize interface
 */
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
     * @param p_rows number of rows
     * @param p_columns number of columns
     * @param p_cellsize cellsize
     * @param p_unit unit scale
     */
    void spriteinitialize( final int p_rows, final int p_columns, final int p_cellsize, final float p_unit );
}
