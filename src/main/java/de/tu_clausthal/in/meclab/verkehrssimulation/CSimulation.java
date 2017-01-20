
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
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import de.tu_clausthal.in.meclab.verkehrssimulation.common.CCommon;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle.CCar;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight.EVehiclesTrafficLight;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual.CStreet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;

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
    private static final int VEHICLES_COUNT = 20;
    /**
     * traffic lights duration
     */
    private static final int TRAFFIC_LIGHT_DURATION = 5;
    /**
     * streets hashmap
     */
    private static HashMap<String, CStreet> s_streets;
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

    private Sprite m_trafficLightEastSprite;
    private Sprite m_trafficLightSouthSprite;
    private Sprite m_trafficLightWestSprite;
    private Sprite m_trafficLightNorthSprite;
    private Texture m_trafficLightEastGreenTexture;
    private Texture m_trafficLightEastRedTexture;
    private Texture m_trafficLightEastYellowTexture;
    private Texture m_trafficLightEastRedYellowTexture;
    private Texture m_trafficLightSouthGreenTexture;
    private Texture m_trafficLightSouthRedTexture;
    private Texture m_trafficLightSouthYellowTexture;
    private Texture m_trafficLightSouthRedYellowTexture;
    private Texture m_trafficLightWestGreenTexture;
    private Texture m_trafficLightWestRedTexture;
    private Texture m_trafficLightWestYellowTexture;
    private Texture m_trafficLightWestRedYellowTexture;
    private Texture m_trafficLightNorthGreenTexture;
    private Texture m_trafficLightNorthRedTexture;
    private Texture m_trafficLightNorthYellowTexture;
    private Texture m_trafficLightNorthRedYellowTexture;

    private HashMap<String, HashMap<EVehiclesTrafficLight, Texture>> m_trafficLightTexturesMap;

    public static HashMap<String, CStreet> getStreets()
    {
        return s_streets;
    }

    @Override
    public void create()
    {
        m_camera = new OrthographicCamera();
        m_camera.setToOrtho( false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        m_camera.update();

        m_roadsTiledMapRenderer = new OrthogonalTiledMapRenderer( new TmxMapLoader().load( "roads.tmx" ) );

        s_streets = new HashMap<>();
        s_streets.put( "west", new CStreet( -90, 90, "south", "north", 480,
            512, 488, 488, "east", EVehiclesTrafficLight.GREEN, 5 * 60, 24 * 60, 1 * 60, 5 * 60, 2 * 60 ) );
        s_streets.put( "south", new CStreet( 0, 180, "east", "west", 512,
            512, 488, 520, "north", EVehiclesTrafficLight.RED, 7 * 60, 24 * 60, 1 * 60, 5 * 60, 2 * 60 ) );
        s_streets.put( "east", new CStreet( 90, -90, "north", "south", 512,
            480, 520, 520, "west", EVehiclesTrafficLight.RED, 15 * 60, 24 * 60, 1 * 60, 5 * 60, 2 * 60 ) );
        s_streets.put( "north", new CStreet( 180, 0, "west", "east", 480,
            480, 520, 488, "south", EVehiclesTrafficLight.RED, 23 * 60, 24 * 60, 1 * 60, 5 * 60, 2 * 60 ) );

        m_startTime = System.currentTimeMillis();

        m_randomGenerator = new Random();

        m_trafficLightEastShapeRenderer = new ShapeRenderer();
        m_trafficLightSouthShapeRenderer = new ShapeRenderer();
        m_trafficLightWestShapeRenderer = new ShapeRenderer();
        m_trafficLightNorthShapeRenderer = new ShapeRenderer();

        m_spriteBatch = new SpriteBatch();
        m_carTexture = new Texture( Gdx.files.internal( "car.png" ) );

        m_trafficLightEastGreenTexture = new Texture( Gdx.files.internal( "trafficlights/tl_east_green.png" ) );
        m_trafficLightEastRedTexture = new Texture( Gdx.files.internal( "trafficlights/tl_east_red.png" ) );
        m_trafficLightEastYellowTexture = new Texture( Gdx.files.internal( "trafficlights/tl_east_yellow.png" ) );
        m_trafficLightEastRedYellowTexture = new Texture( Gdx.files.internal( "trafficlights/tl_east_redyellow.png" ) );
        m_trafficLightSouthGreenTexture = new Texture( Gdx.files.internal( "trafficlights/tl_south_green.png" ) );
        m_trafficLightSouthRedTexture = new Texture( Gdx.files.internal( "trafficlights/tl_south_red.png" ) );
        m_trafficLightSouthYellowTexture = new Texture( Gdx.files.internal( "trafficlights/tl_south_yellow.png" ) );
        m_trafficLightSouthRedYellowTexture = new Texture( Gdx.files.internal( "trafficlights/tl_south_redyellow.png" ) );
        m_trafficLightWestGreenTexture = new Texture( Gdx.files.internal( "trafficlights/tl_west_green.png" ) );
        m_trafficLightWestRedTexture = new Texture( Gdx.files.internal( "trafficlights/tl_west_red.png" ) );
        m_trafficLightWestYellowTexture = new Texture( Gdx.files.internal( "trafficlights/tl_west_yellow.png" ) );
        m_trafficLightWestRedYellowTexture = new Texture( Gdx.files.internal( "trafficlights/tl_west_redyellow.png" ) );
        m_trafficLightNorthGreenTexture = new Texture( Gdx.files.internal( "trafficlights/tl_north_green.png" ) );
        m_trafficLightNorthRedTexture = new Texture( Gdx.files.internal( "trafficlights/tl_north_red.png" ) );
        m_trafficLightNorthYellowTexture = new Texture( Gdx.files.internal( "trafficlights/tl_north_yellow.png" ) );
        m_trafficLightNorthRedYellowTexture = new Texture( Gdx.files.internal( "trafficlights/tl_north_redyellow.png" ) );

        m_trafficLightTexturesMap = new HashMap<>();
        m_trafficLightTexturesMap.put( "east", new HashMap<EVehiclesTrafficLight, Texture>()
            { {
                put( EVehiclesTrafficLight.GREEN, m_trafficLightEastGreenTexture );
                put( EVehiclesTrafficLight.RED, m_trafficLightEastRedTexture );
                put( EVehiclesTrafficLight.YELLOW, m_trafficLightEastYellowTexture );
                put( EVehiclesTrafficLight.REDYELLOW, m_trafficLightEastRedYellowTexture );
            } } );
        m_trafficLightTexturesMap.put( "north", new HashMap<EVehiclesTrafficLight, Texture>()
        { {
            put( EVehiclesTrafficLight.GREEN, m_trafficLightNorthGreenTexture );
            put( EVehiclesTrafficLight.RED, m_trafficLightNorthRedTexture );
            put( EVehiclesTrafficLight.YELLOW, m_trafficLightNorthYellowTexture );
            put( EVehiclesTrafficLight.REDYELLOW, m_trafficLightNorthRedYellowTexture );
        } } );
        m_trafficLightTexturesMap.put( "west", new HashMap<EVehiclesTrafficLight, Texture>()
        { {
            put( EVehiclesTrafficLight.GREEN, m_trafficLightWestGreenTexture );
            put( EVehiclesTrafficLight.RED, m_trafficLightWestRedTexture );
            put( EVehiclesTrafficLight.YELLOW, m_trafficLightWestYellowTexture );
            put( EVehiclesTrafficLight.REDYELLOW, m_trafficLightWestRedYellowTexture );
        } } );
        m_trafficLightTexturesMap.put( "south", new HashMap<EVehiclesTrafficLight, Texture>()
        { {
            put( EVehiclesTrafficLight.GREEN, m_trafficLightSouthGreenTexture );
            put( EVehiclesTrafficLight.RED, m_trafficLightSouthRedTexture );
            put( EVehiclesTrafficLight.YELLOW, m_trafficLightSouthYellowTexture );
            put( EVehiclesTrafficLight.REDYELLOW, m_trafficLightSouthRedYellowTexture );
        } } );

        m_trafficLightEastSprite = new Sprite( m_trafficLightEastRedTexture );
        m_trafficLightEastSprite.setPosition( 576, 552 );
        m_trafficLightSouthSprite = new Sprite( m_trafficLightSouthRedTexture );
        m_trafficLightSouthSprite.setPosition( 552, 404 );
        m_trafficLightWestSprite = new Sprite( m_trafficLightWestRedTexture );
        m_trafficLightWestSprite.setPosition( 407, 455 );
        m_trafficLightNorthSprite = new Sprite( m_trafficLightNorthRedTexture );
        m_trafficLightNorthSprite.setPosition( 457, 576 );

        m_cars = IntStream.range( 0, VEHICLES_COUNT )
            .parallel()
            .mapToObj( i -> CCommon.createRandomCar( m_carTexture ) )
            .toArray( CCar[]::new );
    }

    @Override
    public void dispose()
    {
        m_spriteBatch.dispose();
        m_carTexture.dispose();
        m_trafficLightEastGreenTexture.dispose();
        m_trafficLightEastRedTexture.dispose();
        m_trafficLightEastYellowTexture.dispose();
        m_trafficLightEastRedYellowTexture.dispose();
        m_trafficLightSouthGreenTexture.dispose();
        m_trafficLightSouthRedTexture.dispose();
        m_trafficLightSouthYellowTexture.dispose();
        m_trafficLightSouthRedYellowTexture.dispose();
        m_trafficLightWestGreenTexture.dispose();
        m_trafficLightWestRedTexture.dispose();
        m_trafficLightWestYellowTexture.dispose();
        m_trafficLightWestRedYellowTexture.dispose();
        m_trafficLightNorthGreenTexture.dispose();
        m_trafficLightNorthRedTexture.dispose();
        m_trafficLightNorthYellowTexture.dispose();
        m_trafficLightNorthRedYellowTexture.dispose();
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor( 1, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        m_roadsTiledMapRenderer.setView( m_camera );
        m_roadsTiledMapRenderer.render();

        try
        {
            renderTrafficLights();
        } catch ( final Exception e )
        {
            e.printStackTrace();
        }
        m_spriteBatch.begin();
        moveCars();

        //ToDo: we have problem by rendering when parallel
        Arrays.stream( m_cars )
            .forEach( i ->  i.getSprite().draw( m_spriteBatch ) );

        renderFPS();
        m_spriteBatch.end();
    }

    /**
     * render traffic lights
     */
    private void renderTrafficLights() throws Exception
    {
        s_streets.get( "north" ).call();
        s_streets.get( "south" ).call();
        s_streets.get( "west" ).call();
        s_streets.get( "east" ).call();

        m_trafficLightEastSprite.setTexture( m_trafficLightTexturesMap.get( "east" ).get( s_streets.get( "east" ).getVehiclesTrafficLight().getColor() ) );
        m_trafficLightNorthSprite.setTexture( m_trafficLightTexturesMap.get( "north" ).get( s_streets.get( "north" ).getVehiclesTrafficLight().getColor() ) );
        m_trafficLightWestSprite.setTexture( m_trafficLightTexturesMap.get( "west" ).get( s_streets.get( "west" ).getVehiclesTrafficLight().getColor() ) );
        m_trafficLightSouthSprite.setTexture( m_trafficLightTexturesMap.get( "south" ).get( s_streets.get( "south" ).getVehiclesTrafficLight().getColor() ) );

        m_spriteBatch.begin();
        m_trafficLightEastSprite.draw( m_spriteBatch );
        m_trafficLightSouthSprite.draw( m_spriteBatch );
        m_trafficLightWestSprite.draw( m_spriteBatch );
        m_trafficLightNorthSprite.draw( m_spriteBatch );
        m_spriteBatch.end();

    }

    /**
     * move cars
     */
    private void moveCars()
    {
        for ( int i = 0; i < m_cars.length; i++ )
        {
            final CCar l_car = m_cars[i];
            l_car.call();
            if ( l_car.isOut() )
            {
                m_cars[i] = CCommon.createRandomCar( m_carTexture );
            }
        }
    }

    /**
     * render frame per second
     */
    private void renderFPS()
    {
        final int l_fps = Gdx.graphics.getFramesPerSecond();
        final BitmapFont l_fpsFont = new BitmapFont();
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
