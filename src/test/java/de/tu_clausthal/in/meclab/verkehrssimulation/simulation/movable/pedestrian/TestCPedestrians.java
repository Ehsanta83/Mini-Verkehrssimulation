package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.pedestrian;

import de.tu_clausthal.in.meclab.verkehrssimulation.CCommon;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.algorithm.routing.ERoutingFactory;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.CEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual.ILane;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.CTrigger;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.ITrigger;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.*;
import java.util.logging.LogManager;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * test pedestrians
 */
public class TestCPedestrians
{
    /**
     * pedestrian
     */
    private CPedestrian m_pedestrian;

    static
    {
        LogManager.getLogManager().reset();
    }

    /**
     * initialize
     */
    @SuppressWarnings( "unchecked" )
    @Before
    public void initialize()
    {
        final IEnvironment l_environment = new CEnvironment( 100, 100, 25, ERoutingFactory.JPSPLUS.get(), Collections.<ILane>emptyList() );

        final Set<IAction> l_actions = Collections.unmodifiableSet( Stream.concat(
                org.lightjason.agentspeak.common.CCommon.actionsFromPackage(),
                org.lightjason.agentspeak.common.CCommon.actionsFromAgentClass( CPedestrian.class )
        ).collect( Collectors.toSet() ) );

        Assume.assumeNotNull( l_environment );
        Assume.assumeNotNull( l_actions );

        // read configuration
        try
        {
            final List<Map<String, Object>> l_pedestriansconfiguration = ( (List<Map<String, Object>>)( (Map<String, Object>) new Yaml().load(
                CCommon.getResourceURL( CCommon.PACKAGEPATH + "configuration.yaml" ).openStream() ) )
                .get( "agents" ) )
                .stream()
                .filter( map -> "pedestrian".equals( map.get( "type" ) ) )
                .collect( Collectors.toList() );
            final Map<String, Object> l_distributionconfiguration = (Map<String, Object>) l_pedestriansconfiguration.get( 0 ).get( "distribution" );
            final Map<String, Object> l_agentconfiguration = (Map<String, Object>) l_pedestriansconfiguration.get( 0 ).get( "configuration" );

            final FileInputStream l_stream = new FileInputStream( "src/test/resources/male.asl" );

            m_pedestrian = new CPedestrianGenerator(
                l_stream,
                l_distributionconfiguration,
                l_agentconfiguration,
                l_actions,
                IAggregation.EMPTY,
                l_environment )
                .generatesingle();
            if ( m_pedestrian != null )
            {
                m_pedestrian.call();
            }
            System.out.println( m_pedestrian );
        } catch ( final Exception l_exception )
        {
            l_exception.printStackTrace();
            assertTrue( l_exception.getMessage(), false );
        }
    }

    /**
     * test pedestrian literal
     */
    @Test
    public void testPedestrianLiteral()
    {
        try
        {
            if ( m_pedestrian != null )
            {
                m_pedestrian.trigger(
                    CTrigger.from(
                        ITrigger.EType.ADDGOAL,
                        m_pedestrian.literal().findFirst().get() )
                );
                m_pedestrian.call();
            }
        }
        catch ( final Exception l_exception )
        {
            l_exception.printStackTrace();
            assertTrue( l_exception.getMessage(), false );

        }
    }

    /**
     * main method
     * @param p_args args
     */
    public static void main( final String[] p_args )
    {
        final TestCPedestrians l_test = new TestCPedestrians();

        l_test.initialize();
        l_test.testPedestrianLiteral();
    }

}
