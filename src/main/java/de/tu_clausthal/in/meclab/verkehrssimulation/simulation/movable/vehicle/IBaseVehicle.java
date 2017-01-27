package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual.CVehiclesWay;
import de.tu_clausthal.in.meclab.verkehrssimulation.ui.CScreen;
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
     * coordinates in the current way [indexOfLane, indexOfBlock]
     */
    private int[] m_coordinateInWay;
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
        m_sprite = p_sprite;
        m_velocity = p_velocity;
        m_currentStreet = p_currentStreet;
        m_currentDrivingDirection = p_currentDrivingDirection;
        m_coordinateInWay = new int[]{0, -1};
        m_turning = p_turning;
        m_isTurned = false;
    }

    @Override
    public Sprite sprite()
    {
        return m_sprite;
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
     * get is turned
     *
     * @return is turned
     */
    public boolean isTurned()
    {
        return m_isTurned;
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
     * apply traffic light to the cars
     *
     * @param p_street street
     * @param p_velocity velocity
     * @param p_blockIndex block index
     * @return new velocity
     */
    private int applyTrafficLightToVelocity( final CStreet p_street, final int p_velocity, final int p_blockIndex )
    {
        if ( p_blockIndex == 6 && p_velocity > 3 )
            return 3;
        if ( p_blockIndex == 7 && p_velocity > 2 )
            return 1;
        if ( p_blockIndex == 8 && p_street.getVehiclesTrafficLight().getColor() != EVehiclesTrafficLight.GREEN )
            return 0;
        return p_velocity;
    }

    /**
     * move the vehicle in a way
     *
     * @param p_way the vehicles way of the street
     * @param p_newBlockIndex new block index of the car in the lane
     */
    private void moveInAWay( final CVehiclesWay p_way, final int p_newBlockIndex )
    {
        if ( p_newBlockIndex > m_coordinateInWay[1] && !p_way.isBlockOccupied( new int[]{m_coordinateInWay[0], p_newBlockIndex} ) )
        {
            if ( m_coordinateInWay[1] > -1 )
            {
                p_way.emptyBlock( m_coordinateInWay );
            }
            p_way.occupyBlock( this, new int[]{m_coordinateInWay[0], p_newBlockIndex} );
            m_coordinateInWay[1] = p_newBlockIndex;
        }
        m_velocity = CNagelSchreckenberg.INSTANCE.applyModelToAVehicle( m_velocity, m_coordinateInWay[1], p_way.getNextOccupiedBlockIndexFromIndexInALane( m_coordinateInWay ) );
    }

    @Override
    public IBaseVehicle call()
    {
        int l_xPosition = (int) m_sprite.getX();
        int l_yPosition = (int) m_sprite.getY();
        final HashMap<String, CStreet> l_streets = CScreen.getStreets();
        final int l_distanceFromStartInMovingAxis = l_streets.get( m_currentStreet ).getDistanceBetweenVehicleAndStartPointInMovingAxis( this );
        if ( l_distanceFromStartInMovingAxis < 432 )
        {
            final int l_newBlockIndex = l_distanceFromStartInMovingAxis / 48;
            moveInAWay( l_streets.get( m_currentStreet ).getFirstWay(), l_newBlockIndex );
            m_velocity = applyTrafficLightToVelocity( l_streets.get( m_currentStreet ), m_velocity, m_coordinateInWay[1] );
        }
        else if ( l_distanceFromStartInMovingAxis >= 432 && l_distanceFromStartInMovingAxis < 480 )
        {
            if ( m_coordinateInWay[1] > -1 )
            {
                l_streets.get( m_currentStreet ).getFirstWay().emptyBlock( m_coordinateInWay );
                m_coordinateInWay[1] = -1;
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
                m_currentStreet = l_newStreet;
                m_currentDrivingDirection = l_newStreet;
                m_turning = EVehicleTurning.NONE;
                m_isTurned = true;
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
                m_currentStreet = l_newStreet;
                m_currentDrivingDirection = l_newStreet;
                m_turning = EVehicleTurning.NONE;
                m_isTurned = true;
            }
        }
        else if ( l_distanceFromStartInMovingAxis >= 592 && l_distanceFromStartInMovingAxis < 1024 )
        {
            final int l_newBlockIndex = ( l_distanceFromStartInMovingAxis - 592 ) / 48;
            moveInAWay( l_streets.get( m_currentStreet ).getSecondWay(), l_newBlockIndex );
            m_velocity = CNagelSchreckenberg.INSTANCE.applyModelToAVehicle( m_velocity, m_coordinateInWay[1], l_streets.get( m_currentStreet ).getSecondWay().getNextOccupiedBlockIndexFromIndexInALane( m_coordinateInWay ) );
        }
        else if ( l_distanceFromStartInMovingAxis >= 1024 )
        {
            if ( m_coordinateInWay[1] > -1 )
            {
                l_streets.get( m_currentStreet ).getSecondWay().emptyBlock( m_coordinateInWay );
            }

            m_isOut = true;
        }
        sprite().setPosition( l_xPosition, l_yPosition );
        if ( m_velocity > 0 )
        {
            switch ( m_currentDrivingDirection )
            {
                case "east":
                    m_sprite.translateX( m_velocity );
                    break;
                case "north":
                    m_sprite.translateY( m_velocity );
                    break;
                case "west":
                    m_sprite.translateX( -m_velocity );
                    break;
                case "south":
                    m_sprite.translateY( -m_velocity );
                    break;
                default:
            }
        }
        return this;
    }

    @Override
    public void spriteInitialize( final int p_xPosition, final int p_yPosition, final int p_rotation )
    {
    }
}
