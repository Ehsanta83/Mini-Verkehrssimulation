package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import cern.colt.matrix.DoubleMatrix1D;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.tu_clausthal.in.meclab.verkehrssimulation.ui.CScreen;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle.IBaseVehicle;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight.CVehiclesTrafficLight;

import java.util.HashMap;

/**
 * street class
 */
public class CStreet implements IVirtual
{
    /**
     * sprite
     */
    private final Sprite m_sprite;
    /**
     * vehicles traffic light
     */
    private final CVehiclesTrafficLight m_vehiclesTrafficLight;
    /**
     * first lane
     */
    private final CVehiclesWay m_firstWay;
    /**
     * second lane (opposite lane)
     */
    private final CVehiclesWay m_secondWay;
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
                    final int p_newYAfterTurningRight, final int p_newYAfterTurningLeft, final String p_oppositeStreet,
                    final CVehiclesTrafficLight p_vehiclesTrafficLight )
    {
        m_rightRotationAngel = p_rightRotationAngel;
        m_leftRotationAngel = p_leftRotationAngel;
        m_newDirectionAfterTurningRight = p_newDirectionAfterTurningRight;
        m_newDirectionAfterTurningLeft = p_newDirectionAfterTurningLeft;
        m_newXAfterTurningRight = p_newXAfterTurningRight;
        m_newXAfterTurningLeft = p_newXAfterTurningLeft;
        m_newYAfterTurningRight = p_newYAfterTurningRight;
        m_newYAfterTurningLeft = p_newYAfterTurningLeft;
        m_oppositeStreet = p_oppositeStreet;
        m_vehiclesTrafficLight = p_vehiclesTrafficLight;
        m_firstWay = new CVehiclesWay( 1, 9 );
        m_secondWay = new CVehiclesWay( 1, 9 );
        // TODO: we should change tilemap to sprite later
        m_sprite = null;
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
     * get first way
     *
     * @return first way
     */
    public CVehiclesWay getFirstWay()
    {
        return m_firstWay;
    }

    /**
     * get second way
     *
     * @return second way
     */
    public CVehiclesWay getSecondWay()
    {
        return m_secondWay;
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
     * get distance between vehicle and start point in moving axis
     *
     * @param p_vehicle vehicle
     *
     * @return distance between vehicle and start point in moving axis
     */
    public int getDistanceBetweenVehicleAndStartPointInMovingAxis( final IBaseVehicle p_vehicle )
    {
        /*
        final int l_xPosition = (int) p_vehicle.sprite().getX();
        final int l_yPosition = (int) p_vehicle.sprite().getY();
        final boolean l_isTurned = p_vehicle.isTurned();
        final String l_currentStreet = p_vehicle.getCurrentStreet();
        final HashMap<String, CStreet> l_streets = CScreen.getStreets();
        int l_distanceFromStartInMovingAxis = 0;

        final String l_mostBackStreet = l_isTurned ? l_streets.get( l_currentStreet ).m_oppositeStreet : l_currentStreet;
        switch ( l_mostBackStreet )
        {
            case "west":
                l_distanceFromStartInMovingAxis = l_xPosition;
                break;
            case "south":
                l_distanceFromStartInMovingAxis = l_yPosition;
                break;
            case "east":
                l_distanceFromStartInMovingAxis = 1024 - l_xPosition;
                break;
            case "north":
                l_distanceFromStartInMovingAxis = 1024 - l_yPosition;
                break;
        }
        return l_distanceFromStartInMovingAxis;*/
        return 0;
    }

    @Override
    public CStreet call() throws Exception
    {
        m_vehiclesTrafficLight.call();
        return this;
    }

    @Override
    public Sprite sprite()
    {
        return m_sprite;
    }

    @Override
    public void spriteinitialize( final float p_unit )
    {
    }

    @Override
    public DoubleMatrix1D position()
    {
        return null;
    }
}
