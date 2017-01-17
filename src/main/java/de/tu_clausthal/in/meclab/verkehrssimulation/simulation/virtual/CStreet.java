package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import com.badlogic.gdx.Gdx;
import de.tu_clausthal.in.meclab.verkehrssimulation.CSimulation;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle.IBaseVehicle;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight.CVehiclesTrafficLight;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight.EVehiclesTrafficLight;

import java.util.HashMap;

/**
 * street class
 *
 * @author Ehsan Tatasadi
 */
public class CStreet implements IVirtual
{
    /**
     * vehicles traffic light
     */
    private final CVehiclesTrafficLight m_vehiclesTrafficLight;
    /**
     * first lane
     */
    private final CLane m_firstLane;
    /**
     * second lane (opposite lane)
     */
    private final CLane m_secondLane;
    /**
     * new rotation angel of the vehicle after turning right
     */
    private final int m_rightRotationAngel;
    /**
     * new rotation angel of the vehicle after turning left
     */
    private final int m_leftRotationAngel;
    /**
     * new driving direction of the vehicle after turning right
     */
    private final String m_newDirectionAfterTurningRight;
    /**
     * new driving direction of the vehicle after turning left
     */
    private final String m_newDirectionAfterTurningLeft;
    /**
     * new x position of the vehicle after turning right
     */
    private final int m_newXAfterTurningRight;
    /**
     * new x position of the vehicle after turning left
     */
    private final int m_newXAfterTurningLeft;
    /**
     * new y position of the vehicle after turning right
     */
    private final int m_newYAfterTurningRight;
    /**
     * new y position of the vehicle after turning left
     */
    private final int m_newYAfterTurningLeft;
    /**
     * opposite street
     */
    private final String m_oppositeStreet;

    /**
     * street class constructor
     *
     * @param p_rightRotationAngel right rotation angel
     * @param p_leftRotationAngel left rotation angel
     * @param p_newDirectionAfterTurningRight new direction after turning right
     * @param p_newDirectionAfterTurningLeft new direction after turning left
     * @param p_newXAfterTurningRight new x after turning right
     * @param p_newXAfterTurningLeft new x after turning left
     * @param p_newYAfterTurningRight new y after turning right
     * @param p_newYAfterTurningLeft new y after turning left
     * @param p_oppositeStreet opposite street
     */
    public CStreet( final int p_rightRotationAngel, final int p_leftRotationAngel, final String p_newDirectionAfterTurningRight,
                    final String p_newDirectionAfterTurningLeft, final int p_newXAfterTurningRight, final int p_newXAfterTurningLeft,
                    final int p_newYAfterTurningRight, final int p_newYAfterTurningLeft, final String p_oppositeStreet )
    {
        this.m_rightRotationAngel = p_rightRotationAngel;
        this.m_leftRotationAngel = p_leftRotationAngel;
        this.m_newDirectionAfterTurningRight = p_newDirectionAfterTurningRight;
        this.m_newDirectionAfterTurningLeft = p_newDirectionAfterTurningLeft;
        this.m_newXAfterTurningRight = p_newXAfterTurningRight;
        this.m_newXAfterTurningLeft = p_newXAfterTurningLeft;
        this.m_newYAfterTurningRight = p_newYAfterTurningRight;
        this.m_newYAfterTurningLeft = p_newYAfterTurningLeft;
        this.m_oppositeStreet = p_oppositeStreet;
        this.m_vehiclesTrafficLight = new CVehiclesTrafficLight( 15 * 60, 2 * 60, 5 * 60, 3 * 60 );
        this.m_firstLane = new CLane( 9 );
        this.m_secondLane = new CLane( 9 );
    }

    /**
     * get vehicles traffic light
     *
     * @return vehicles traffic light
     */
    public CVehiclesTrafficLight getVehiclesTrafficLight()
    {
        return m_vehiclesTrafficLight;
    }

    /**
     * get first lane
     *
     * @return first lane
     */
    public CLane getFirstLane()
    {
        return m_firstLane;
    }

    /**
     * get second lane
     *
     * @return second lane
     */
    public CLane getSecondLane()
    {
        return m_secondLane;
    }

    /**
     * get right rotation angel
     *
     * @return right rotation angel
     */
    public int getRightRotationAngel()
    {
        return m_rightRotationAngel;
    }

    /**
     * get left rotation angel
     *
     * @return left rotation angel
     */
    public int getLeftRotationAngel()
    {
        return m_leftRotationAngel;
    }

    /**
     * get new direction after turning right
     *
     * @return new direction after turning right
     */
    public String getNewDirectionAfterTurningRight()
    {
        return m_newDirectionAfterTurningRight;
    }

    /**
     * get new direction after turning left
     *
     * @return new direction after turning left
     */
    public String getNewDirectionAfterTurningLeft()
    {
        return m_newDirectionAfterTurningLeft;
    }

    /**
     * get new x after turning right
     *
     * @return new x after turning right
     */
    public int getNewXAfterTurningRight()
    {
        return m_newXAfterTurningRight;
    }

    /**
     * get new x after turning left
     *
     * @return new x after turning left
     */
    public int getNewXAfterTurningLeft()
    {
        return m_newXAfterTurningLeft;
    }

    /**
     * get new y after turning right
     *
     * @return new y after turning right
     */
    public int getNewYAfterTurningRight()
    {
        return m_newYAfterTurningRight;
    }

    /**
     * get new y after turning left
     *
     * @return new y after turning left
     */
    public int getNewYAfterTurningLeft()
    {
        return m_newYAfterTurningLeft;
    }

    /**
     * get opposite street
     *
     * @return opposite street
     */
    public String getOppositeStreet()
    {
        return m_oppositeStreet;
    }

    /**
     * get distance between vehicle and start point in moving axis
     *
     * @param p_vehicle vehicle
     *
     * @return distance between vehicle and start point in moving axis
     */
    public int getDistanceBetweenVehicleAndStartPointInMovingAxis( final IBaseVehicle p_vehicle )
    {
        final int l_xPosition = (int) p_vehicle.getSprite().getX();
        final int l_yPosition = (int) p_vehicle.getSprite().getY();
        final boolean l_isTurned = p_vehicle.isTurned();
        final String l_currentStreet = p_vehicle.getCurrentStreet();
        final HashMap<String, CStreet> l_streets = CSimulation.getStreets();
        int l_distanceFromStartInMovingAxis = 0;

        final String l_mostBackStreet = l_isTurned ? l_streets.get( l_currentStreet ).getOppositeStreet() : l_currentStreet;
        if ( "west".equals( l_mostBackStreet ) )
        {
            l_distanceFromStartInMovingAxis = l_xPosition;
        }
        else if ( "south".equals( l_mostBackStreet ) )
        {
            l_distanceFromStartInMovingAxis = l_yPosition;
        }
        else if ( "east".equals( l_mostBackStreet ) )
        {
            l_distanceFromStartInMovingAxis = 1024 - l_xPosition;
        }
        else if ( "north".equals( l_mostBackStreet ) )
        {
            l_distanceFromStartInMovingAxis = 1024 - l_yPosition;
        }
        return l_distanceFromStartInMovingAxis;
    }

    @Override
    public Object call() throws Exception
    {
        m_vehiclesTrafficLight.call();
        return this;
    }
}
