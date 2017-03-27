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
    @Deprecated
    TiledMap map();

    /**
     * returns the number of rows
     *
     * @return rows
     */
    @Deprecated
    int row();

    /**
     * returns the number of columns
     *
     * @return columns
     */
    @Deprecated
    int column();

    /**
     * returns the cell size
     *
     * @return cell size
     */
    @Deprecated
    int cellsize();
}
