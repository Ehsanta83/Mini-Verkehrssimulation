package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * car class
 *
 * @author Ehsan Tatasadi
 */
public class CCar extends IBaseVehicle
{

    /**
     * constructor of CCar
     *
     * @param p_sprite                  sprite object from libgdx
     * @param p_velocity                velocity
     * @param p_currentStreet           east/west/north/south
     * @param p_currentDrivingDirection east/west/north/south
     * @param p_turning                 none/left/right
     */
    public CCar( final Sprite p_sprite, final int p_velocity, final String p_currentStreet,
                    final String p_currentDrivingDirection, final EVehicleTurning p_turning )
    {
        super( p_sprite, p_velocity, p_currentStreet, p_currentDrivingDirection, p_turning );
    }
}
