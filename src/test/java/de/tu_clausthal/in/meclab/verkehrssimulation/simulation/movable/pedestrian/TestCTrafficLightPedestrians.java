package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.pedestrian;

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
 * test pedestrians
 */
public class TestCTrafficLightPedestrians
{

    private CPedestrian m_pedestrian;

    private IEnvironment m_environment;

    private Set<IAction> m_actions;


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
                org.lightjason.agentspeak.common.CCommon.actionsFromAgentClass( CPedestrian.class )
        ).collect( Collectors.toSet() ) );

        Assume.assumeNotNull( m_environment );
        Assume.assumeNotNull( m_actions );
    }

    /**
     * test pedestrian generator
     */
    @Test
    public void testPedestrianGenerator()
    {
        try
        (
            final FileInputStream l_stream = new FileInputStream( "src/test/resources/pedestrian.asl" );
        )
        {
            m_pedestrian = new CPedestrianGenerator(
                l_stream,
                m_actions,
                IAggregation.EMPTY,
                m_environment )
                    .generatesingle();
            //m_pedestrian.call();
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
        /*try
        {
            m_pedestrian.trigger(
                CTrigger.from(
                    ITrigger.EType.ADDGOAL,
                    m_pedestrian.literal().findFirst().get() )
            );
            m_pedestrian.call();
        }
        catch ( final Exception l_exception )
        {
            l_exception.printStackTrace();
            assertTrue( l_exception.getMessage(), false );

        }*/
    }

    /**
     * main method
     * @param p_args args
     */
    public static void main( final String[] p_args )
    {
        final TestCTrafficLightPedestrians l_test = new TestCTrafficLightPedestrians();

        l_test.initialize();
        l_test.testPedestrianGenerator();

        l_test.initialize();
        l_test.testPedestrianLiteral();
    }

}
