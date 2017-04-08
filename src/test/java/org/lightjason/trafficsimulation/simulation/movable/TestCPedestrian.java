package org.lightjason.trafficsimulation.simulation.movable;

import org.junit.Assert;
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
    private IAgentGenerator m_pedestriangenerator;

    /**
     * initialize pedestrian
     *
     * @throws Exception on initialize environment error
     */
    @Before
    public final void initialize() throws Exception
    {
        initializeenvironment();
        m_pedestriangenerator = generator(
            EObjectFactory.PEDESTRIAN,
            "src/test/resources/pedestrian.asl",
            EDistributionFactory.NORMAL,
            new double[]{5, 0.1},
            new double[]{7, 0.1}
        );
    }

    /**
     * test pedestrian call
     *
     * @throws Exception on execution error
     */
    @Test
    public final void testagentcall() throws Exception
    {
        final CPedestrian l_pedestrian = (CPedestrian) m_pedestriangenerator.generatesingle();

        try
        {
            l_pedestrian.call();
        }
        catch ( final Exception l_exception )
        {
            l_exception.printStackTrace();
            Assert.assertTrue( false );
        }
        System.out.println( l_pedestrian.literal().collect( Collectors.toSet() ) );
    }
}
