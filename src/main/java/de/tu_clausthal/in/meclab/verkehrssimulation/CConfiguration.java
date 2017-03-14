package de.tu_clausthal.in.meclab.verkehrssimulation;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.algorithm.routing.ERoutingFactory;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.CEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IAgent;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle.CVehicle;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle.CVehicleGenerator;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight.CVehiclesTrafficLight;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight.EVehiclesTrafficLight;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight.IBaseTrafficLight;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual.CLane;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual.CVehiclesWay;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual.IBaseWay;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual.ILane;
import org.apache.commons.io.FilenameUtils;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.action.IBaseAction;
import org.lightjason.agentspeak.common.CPath;
import org.lightjason.agentspeak.common.IPath;
import org.lightjason.agentspeak.generator.IAgentGenerator;
import org.lightjason.agentspeak.language.ITerm;
import org.lightjason.agentspeak.language.execution.IContext;
import org.lightjason.agentspeak.language.execution.fuzzy.CFuzzyValue;
import org.lightjason.agentspeak.language.execution.fuzzy.IFuzzyValue;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;
import java.util.logging.LogManager;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     * configuration path
     */
    private String m_configurationpath;
    /**
     * window height
     */
    private int m_windowheight;
    /**
     * window width
     */
    private int m_windowwidth;
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
     * simulation ways
     */
    private List<IBaseWay> m_ways;
    /**
     * simulation lanes
     */
    private List<ILane> m_lanes;
    /**
     * simulation agents
     */
    private List<IAgent> m_agents;

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
        m_configurationpath = FilenameUtils.getPath( l_path.toString() );

        // get initial values
        m_stacktrace = (boolean) l_data.getOrDefault( "stacktrace", false );
        m_threadsleeptime = (int) l_data.getOrDefault( "threadsleeptime", 0 );
        m_simulationstep = (int) l_data.getOrDefault( "steps", Integer.MAX_VALUE );
        if ( !(boolean) l_data.getOrDefault( "logging", false ) )
            LogManager.getLogManager().reset();

        m_windowwidth = ( (Map<String, Integer>) l_data.getOrDefault( "window", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "width", 1024 );
        m_windowheight = ( (Map<String, Integer>) l_data.getOrDefault( "window", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "height", 1024 );

        // create traffic lights
        final List<IBaseTrafficLight> l_trafficlights = new LinkedList<>();
        this.createTrafficLights( (List<Map<String, Object>>) l_data.getOrDefault( "trafficlights", Collections.<Map<String, Object>>emptyList() ), l_trafficlights );
        m_trafficlights = Collections.unmodifiableList( l_trafficlights );

        // create ways
        final List<IBaseWay> l_ways = new LinkedList<>();
        this.createWays( (List<Map<String, Object>>) l_data.getOrDefault( "ways", Collections.<Map<String, Object>>emptyList() ), l_ways );
        m_ways = Collections.unmodifiableList( l_ways );

        // create lanes
        final List<ILane> l_lanes = new LinkedList<>();
        this.createLanes( (List<Map<String, Object>>) l_data.getOrDefault( "lane", Collections.<Map<String, Object>>emptyList() ), l_lanes );
        m_lanes = Collections.unmodifiableList( l_lanes );

        // create environment - static items must be exists
        m_environment = new CEnvironment(
            (Integer) ( (Map<String, Object>) l_data.getOrDefault( "environment", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "rows", -1 ),
            (Integer) ( (Map<String, Object>) l_data.getOrDefault( "environment", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "columns", -1 ),
            (Integer) ( (Map<String, Object>) l_data.getOrDefault( "environment", Collections.<String, Integer>emptyMap() ) )
                .getOrDefault( "cellsize", -1 ),
            ERoutingFactory.valueOf( ( (String) ( (Map<String, Object>) l_data.getOrDefault( "environment", Collections.<String, Integer>emptyMap() ) )
                .getOrDefault( "routing", "" ) ).trim().toUpperCase() ).get(),
            m_lanes
        );

        // create agents
        final List<IAgent> l_agents = new LinkedList<>();
        this.createAgents(
            (List<Map<String, Object>>) l_data.getOrDefault( "agents", Collections.<String, Object>emptyMap() ),
            l_agents,
            (boolean) l_data.getOrDefault( "agentprint", true )
        );
        m_agents = Collections.unmodifiableList( l_agents );

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
     * return all traffic lights
     *
     * @return list of traffic lights
     */
    final List<IBaseTrafficLight> trafficlights()
    {
        return m_trafficlights;
    }

    /**
     * return all ways
     *
     * @return list of ways
     */
    final List<IBaseWay> ways()
    {
        return m_ways;
    }

    /**
     * return all lanes
     *
     * @return list of lanes
     */
    final List<ILane> lanes()
    {
        return m_lanes;
    }
    /**
     * return all agents
     *
     * @return list of agents
     */
    final List<IAgent> agents()
    {
        return m_agents;
    }

    /**
     * create traffic lights
     *
     * @param p_trafficlightsConfiguration traffic light configuration
     * @param p_trafficlights list of traffic lights
     */
    private void createTrafficLights( final List<Map<String, Object>> p_trafficlightsConfiguration, final List<IBaseTrafficLight> p_trafficlights )
    {
        p_trafficlightsConfiguration
            .stream()
            .map( i -> (Map<String, Object>) i.get( "vehicles" ) )
            .filter( Objects::nonNull )
            .map( i -> new CVehiclesTrafficLight(
                (List<Integer>) i.get( "position" ),
                (int) i.get( "rotation" ),
                (int) i.get( "width" ),
                (int) i.get( "height" ),
                i.get( "startcolor" ).equals( "green" ) ? EVehiclesTrafficLight.GREEN : EVehiclesTrafficLight.RED,
                (int) i.get( "startcolorduration" ),
                ( (List<Integer>) i.get( "duration" ) ).stream().mapToInt( j -> j ).toArray()

            ) )
            .forEach( p_trafficlights::add );
    }

    /**
     * create ways
     *
     * @param p_waysConfiguration way configuration
     * @param p_ways list of ways
     */
    private void createWays( final List<Map<String, Object>> p_waysConfiguration, final List<IBaseWay> p_ways )
    {
        p_waysConfiguration
            .stream()
            .map( i -> (Map<String, Object>) i.get( "vehicles" ) )
            .filter( Objects::nonNull )
            .map( i -> new CVehiclesWay(
                (List<Integer>) i.get( "spriteposition" ),
                (List<Integer>) i.get( "leftbottom" ),
                (List<Integer>) i.get( "righttop" ),
                (int) i.get( "rotation" )
            ) )
            .forEach( p_ways::add );
    }

    /**
     * create lanes
     *
     * @param p_lanesConfiguration way configuration
     * @param p_lanes list of ways
     */
    private void createLanes( final List<Map<String, Object>> p_lanesConfiguration, final List<ILane> p_lanes)
    {
        p_lanesConfiguration
            .stream()
            .map( i -> (Map<String, Object>) i.get( "vehicles" ) )
            .filter( Objects::nonNull )
            .map( i -> new CLane(
                (List<Integer>) i.get( "leftbottom" ),
                (List<Integer>) i.get( "righttop" )
            ) )
            .forEach( p_lanes::add );
    }

    /**
     * creates the moving agent based on the configuration
     *
     * @param p_agentsConfiguration subsection for movable configuration
     * @param p_elements agents list
     * @throws IOException thrown on ASL reading error
     */
    @SuppressWarnings( "unchecked" )
    private void createAgents( final List<Map<String, Object>> p_agentsConfiguration, final List<IAgent> p_elements, final boolean p_agentprint ) throws IOException
    {

        final Map<String, IAgentGenerator<IAgent>> l_agentgenerator = new HashMap<>();
        final Set<IAction> l_action = Collections.unmodifiableSet(
            Stream.concat(
                p_agentprint ? Stream.of() : Stream.of( new CEmptyPrint() ),
                Stream.concat(
                    org.lightjason.agentspeak.common.CCommon.actionsFromPackage(),
                    org.lightjason.agentspeak.common.CCommon.actionsFromAgentClass( CVehicle.class )
                )
            ).collect( Collectors.toSet() ) );

        List<Map<String, Object>> l_vehiclesrandomgeneratepositions = new LinkedList<>();
        p_agentsConfiguration
            .stream()
            .map( i -> (Map<String, Object>) i.get( "vehiclesrandomgeneratepositions" ) )
            .filter( Objects::nonNull )
            .collect(Collectors.toCollection( () -> l_vehiclesrandomgeneratepositions ));

        p_agentsConfiguration
            .stream()
            .map( i -> (Map<String, Object>) i.get( "vehicles" ) )
            .filter( Objects::nonNull )
            .forEach( i ->
            {
                // read ASL item from configuration and get the path relative to configuration
                final String l_asl = m_configurationpath + ( (String) i.getOrDefault( "asl", "" ) ).trim();

                try (
                    // open filestream of ASL content
                    final InputStream l_stream = new URL( l_asl ).openStream();
                )
                {
                    // get existing agent generator or create a new one based on the ASL
                    // and push it back if generator does not exists
                    final IAgentGenerator<IAgent> l_generator = l_agentgenerator.getOrDefault(
                        l_asl,
                        new CVehicleGenerator(
                            m_environment,
                            l_stream,
                            l_action,
                            IAggregation.EMPTY
                        )
                    );
                    l_agentgenerator.putIfAbsent( l_asl, l_generator );

                    // generate agents and put it to the list
                    l_generator.generatemultiple(
                        (int) i.getOrDefault( "number", 0 ),
                        l_vehiclesrandomgeneratepositions,
                        i.get( "type" ),
                        i.get( "width" ),
                        i.get( "height" )
                    ).sequential().forEach( p_elements::add );
                }
                catch ( final Exception l_exception )
                {
                    l_exception.printStackTrace( System.out );
                    System.err.println( MessageFormat.format( "error on agent generation: {0}", l_exception ) );
                }

            } );
    }

    /**
     * creates an empty print action to supress output
     */
    private static final class CEmptyPrint extends IBaseAction
    {
        @Override
        public final IPath name()
        {
            return CPath.from( "generic/print" );
        }

        @Override
        public final int minimalArgumentNumber()
        {
            return 0;
        }

        @Override
        public IFuzzyValue<Boolean> execute( final IContext p_context, final boolean p_parallel, final List<ITerm> p_argument, final List<ITerm> p_return,
                                            final List<ITerm> p_annotation
        )
        {
            return CFuzzyValue.from( true );
        }
    }
}
