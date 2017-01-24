
package de.tu_clausthal.in.meclab.verkehrssimulation.ui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import de.tu_clausthal.in.meclab.verkehrssimulation.CCommon;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IVisualize;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle.CCar;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight.CVehiclesTrafficLight;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight.EVehiclesTrafficLight;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual.CStreet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

/**
 * simulation class
 *
 * @author Ehsan Tatasadi
 */
public class CScreen extends ApplicationAdapter
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
     * sprites list
     */
    private final List<? extends IVisualize> m_sprites;
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
     * array of all cars in simulation
     */
    private CCar[] m_cars;


    private CVehiclesTrafficLight m_eastVehiclesTrafficLight;
    private CVehiclesTrafficLight m_northVehiclesTrafficLight;
    private CVehiclesTrafficLight m_westVehiclesTrafficLight;
    private CVehiclesTrafficLight m_southVehiclesTrafficLight;



    /**
     * constructor
     *
     * @param p_sprites list of sprites
     */
    public CScreen( final List<? extends IVisualize> p_sprites )
    {
        m_sprites = p_sprites;
    }

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

        m_westVehiclesTrafficLight = new CVehiclesTrafficLight( EVehiclesTrafficLight.GREEN, 5 * 60, 24 * 60, 1 * 60, 5 * 60, 2 * 60 );
        m_southVehiclesTrafficLight = new CVehiclesTrafficLight( EVehiclesTrafficLight.RED, 7 * 60, 24 * 60, 1 * 60, 5 * 60, 2 * 60 );
        m_eastVehiclesTrafficLight = new CVehiclesTrafficLight( EVehiclesTrafficLight.RED, 15 * 60, 24 * 60, 1 * 60, 5 * 60, 2 * 60 );
        m_northVehiclesTrafficLight = new CVehiclesTrafficLight( EVehiclesTrafficLight.RED, 23 * 60, 24 * 60, 1 * 60, 5 * 60, 2 * 60 );

        s_streets = new HashMap<>();
        s_streets.put( "west", new CStreet( -90, 90, "south", "north", 480,
            512, 488, 488, "east", m_westVehiclesTrafficLight ) );
        s_streets.put( "south", new CStreet( 0, 180, "east", "west", 512,
            512, 488, 520, "north", m_southVehiclesTrafficLight ) );
        s_streets.put( "east", new CStreet( 90, -90, "north", "south", 512,
            480, 520, 520, "west", m_eastVehiclesTrafficLight ) );
        s_streets.put( "north", new CStreet( 180, 0, "west", "east", 480,
            480, 520, 488, "south", m_northVehiclesTrafficLight ) );


        m_spriteBatch = new SpriteBatch();
        m_carTexture = new Texture( Gdx.files.internal( "car.png" ) );

        m_eastVehiclesTrafficLight.spriteInitialize( 590, 547, 90 );
        m_southVehiclesTrafficLight.spriteInitialize( 560, 405, 0 );
        m_westVehiclesTrafficLight.spriteInitialize( 420, 435, 270 );
        m_northVehiclesTrafficLight.spriteInitialize( 449, 576, 180 );

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
        EVehiclesTrafficLight.GREEN.getTexture().dispose();
        EVehiclesTrafficLight.RED.getTexture().dispose();
        EVehiclesTrafficLight.REDYELLOW.getTexture().dispose();
        EVehiclesTrafficLight.YELLOW.getTexture().dispose();
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
            .forEach( i ->  i.sprite().draw( m_spriteBatch ) );

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

        m_spriteBatch.begin();
        m_westVehiclesTrafficLight.sprite().draw( m_spriteBatch );
        m_southVehiclesTrafficLight.sprite().draw( m_spriteBatch );
        m_eastVehiclesTrafficLight.sprite().draw( m_spriteBatch );
        m_northVehiclesTrafficLight.sprite().draw( m_spriteBatch );
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
