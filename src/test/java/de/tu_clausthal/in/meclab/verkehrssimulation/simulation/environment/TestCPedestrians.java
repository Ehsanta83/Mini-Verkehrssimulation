package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.algorithm.routing.ERoutingFactory;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.human.CPedestrian;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.human.CPedestrianGenerator;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle.CVehicle;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle.CVehicleGenerator;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual.ILane;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.CTrigger;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.ITrigger;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.FileInputStream;
import java.util.*;
import java.util.logging.LogManager;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * Created by Nina on 17.03.2017.
 */
public class TestCPedestrians {

    private CPedestrian m_pedestrian;

    private IEnvironment m_environment;

    private Set<IAction> m_actions;


    static
    {
        LogManager.getLogManager().reset();
    }

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

    @Test
    public void testPedestrianGenerator()
    {    try
                (
                        final FileInputStream l_stream = new FileInputStream( "src/test/resources/pedestrian.asl" );
                )
        {
            m_pedestrian = (CPedestrian) new CPedestrianGenerator(
                    m_environment,
                    l_stream,
                    m_actions,
                    IAggregation.EMPTY )
                    .generatesingle();
            m_pedestrian.call();
        } catch (final Exception l_exception) {
            l_exception.printStackTrace();
            assertTrue( l_exception.getMessage() , false );
        }
    }
    public void testPedestrianLiteral(){
        try{
            m_pedestrian.trigger(
                    CTrigger.from(
                            ITrigger.EType.ADDGOAL,
                            m_pedestrian.literal().findFirst().get() )
            );
            m_pedestrian.call();
        }
        catch(final Exception l_exception){
            l_exception.printStackTrace();
            assertTrue( l_exception.getMessage() , false );

        }
    }

    public static void main( final String[] p_args )
    {
        final TestCPedestrians l_test = new TestCPedestrians();

        l_test.initialize();
        l_test.testPedestrianGenerator();

        l_test.initialize();
        l_test.testPedestrianLiteral();
    }
}
