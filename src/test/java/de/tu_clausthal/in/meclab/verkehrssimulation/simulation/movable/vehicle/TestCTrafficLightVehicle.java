package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.algorithm.routing.ERoutingFactory;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.CEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual.ILane;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.FileInputStream;
import java.util.*;
import java.util.logging.LogManager;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;


/**
 * test vehicle
 */
public final class TestCTrafficLightVehicle
{

    /**
     * environment reference
     */
    private IEnvironment m_environment;
    /**
     * action references
     */
    private Set<IAction> m_actions;
    /**
     * vehicle agent
     */
    private CVehicle m_vehicle;

    static
    {
        LogManager.getLogManager().reset();
    }

    /**
     * initialize
     */
    @Before
    public void initialize()
    {
        m_environment = new CEnvironment( 100, 100, 25, ERoutingFactory.JPSPLUS.get(), Collections.<ILane>emptyList() );

        m_actions = Collections.unmodifiableSet( Stream.concat(
                org.lightjason.agentspeak.common.CCommon.actionsFromPackage(),
                org.lightjason.agentspeak.common.CCommon.actionsFromAgentClass( CVehicle.class )
        ).collect( Collectors.toSet() ) );

        Assume.assumeNotNull( m_environment );
        Assume.assumeNotNull( m_actions );
    }

    /**
     * test vehicle generator
     */
    @Test
    public void testVehicleGenerator()
    {
        final List<Map<String, Object>> l_vehiclerandomgeneratepositions = new LinkedList<>();
        final Map<String, Object> l_map = new HashMap<String, Object>()
        {
            {
                put( "position", Stream.of( 1, 2 ).collect( Collectors.toList() ) );
                put( "rotation", 0 );
            }
        };
        l_vehiclerandomgeneratepositions.add( l_map );
        try
            (
                final FileInputStream l_stream = new FileInputStream( "src/test/resources/vehicle.asl" );
            )
        {
            m_vehicle = new CVehicleGenerator(
                m_environment,
                l_stream,
                m_actions,
                IAggregation.EMPTY )
                .generatesingle( l_vehiclerandomgeneratepositions, "car", 32, 16 );
            //m_vehicle.call();
        } catch ( final Exception l_exception )
        {
            l_exception.printStackTrace();
            assertTrue( l_exception.getMessage(), false );
        }
    }

    /**
     * test vehicle literals
     */
    @Test
    public void testVehicleLiteral()
    {
        /*try
        {
            m_vehicle.trigger(
                    CTrigger.from(
                            ITrigger.EType.ADDGOAL,
                            m_vehicle.literal().findFirst().get() )
            );

            m_vehicle.call();
        }
        catch ( final Exception l_exception )
        {
            assertTrue( l_exception.getMessage(), false );
        }*/
    }

    /**
     * main method
     *
     * @param p_args args
     */
    public static void main( final String[] p_args )
    {
        final TestCTrafficLightVehicle l_test = new TestCTrafficLightVehicle();

        l_test.initialize();
        l_test.testVehicleGenerator();

        l_test.initialize();
        l_test.testVehicleLiteral();
    }
}
