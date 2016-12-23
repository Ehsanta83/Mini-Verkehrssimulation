package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IMovable;

/**
 * vehicle abstract class
 *
 * @author Ehsan Tatasadi
 */
public abstract class IBaseVehicle implements IMovable
{
    /**
     * sprite object from libgdx
     */
    private final Sprite m_sprite;
    /**
     * velocity of the vehicle
     */
    private int m_velocity;
    /**
     * current street of the vehicle
     * east/west/north/south
     */
    private String m_currentStreet;
    /**
     * current driving direction of the vehicle
     * east/west/north/south
     */
    private String m_currentDrivingDirection;
    /**
     * the index of the block of the lane of the street in which the vehicle now is
     */
    private int m_blockIndex;
    /**
     * in which direction the vehicle will drive in the intersection
     * none(drives direct)/left/right
     */
    private EVehicleTurning m_turning;
    /**
     * if the vehicle has turned right or left
     */
    private boolean m_isTurned;
    /**
     * the index of the vehicle in the vehicles array
     */
    private int m_index;

    /**
     * constructor of vehicle
     *
     * @param p_sprite sprite object from libgdx
     * @param p_velocity velocity
     * @param p_currentStreet east/west/north/south
     * @param p_currentDrivingDirection east/west/north/south
     * @param p_turning none/left/right
     */
    protected IBaseVehicle( final Sprite p_sprite, final int p_velocity, final String p_currentStreet,
                            final String p_currentDrivingDirection, final EVehicleTurning p_turning )
    {
        this.m_sprite = p_sprite;
        this.m_velocity = p_velocity;
        this.m_currentStreet = p_currentStreet;
        this.m_currentDrivingDirection = p_currentDrivingDirection;
        this.m_blockIndex = -1;
        this.m_turning = p_turning;
        this.m_isTurned = false;
        this.m_index = -1;
    }

    /**
     * get sprite
     *
     * @return sprite
     */
    public Sprite getSprite()
    {
        return m_sprite;
    }

    /**
     * get velocity
     *
     * @return velocity
     */
    public int getVelocity()
    {
        return m_velocity;
    }

    /**
     * set velocity
     *
     * @param p_velocity velocity
     */
    public void setVelocity( final int p_velocity )
    {
        this.m_velocity = p_velocity;
    }

    /**
     * get current street
     *
     * @return current street
     */
    public String getCurrentStreet()
    {
        return m_currentStreet;
    }

    /**
     * set current street
     *
     * @param p_currentStreet current street
     */
    public void setCurrentStreet( final String p_currentStreet )
    {
        this.m_currentStreet = p_currentStreet;
    }

    /**
     * get block index
     *
     * @return block index
     */
    public int getBlockIndex()
    {
        return m_blockIndex;
    }

    /**
     * set block index
     *
     * @param p_blockIndex block index
     */
    public void setBlockIndex( final int p_blockIndex )
    {
        this.m_blockIndex = p_blockIndex;
    }

    /**
     * get turning
     *
     * @return turning
     */
    public EVehicleTurning getTurning()
    {
        return m_turning;
    }

    /**
     * set turning
     *
     * @param p_turning turning
     */
    public void setTurning( final EVehicleTurning p_turning )
    {
        this.m_turning = p_turning;
    }

    /**
     * get current driving direction
     *
     * @return current driving direction
     */
    public String getCurrentDrivingDirection()
    {
        return m_currentDrivingDirection;
    }

    /**
     * set current driving direction
     *
     * @param p_currentDrivingDirection current driving direciton
     */
    public void setCurrentDrivingDirection( final String p_currentDrivingDirection )
    {
        this.m_currentDrivingDirection = p_currentDrivingDirection;
    }

    /**
     * get is turned
     *
     * @return is turned
     */
    public boolean isTurned()
    {
        return m_isTurned;
    }

    /**
     * set is turned
     *
     * @param p_isTurned is turned
     */
    public void setTurned( final boolean p_isTurned )
    {
        this.m_isTurned = p_isTurned;
    }

    /**
     * get index
     *
     * @return index
     */
    public int getIndex()
    {
        return m_index;
    }

    /**
     * set index
     *
     * @param p_index index
     */
    public void setIndex( final int p_index )
    {
        this.m_index = p_index;
    }

    /**
     * change the position of the sprite of the car in driving direction according to the velocity
     */
    public void move()
    {
        if ( "east".equals( m_currentDrivingDirection ) )
        {
            m_sprite.translateX( m_velocity );
        }
        else if ( "north".equals( m_currentDrivingDirection ) )
        {
            m_sprite.translateY( m_velocity );
        }
        else if ( "west".equals( m_currentDrivingDirection ) )
        {
            m_sprite.translateX( -m_velocity );
        }
        else if ( "south".equals( m_currentDrivingDirection ) )
        {
            m_sprite.translateY( -m_velocity );
        }
    }

    @Override
    public Runnable call() throws Exception
    {
        return null;
    }
}
