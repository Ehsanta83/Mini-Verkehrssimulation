package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import de.tu_clausthal.in.meclab.verkehrssimulation.CSimulation;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.followingmodel.CNagelSchreckenberg;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IMovable;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight.EVehiclesTrafficLight;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual.CStreet;

import java.util.HashMap;

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
     * if the vehicle is out of the screen
     */
    private boolean m_isOut;

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
     *  get is out
     *
     * @return is out
     */
    public boolean isOut()
    {
        return m_isOut;
    }

    /**
     * set is out
     *
     * @param p_isOut
     */
    public void setIsOut( final boolean p_isOut )
    {
        this.m_isOut = p_isOut;
    }


    /**
     * apply traffic light to the cars
     *
     * @param p_street street
     * @param p_velocity velocity
     * @param p_blockIndex block index
     * @return new velocity
     */
    private int applyTrafficLightToVelocity( final CStreet p_street, final int p_velocity, final int p_blockIndex )
    {
        int l_newVelocity = p_velocity;
        if ( p_blockIndex == 6 && p_velocity > 3 )
            l_newVelocity = 3;
        if ( p_blockIndex == 7 && p_velocity > 2 )
            l_newVelocity = 1;
        if ( p_blockIndex == 8 && p_street.getVehiclesTrafficLight().getStatus() == EVehiclesTrafficLight.RED )
            l_newVelocity = 0;
        return l_newVelocity;
    }

    @Override
    public Object call()
    {
        int l_xPosition = (int) m_sprite.getX();
        int l_yPosition = (int) m_sprite.getY();
        final HashMap<String, CStreet> l_streets = CSimulation.getStreets();
        final CNagelSchreckenberg l_nagelSchreckenberg = new CNagelSchreckenberg();
        final int l_distanceFromStartInMovingAxis = l_streets.get( m_currentStreet ).getDistanceBetweenVehicleAndStartPointInMovingAxis( this );
        if ( l_distanceFromStartInMovingAxis < 432 )
        {
            final int l_newBlockIndex = l_distanceFromStartInMovingAxis / 48;
            if ( l_newBlockIndex > m_blockIndex && !l_streets.get( m_currentStreet ).getFirstLane().isBlockOccupied( l_newBlockIndex ) )
            {
                if ( m_blockIndex > -1 )
                {
                    l_streets.get( m_currentStreet ).getFirstLane().emptyBlock( m_blockIndex );
                }
                l_streets.get( m_currentStreet ).getFirstLane().occupyBlock( l_newBlockIndex );
                m_blockIndex = l_newBlockIndex;
            }
            m_velocity = l_nagelSchreckenberg.applyModelToAVehicle( m_velocity, m_blockIndex, l_streets.get( m_currentStreet ).getFirstLane().getNextOccupiedBlockIndexFromIndex( m_blockIndex ) );
            m_velocity = applyTrafficLightToVelocity( l_streets.get( m_currentStreet ), m_velocity, m_blockIndex );
        }
        else if ( l_distanceFromStartInMovingAxis >= 432 && l_distanceFromStartInMovingAxis < 480 )
        {
            if ( m_blockIndex > -1 )
            {
                l_streets.get( m_currentStreet ).getFirstLane().emptyBlock( m_blockIndex );
                m_blockIndex = -1;
            }
        }
        else if ( l_distanceFromStartInMovingAxis >= 480 && l_distanceFromStartInMovingAxis < 512 )
        {
            if ( m_turning.equals( EVehicleTurning.RIGHT ) )
            {
                m_sprite.setRotation( l_streets.get( m_currentStreet ).getRightRotationAngel() );
                l_xPosition = l_streets.get( m_currentStreet ).getNewXAfterTurningRight();
                l_yPosition = l_streets.get( m_currentStreet ).getNewYAfterTurningRight();
                final String l_newStreet = l_streets.get( m_currentStreet ).getNewDirectionAfterTurningRight();
                setCurrentStreet( l_newStreet );
                setCurrentDrivingDirection( l_newStreet );
                setTurning( EVehicleTurning.NONE );
                setTurned( true );
            }
        }
        else if ( l_distanceFromStartInMovingAxis >= 512 && l_distanceFromStartInMovingAxis < 592 )
        {
            if ( m_turning.equals( EVehicleTurning.LEFT ) )
            {
                m_sprite.setRotation( l_streets.get( m_currentStreet ).getLeftRotationAngel() );
                l_xPosition = l_streets.get( m_currentStreet ).getNewXAfterTurningLeft();
                l_yPosition = l_streets.get( m_currentStreet ).getNewYAfterTurningLeft();
                final String l_newStreet = l_streets.get( m_currentStreet ).getNewDirectionAfterTurningLeft();
                setCurrentStreet( l_newStreet );
                setCurrentDrivingDirection( l_newStreet );
                setTurning( EVehicleTurning.NONE );
                setTurned( true );
            }
        }
        else if ( l_distanceFromStartInMovingAxis >= 592 && l_distanceFromStartInMovingAxis < 1024 )
        {
            final int l_newBlockIndex = ( l_distanceFromStartInMovingAxis - 592 ) / 48;
            if ( l_newBlockIndex > m_blockIndex && !l_streets.get( m_currentStreet ).getSecondLane().isBlockOccupied( l_newBlockIndex ) )
            {
                if ( m_blockIndex > -1 )
                {
                    l_streets.get( m_currentStreet ).getSecondLane().emptyBlock( m_blockIndex );
                }
                l_streets.get( m_currentStreet ).getSecondLane().occupyBlock( l_newBlockIndex );
                m_blockIndex = l_newBlockIndex;
            }
            m_velocity = l_nagelSchreckenberg.applyModelToAVehicle( m_velocity, m_blockIndex, l_streets.get( m_currentStreet ).getSecondLane().getNextOccupiedBlockIndexFromIndex( m_blockIndex ) );
        }
        else if ( l_distanceFromStartInMovingAxis >= 1024 )
        {
            if ( m_blockIndex > -1 )
            {
                l_streets.get( m_currentStreet ).getSecondLane().emptyBlock( m_blockIndex );
            }

            setIsOut( true );
        }
        setBlockIndex( m_blockIndex );
        setVelocity( m_velocity );
        getSprite().setPosition( l_xPosition, l_yPosition );
        if ( m_velocity > 0 )
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
        return null;
    }
}
