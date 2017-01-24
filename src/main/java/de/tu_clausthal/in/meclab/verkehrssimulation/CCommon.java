package de.tu_clausthal.in.meclab.verkehrssimulation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.CConfigs;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle.CCar;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle.EVehicleTurning;

import java.util.*;

/**
 * common class
 *
 * @author Ehsan Tatasadi
 */
public final class CCommon
{
    /**
     * constructor
     */
    private CCommon()
    {
    }
    /**
     * create a random car. start street, next street and velocity will be random.
     *
     * @return car
     */
    public static final CCar createRandomCar( final Texture p_carTexture )
    {
        final Random l_randomGenerator = new Random();
        final Sprite l_carSprite = new Sprite( p_carTexture );
        final String l_startStreet;
        final String l_currentDrivingDirection;
        switch ( l_randomGenerator.nextInt( 4 ) )
        {
            case 0:
                l_carSprite.setPosition( -32, 488 );
                l_startStreet = "west";
                l_currentDrivingDirection = "east";
                break;
            case 1:
                l_carSprite.setPosition( 480, 1032 );
                l_carSprite.setRotation( -90 );
                l_startStreet = "north";
                l_currentDrivingDirection = "south";
                break;
            case 2:
                l_carSprite.setPosition( 1024, 520 );
                l_carSprite.setRotation( 180 );
                l_startStreet = "east";
                l_currentDrivingDirection = "west";
                break;
            default:
                l_carSprite.setPosition( 512, -24 );
                l_carSprite.setRotation( 90 );
                l_startStreet = "south";
                l_currentDrivingDirection = "north";
                break;
        }

        final EVehicleTurning l_turning;
        switch ( l_randomGenerator.nextInt( 3 ) )
        {
            case 1:
                l_turning = EVehicleTurning.LEFT;
                break;
            case 2:
                l_turning = EVehicleTurning.RIGHT;
                break;
            default:
                l_turning = EVehicleTurning.NONE;
                break;
        }
        return new CCar( l_carSprite, l_randomGenerator.nextInt( CConfigs.MAX_VELOCITY_OF_VEHICLES ) + 1, l_startStreet, l_currentDrivingDirection, l_turning );
    }
}
