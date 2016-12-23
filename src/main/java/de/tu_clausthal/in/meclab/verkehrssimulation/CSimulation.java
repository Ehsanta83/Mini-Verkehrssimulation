package de.tu_clausthal.in.meclab.verkehrssimulation;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle.CCar;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle.EVehicleTurning;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight.ETrafficLightStatus;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual.CStreet;

import java.util.HashMap;
import java.util.Random;

/**
 * simulation class
 *
 * @author Ehsan Tatasadi
 */
public class CSimulation extends ApplicationAdapter
{
    /**
     * count of vehicles in simulation
     */
    private final int m_countOfVehicles = 20;
    /**
     * traffic lights duration
     */
    private final int m_trafficLightDuration = 5;
    /**
     * maximum velocity of a vehicle
     */
    private final int m_maxVelocity = 3;
    /**
     * roads tilemap
     */
    private TiledMap m_roadsTiledMap;
    /**
     * camera
     */
    private OrthographicCamera m_camera;
    /**
     * tilemap renderer
     */
    private TiledMapRenderer m_roadsTiledMapRenderer;
    /**
     * sprite batch
     */
    private SpriteBatch m_spriteBatch;
    /**
     * car texture
     */
    private Texture m_carTexture;
    /**
     * streets hashmap
     */
    private HashMap<String, CStreet> m_streets;
    /**
     * east street traffic light shape renderer
     */
    private ShapeRenderer m_trafficLightEastShapeRenderer;
    /**
     * south street traffic light shape renderer
     */
    private ShapeRenderer m_trafficLightSouthShapeRenderer;
    /**
     * west street traffic light shape renderer
     */
    private ShapeRenderer m_trafficLightWestShapeRenderer;
    /**
     * north street traffic light shape renderer
     */
    private ShapeRenderer m_trafficLightNorthShapeRenderer;
    /**
     * start time in millisecond
     */
    private long m_startTime;
    /**
     * array of all cars in simulation
     */
    private CCar[] m_cars;
    /**
     * random generator
     */
    private Random m_randomGenerator;

    @Override
    public void create( )
    {
        final float l_width = Gdx.graphics.getWidth( );
        final float l_height = Gdx.graphics.getHeight( );

        m_camera = new OrthographicCamera( );
        m_camera.setToOrtho( false, l_width, l_height );
        m_camera.update( );

        m_roadsTiledMap = new TmxMapLoader().load( "roads.tmx" );
        m_roadsTiledMapRenderer = new OrthogonalTiledMapRenderer( m_roadsTiledMap );

        final CStreet l_streetEast = new CStreet( 90, -90, "north", "south", 512, 480, 520, 520, "west" );
        final CStreet l_streetSouth = new CStreet( 0, 180, "east", "west", 512, 512, 488, 520, "north" );
        final CStreet l_streetWest = new CStreet( -90, 90, "south", "north", 480, 512, 488, 488, "east" );
        final CStreet l_streetNorth = new CStreet( 180, 0, "west", "east", 480, 480, 520, 488, "south" );
        m_streets = new HashMap<String, CStreet>( );
        m_streets.put( "east", l_streetEast );
        m_streets.put( "south", l_streetSouth );
        m_streets.put( "west", l_streetWest );
        m_streets.put( "north", l_streetNorth );

        m_startTime = System.currentTimeMillis( );

        m_randomGenerator = new Random( );

        m_trafficLightEastShapeRenderer = new ShapeRenderer( );
        m_trafficLightSouthShapeRenderer = new ShapeRenderer( );
        m_trafficLightWestShapeRenderer = new ShapeRenderer( );
        m_trafficLightNorthShapeRenderer = new ShapeRenderer( );

        m_spriteBatch = new SpriteBatch( );
        m_carTexture = new Texture( Gdx.files.internal( "car.png" ) );
        m_cars = new CCar[m_countOfVehicles];
        for ( int i = 0; i < m_countOfVehicles; i++ )
        {
            m_cars[i] = createRandomCar();
        }
        //threads = new Thread[m_countOfVehicles];


    }

    @Override
    public void dispose( )
    {
        m_spriteBatch.dispose( );
        m_carTexture.dispose( );
    }

