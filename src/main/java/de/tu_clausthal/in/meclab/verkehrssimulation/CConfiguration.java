package de.tu_clausthal.in.meclab.verkehrssimulation;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.CEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle.IBaseVehicle;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight.CVehiclesTrafficLight;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight.EVehiclesTrafficLight;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight.IBaseTrafficLight;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.logging.LogManager;

/**
 * configuration and initialization of all simulation objects
 */
public final class CConfiguration
{
    /**
     * singleton instance
     */
    public static final CConfiguration INSTANCE = new CConfiguration();
    /**
     * window height
     */
    private int m_windowheight;
    /**
     * window width
     */
    private int m_windowwidth;
    /**
     * vehicles count in the simulation
     */
    private int m_vehiclesCount;
    /**
     * environment
     */
    private IEnvironment m_environment;
    /**
     * simulation steps
     */
    private int m_simulationstep;
    /**
     * thread-sleep time in milliseconds
     */
    private int m_threadsleeptime;
    /**
     * boolean flag to show stack trace
     */
    private boolean m_stacktrace;
    /**
     * simulation traffic lights
     */
    private List<IBaseTrafficLight> m_trafficlights;
    /**
     * simulation vehicles
     */
    private List<IBaseVehicle> m_vehicles;

    /**
     * constructor
     */
    private CConfiguration()
    {
    }

    /**
     * loads the configuration from a file
     *
     * @param p_input YAML configuration file
     * @return instance
     *
     * @throws IOException on io errors
     * @throws URISyntaxException on URI syntax error
     */
    @SuppressWarnings( "unchecked" )
    public final CConfiguration load( final String p_input ) throws IOException, URISyntaxException
    {
        final URL l_path = CCommon.getResourceURL( p_input );

        // read configuration
        final Map<String, Object> l_data = (Map<String, Object>) new Yaml().load( l_path.openStream() );

        // get initial values
        m_stacktrace = (boolean) l_data.getOrDefault( "stacktrace", false );
        m_threadsleeptime = (int) l_data.getOrDefault( "threadsleeptime", 0 );
        m_simulationstep = (int) l_data.getOrDefault( "steps", Integer.MAX_VALUE );
        if ( !(boolean) l_data.getOrDefault( "logging", false ) )
            LogManager.getLogManager().reset();

        m_windowwidth = ( (Map<String, Integer>) l_data.getOrDefault( "window", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "width", 1024 );
        m_windowheight = ( (Map<String, Integer>) l_data.getOrDefault( "window", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "height", 1024 );
        m_vehiclesCount = ( (Map<String, Integer>) l_data.getOrDefault( "counts", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "vehicles", 1024 );

        // create static objects - static object are needed by the environment
        final List<IBaseTrafficLight> l_trafficlights = new LinkedList<>();
        this.createTrafficLights( (List<Map<String, Object>>) l_data.getOrDefault( "trafficlights", Collections.<Map<String, Object>>emptyList() ), l_trafficlights );
        m_trafficlights = Collections.unmodifiableList( l_trafficlights );

        // create static objects - static object are needed by the environment
        final List<IBaseVehicle> l_vehicles = new LinkedList<>();

        m_vehicles = Collections.unmodifiableList( l_vehicles );

        // create environment - static items must be exists
        m_environment = new CEnvironment(
            (Integer) ( (Map<String, Object>) l_data.getOrDefault( "environment", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "rows", -1 ),
            (Integer) ( (Map<String, Object>) l_data.getOrDefault( "environment", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "columns", -1 ),
            (Integer) ( (Map<String, Object>) l_data.getOrDefault( "environment", Collections.<String, Integer>emptyMap() ) )
                .getOrDefault( "cellsize", -1 )
        );

        return this;
    }

    /**
     * return window height
     *
     * @return height
     */
    public final int windowHeight()
    {
        return m_windowheight;
    }

    /**
     * return window width
     *
     * @return width
     */
    public final int windowWidth()
    {
        return m_windowwidth;
    }

    /**
     * return vehicles count in the simulation
     *
     * @return vehicles count
     */
    public final int vehiclesCount()
    {
        return m_vehiclesCount;
    }

    /**
     * returns the environment
     *
     * @return environment
     */
    public final IEnvironment environment()
    {
        return m_environment;
    }

    /**
     * number of simulation steps
     *
     * @return steps
     */
    final int simulationsteps()
    {
        return m_simulationstep;
    }

    /**
     * returns the thread-sleep time
     *
     * @return sleep time
     */
    final int threadsleeptime()
    {
        return m_threadsleeptime;
    }

    /**
     * returns the stacktrace visiblity
     *
     * @return stacktrace visibility
     */
    final boolean stackstrace()
    {
        return m_stacktrace;
    }

    /**
     * return all static elements
     *
     * @return object list
     */
    final List<IBaseTrafficLight> trafficlights()
    {
        return m_trafficlights;
    }

    /**
     * return all vehicles
     *
     * @return object list
     */
    final List<IBaseVehicle> vehicles()
    {
        return m_vehicles;
    }

    private void createTrafficLights( final List<Map<String, Object>> p_trafficlightsConfiguration, final List<IBaseTrafficLight> p_trafficlights)
    {
        p_trafficlightsConfiguration
            .stream()
            .map( i -> (Map<String, Object>) i.get( "vehicles" ) )
            .filter( Objects::nonNull )
            .map( i -> new CVehiclesTrafficLight(
                (List<Integer>) i.get( "position" ),
                (int) i.get( "rotation" ),
                i.get( "startcolor" ).equals( "green" ) ? EVehiclesTrafficLight.GREEN : EVehiclesTrafficLight.RED,
                (int) i.get( "startcolorduration" ),
                ( (List<Integer>) i.get( "duration" ) ).stream().mapToInt( j -> j ).toArray()

            ) )
            .forEach( p_trafficlights::add );
    }
}
