package org.lightjason.trafficsimulation.simulation.movable;

import org.junit.Assert;
import org.junit.Assume;
import org.lightjason.agentspeak.generator.IAgentGenerator;
import org.lightjason.trafficsimulation.IBaseTest;
import org.lightjason.trafficsimulation.math.EDistributionFactory;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Collectors;


/**
 * test pedestrians
 *
 * @todo create test cases
 */
public class TestCPedestrian extends IBaseTest
{

    /**
     * area generator
     */
    private IAgentGenerator<CPedestrian> m_pedestriangenerator;

    /**
     * initialize pedestrian
     *
     * @throws Exception on initialize environment error
     */
    @Before
    public final void initialize() throws Exception
    {
        //m_pedestriangenerator = this.generate( "src/test/resources/pedestrian.asl", EObjectFactory.PEDESTRIAN );
    }

    /**
     * test pedestrian call
     *
     * @throws Exception on execution error
     */
    @Test
    public final void testagentcall() throws Exception
    {
        Assume.assumeNotNull( m_pedestriangenerator );

        System.out.println(
            executeagent(
                m_pedestriangenerator.generatesingle()
            ).literal().collect( Collectors.toSet() )
        );
    }


    /**
     * test call
     *
     * @param p_args command-line arguments
     */
    public static void main( final String[] p_args )
    {
        new TestCPedestrian().invoketest();
    }
}
