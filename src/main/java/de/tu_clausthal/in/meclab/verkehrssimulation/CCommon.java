package de.tu_clausthal.in.meclab.verkehrssimulation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.CConfigs;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle.CCar;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle.EVehicleTurning;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * common class
 */
public final class CCommon
{
    /**
     * package path
     */
    public static final String PACKAGEPATH = "de/tu_clausthal/in/meclab/verkehrssimulation/";
    /**
     * constructor
     */
    private CCommon()
    {
    }

    /**
     * returns a file from a resource e.g. Jar file
     *
     * @param p_file file
     * @return URL of file or null
     * @throws MalformedURLException on incorrect URL
     * @throws URISyntaxException on incorrect URI syntax
     */
    public static URL getResourceURL( final String p_file ) throws MalformedURLException, URISyntaxException
    {
        return CCommon.getResourceURL( new File( p_file ) );
    }

    /**
     * returns a file from a resource e.g. Jar file
     *
     * @param p_file file relative to the CMain
     * @return URL of file or null
     * @throws MalformedURLException on incorrect URL
     * @throws URISyntaxException on incorrect URI syntax
     */
    public static URL getResourceURL( final File p_file ) throws MalformedURLException, URISyntaxException
    {
        try
        {
            if ( p_file.exists() )
                return p_file.toURI().normalize().toURL();
            return CCommon.class.getClassLoader().getResource( p_file.toString().replace( File.separator, "/" ) ).toURI().normalize().toURL();
        }
        catch ( final Exception l_exception )
        {
            throw l_exception;
        }
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
