
package de.tu_clausthal.in.meclab.verkehrssimulation;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
            512, 488, 488, "east", EVehiclesTrafficLight.GREEN, 5 * 30, 24 * 30, 1 * 30, 5 * 30, 2 * 30 ) );
        s_streets.put( "south", new CStreet( 0, 180, "east", "west", 512,
            512, 488, 520, "north", EVehiclesTrafficLight.RED, 7 * 30, 24 * 30, 1 * 30, 5 * 30, 2 * 30 ) );
        s_streets.put( "east", new CStreet( 90, -90, "north", "south", 512,
            480, 520, 520, "west", EVehiclesTrafficLight.RED, 15 * 30, 24 * 30, 1 * 30, 5 * 30, 2 * 30 ) );
        s_streets.put( "north", new CStreet( 180, 0, "west", "east", 480,
            480, 520, 488, "south", EVehiclesTrafficLight.RED, 23 * 30, 24 * 30, 1 * 30, 5 * 30, 2 * 30 ) );

        m_startTime = System.currentTimeMillis();

        m_randomGenerator = new Random();

        m_trafficLightEastShapeRenderer = new ShapeRenderer();
        m_trafficLightSouthShapeRenderer = new ShapeRenderer();
        m_trafficLightWestShapeRenderer = new ShapeRenderer();
        m_trafficLightNorthShapeRenderer = new ShapeRenderer();

        m_spriteBatch = new SpriteBatch();
        m_carTexture = new Texture( Gdx.files.internal( "car.png" ) );

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

        m_trafficLightEastShapeRenderer.begin( ShapeRenderer.ShapeType.Filled );
        m_trafficLightEastShapeRenderer.setColor( s_streets.get( "east" ).getVehiclesTrafficLight().getColor() == EVehiclesTrafficLight.GREEN ? Color.GREEN : Color.RED );
        m_trafficLightEastShapeRenderer.circle( 576, 552, 8 );
        m_trafficLightEastShapeRenderer.end();

        m_trafficLightSouthShapeRenderer.begin( ShapeRenderer.ShapeType.Filled );
        m_trafficLightSouthShapeRenderer.setColor( s_streets.get( "south" ).getVehiclesTrafficLight().getColor() == EVehiclesTrafficLight.GREEN ? Color.GREEN : Color.RED );
        m_trafficLightSouthShapeRenderer.circle( 552, 448, 8 );
        m_trafficLightSouthShapeRenderer.end();

        m_trafficLightWestShapeRenderer.begin( ShapeRenderer.ShapeType.Filled );
        m_trafficLightWestShapeRenderer.setColor( s_streets.get( "west" ).getVehiclesTrafficLight().getColor() == EVehiclesTrafficLight.GREEN ? Color.GREEN : Color.RED );
        m_trafficLightWestShapeRenderer.circle( 448, 472, 8 );
        m_trafficLightWestShapeRenderer.end();

        m_trafficLightNorthShapeRenderer.begin( ShapeRenderer.ShapeType.Filled );
        m_trafficLightNorthShapeRenderer.setColor( s_streets.get( "north" ).getVehiclesTrafficLight().getColor() == EVehiclesTrafficLight.GREEN ? Color.GREEN : Color.RED );
        m_trafficLightNorthShapeRenderer.circle( 472, 576, 8 );
        m_trafficLightNorthShapeRenderer.end();
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
