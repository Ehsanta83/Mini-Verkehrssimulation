package de.tu_clausthal.in.meclab.verkehrssimulation.ui;

import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * interface for creating a tilemap (environment)
 *
 * @deprecated remove for jetty
 */
@Deprecated
public interface ITileMap extends IVisualize
{
    /**
     * returns the tilemap
     *
     * @return map
     */
    TiledMap map();

    /**
     * returns the number of rows
     *
     * @return rows
     */
    int row();

    /**
     * returns the number of columns
     *
     * @return columns
     */
    int column();

    /**
     * returns the cell size
     *
     * @return cell size
     */
    int cellsize();
}