    @Override
    public void render( )
    {
        Gdx.gl.glClearColor( 1, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        m_roadsTiledMapRenderer.setView( m_camera );
        m_roadsTiledMapRenderer.render( );

        renderTrafficLights( );
        m_spriteBatch.begin( );
        moveCars( );

        for ( final CCar l_car: m_cars )
        {
            l_car.getSprite().draw( m_spriteBatch );
        }
        renderFPS( );
        m_spriteBatch.end( );
    }

    /**
     * turn all traffic lights to red
     */
    private void turnAllTrafficLightsToRed( )
    {
        m_streets.get( "west" ).turnTrafficLightToRed( );
        m_streets.get( "south" ).turnTrafficLightToRed( );
        m_streets.get( "north" ).turnTrafficLightToRed( );
        m_streets.get( "east" ).turnTrafficLightToRed( );
    }

    /**
     * create a random car. start street, next street and velocity will be random.
     *
     * @return car
     */
    private CCar createRandomCar( )
    {
        final Sprite l_carSprite = new Sprite( m_carTexture );
        final int l_velocity = m_randomGenerator.nextInt( m_maxVelocity ) + 1;
        String l_startStreet = "";
        String l_currentDrivingDirection = "";
        int l_random = m_randomGenerator.nextInt( 4 );
        switch ( l_random )
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

        l_random = m_randomGenerator.nextInt( 3 );
        final EVehicleTurning l_turning;
        switch ( l_random )
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

        final CCar l_car = new CCar( l_carSprite, l_velocity, l_startStreet, l_currentDrivingDirection, l_turning );
        return l_car;
    }

    /**
     * apply nagel-schreckenberg model to the velocity of a car
     *
     * @param p_velocity velocity
     * @param p_blockIndex block index
     * @param p_nextOccupiedBlockIndex next occupied block index
     * @return new velocity
     */
    private int applyNagelSchreckenbergModel( int p_velocity, int p_blockIndex, int p_nextOccupiedBlockIndex )
    {
        int l_newVelocity = p_velocity;
        //1. rule in Nagel-Schreckenberg-Modell
        if ( l_newVelocity < m_maxVelocity )
            l_newVelocity++;
        //2. rule in Nagel-Schreckenberg-Modell
        if ( p_nextOccupiedBlockIndex != -1 )
        {
            if ( p_blockIndex == -1 && p_nextOccupiedBlockIndex <= m_maxVelocity )
            {
                l_newVelocity = p_nextOccupiedBlockIndex;
            }
            else
            {
                if ( l_newVelocity > p_nextOccupiedBlockIndex - p_blockIndex - 1 )
                {
                    l_newVelocity = p_nextOccupiedBlockIndex - p_blockIndex - 1;
                }
            }
        }
        //3. rule in Nagel-Schreckenberg-Modell
        // get a random number between 1,2,3
        final int l_random = m_randomGenerator.nextInt( 3 );
        if ( l_random == 0 && l_newVelocity >= 1 )
            l_newVelocity--;
        return l_newVelocity;
    }

    /**
     * apply traffic light to the cars
     *
     * @param p_street street
     * @param p_velocity velocity
     * @param p_blockIndex block index
     * @return new velocity
     */
    private int applyTrafficLightToCars( final CStreet p_street, final int p_velocity, final int p_blockIndex )
    {
        int l_newVelocity = p_velocity;
        if ( p_blockIndex == 6 && p_velocity > 3 )
            l_newVelocity = 3;
        if ( p_blockIndex == 7 && p_velocity > 2 )
            l_newVelocity = 1;
        if ( p_blockIndex == 8 && p_street.getVehiclesTrafficLight().getStatus() == ETrafficLightStatus.RED )
            l_newVelocity = 0;
        return l_newVelocity;
    }

    /**
     * render traffic lights
     */
    private void renderTrafficLights( )
    {
        final long l_elapsedTime = ( System.currentTimeMillis() - m_startTime ) / 1000;
        final double l_mod = l_elapsedTime % ( m_trafficLightDuration * 4 );
        if ( l_mod < m_trafficLightDuration )
        {
            turnAllTrafficLightsToRed( );
            m_streets.get( "east" ).turnTrafficLightToGreen( );
        }
        else if ( l_mod >= m_trafficLightDuration && l_mod < 2 * m_trafficLightDuration )
        {
            turnAllTrafficLightsToRed( );
            m_streets.get( "south" ).turnTrafficLightToGreen( );
        }
        else if ( l_mod >= 2 * m_trafficLightDuration && l_mod < 3 * m_trafficLightDuration )
        {
            turnAllTrafficLightsToRed( );
            m_streets.get( "west" ).turnTrafficLightToGreen( );
        }
        else
        {
            turnAllTrafficLightsToRed( );
            m_streets.get( "north" ).turnTrafficLightToGreen( );
        }

        m_trafficLightEastShapeRenderer.begin( ShapeRenderer.ShapeType.Filled );
        m_trafficLightEastShapeRenderer.setColor( m_streets.get( "east" ).getVehiclesTrafficLight( ).getStatus() == ETrafficLightStatus.GREEN ? Color.GREEN : Color.RED );
        m_trafficLightEastShapeRenderer.circle( 576, 552, 8 );
        m_trafficLightEastShapeRenderer.end();

        m_trafficLightSouthShapeRenderer.begin( ShapeRenderer.ShapeType.Filled );
        m_trafficLightSouthShapeRenderer.setColor( m_streets.get( "south" ).getVehiclesTrafficLight( ).getStatus( ) == ETrafficLightStatus.GREEN ? Color.GREEN : Color.RED );
        m_trafficLightSouthShapeRenderer.circle( 552, 448, 8 );
        m_trafficLightSouthShapeRenderer.end();

        m_trafficLightWestShapeRenderer.begin( ShapeRenderer.ShapeType.Filled );
        m_trafficLightWestShapeRenderer.setColor( m_streets.get( "west" ).getVehiclesTrafficLight( ).getStatus( ) == ETrafficLightStatus.GREEN ? Color.GREEN : Color.RED );
        m_trafficLightWestShapeRenderer.circle( 448, 472, 8 );
        m_trafficLightWestShapeRenderer.end( );

        m_trafficLightNorthShapeRenderer.begin( ShapeRenderer.ShapeType.Filled );
        m_trafficLightNorthShapeRenderer.setColor( m_streets.get( "north" ).getVehiclesTrafficLight( ).getStatus( ) == ETrafficLightStatus.GREEN ? Color.GREEN : Color.RED );
        m_trafficLightNorthShapeRenderer.circle( 472, 576, 8 );
        m_trafficLightNorthShapeRenderer.end( );
    }

    /**
     * move cars
     */
    private void moveCars( )
    {
        for ( int i = 0; i < m_cars.length; i++ )
        {
            final CCar l_car = m_cars[i];
            l_car.setIndex( i );
            new Thread( new Runnable()
            {
                @Override
                public void run( )
                {
                    int l_velocity = l_car.getVelocity();
                    int l_xPosition = (int) l_car.getSprite().getX();
                    int l_yPosition = (int) l_car.getSprite().getY();
                    int l_blockIndex = l_car.getBlockIndex();
                    final EVehicleTurning l_turning = l_car.getTurning();
                    final String l_currentStreet = l_car.getCurrentStreet();

                    int l_nextOccupiedBlockIndex = -1;
                    int l_distanceFromStartInMovingAxis = 0;

                    final String l_mostBackStreet = l_car.isTurned( ) ? m_streets.get( l_currentStreet ).getOppositeStreet( ) : l_currentStreet;
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

                    if ( l_distanceFromStartInMovingAxis < 432 )
                    {
                        final int l_newBlockIndex = (int) l_distanceFromStartInMovingAxis / 48;
                        if ( l_newBlockIndex > l_blockIndex && !m_streets.get( l_currentStreet ).getFirstLane( ).isBlockOccupied( l_newBlockIndex ) )
                        {
                            if ( l_blockIndex > -1 )
                            {
                                m_streets.get( l_currentStreet ).getFirstLane( ).emptyBlock( l_blockIndex );
                            }
                            m_streets.get( l_currentStreet ).getFirstLane( ).occupyBlock( l_newBlockIndex );
                            l_blockIndex = l_newBlockIndex;
                        }
                        l_nextOccupiedBlockIndex = m_streets.get( l_currentStreet ).getFirstLane( ).getNextOccupiedBlockIndexFromIndex( l_blockIndex );
                        l_velocity = applyNagelSchreckenbergModel( l_velocity, l_blockIndex, l_nextOccupiedBlockIndex );
                        l_velocity = applyTrafficLightToCars( m_streets.get( l_currentStreet ), l_velocity, l_blockIndex );
                    }
                    else if ( l_distanceFromStartInMovingAxis >= 432 && l_distanceFromStartInMovingAxis < 480 )
                    {
                        if ( l_blockIndex > -1 )
                        {
                            m_streets.get( l_currentStreet ).getFirstLane().emptyBlock( l_blockIndex );
                            l_blockIndex = -1;
                        }
                    }
                    else if ( l_distanceFromStartInMovingAxis >= 480 && l_distanceFromStartInMovingAxis < 512 )
                    {
                        if ( l_turning.equals( EVehicleTurning.RIGHT ) )
                        {
                            l_car.getSprite( ).setRotation( m_streets.get( l_currentStreet ).getRightRotationAngel( ) );
                            l_xPosition = m_streets.get( l_currentStreet ).getNewXAfterTurningRight( );
                            l_yPosition = m_streets.get( l_currentStreet ).getNewYAfterTurningRight( );
                            final String l_newStreet = m_streets.get( l_currentStreet ).getNewDirectionAfterTurningRight( );
                            l_car.setCurrentStreet( l_newStreet );
                            l_car.setCurrentDrivingDirection( l_newStreet );
                            l_car.setTurning( EVehicleTurning.NONE );
                            l_car.setTurned( true );
                        }
                    }
                    else if ( l_distanceFromStartInMovingAxis >= 512 && l_distanceFromStartInMovingAxis < 592 )
                    {
                        if ( l_turning.equals( EVehicleTurning.LEFT ) )
                        {
                            l_car.getSprite( ).setRotation( m_streets.get( l_currentStreet ).getLeftRotationAngel( ) );
                            l_xPosition = m_streets.get( l_currentStreet ).getNewXAfterTurningLeft( );
                            l_yPosition = m_streets.get( l_currentStreet ).getNewYAfterTurningLeft( );
                            final String l_newStreet = m_streets.get( l_currentStreet ).getNewDirectionAfterTurningLeft( );
                            l_car.setCurrentStreet( l_newStreet );
                            l_car.setCurrentDrivingDirection( l_newStreet );
                            l_car.setTurning( EVehicleTurning.NONE );
                            l_car.setTurned( true );
                        }
                    }
                    else if ( l_distanceFromStartInMovingAxis >= 592 && l_distanceFromStartInMovingAxis < 1024 )
                    {
                        final int l_newBlockIndex = ( l_distanceFromStartInMovingAxis - 592 ) / 48;
                        if ( l_newBlockIndex > l_blockIndex && !m_streets.get( l_currentStreet ).getSecondLane( ).isBlockOccupied( l_newBlockIndex ) )
                        {
                            if ( l_blockIndex > -1 )
                            {
                                m_streets.get( l_currentStreet ).getSecondLane( ).emptyBlock( l_blockIndex );
                            }
                            m_streets.get( l_currentStreet ).getSecondLane( ).occupyBlock( l_newBlockIndex );
                            l_blockIndex = l_newBlockIndex;
                        }
                        l_nextOccupiedBlockIndex = m_streets.get( l_currentStreet ).getSecondLane( ).getNextOccupiedBlockIndexFromIndex( l_blockIndex );
                        l_velocity = applyNagelSchreckenbergModel( l_velocity, l_blockIndex, l_nextOccupiedBlockIndex );
                    }
                    else if ( l_distanceFromStartInMovingAxis >= 1024 )
                    {
                        if ( l_blockIndex > -1 )
                        {
                            m_streets.get( l_currentStreet ).getSecondLane( ).emptyBlock( l_blockIndex );
                        }
                        m_cars[l_car.getIndex( )] = createRandomCar( );
                    }

                    l_car.setBlockIndex( l_blockIndex );
                    l_car.setVelocity( l_velocity );
                    l_car.getSprite( ).setPosition( l_xPosition, l_yPosition );

                    if ( l_velocity > 0 )
                    {
                        l_car.move();
                    }
                }
            } ).start( );
        }
    }

    /**
     * render frame per second
     */
    private void renderFPS( )
    {
        final int l_fps = Gdx.graphics.getFramesPerSecond( );
        final BitmapFont l_fpsFont = new BitmapFont( );
        if ( l_fps >= 45 )
        {
            //green
            l_fpsFont.setColor( 0, 1, 0, 1 );
        }
        else if ( l_fps >= 30 )
        {
            //yellow
            l_fpsFont.setColor( 1, 1, 0, 1 );
        }
        else
        {
            //red
            l_fpsFont.setColor( 1, 0, 0, 1 );
        }
        l_fpsFont.draw( m_spriteBatch, "FPS: " + l_fps, 19, 1005 );
        //white
        l_fpsFont.setColor( 1, 1, 1, 1 );
    }
}
