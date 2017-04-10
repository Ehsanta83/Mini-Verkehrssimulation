package org.lightjason.trafficsimulation.simulation.environment;

import cern.colt.matrix.ObjectMatrix2D;
import org.junit.Before;
import org.lightjason.trafficsimulation.IBaseTest;
import org.junit.Test;
import org.lightjason.trafficsimulation.math.EDistributionFactory;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;
import org.lightjason.trafficsimulation.simulation.movable.CPedestrian;

import java.text.MessageFormat;
import java.util.stream.IntStream;


/**
 * test environment
 *
 * @todo create test cases
 */
public final class TestCEnvironment extends IBaseTest
{
    /**
     * pedestrian
     */
    private CPedestrian m_pedestrian;

    /**
     * initialize
     *
     * @throws Exception on any error
     */
    @Before
    public final void initilize() throws Exception
    {
        initializeenvironment();
        initializeArea();

        m_pedestrian = (CPedestrian) generator(
            EObjectFactory.PEDESTRIAN,
            "src/test/resources/pedestrian.asl",
            EDistributionFactory.NORMAL,
            new double[]{5, 0.1},
            new double[]{7, 0.1}
        ).generatesingle();

        System.out.println( MessageFormat.format( "pedestrian position: [{0},{1}]", m_pedestrian.position().get( 0 ), m_pedestrian.position().get( 1 ) ) );
    }

    /**
     * environment test
     *
     * @throws Exception on execution error
     */
    @Test
    public final void testEnvironmentCall() throws Exception
    {
        m_environment.call();
    }

    /**
     * are grid test
     *
     * @throws Exception on execution error
     */
    @Test
    public final void testAreaGrid()
    {
        final ObjectMatrix2D l_areagrid = m_environment.areagrid();
        IntStream.rangeClosed( 1, l_areagrid.rows() ).forEach( i ->
        {
            IntStream.rangeClosed( 1, l_areagrid.columns() ).forEach( j ->
            {
                System.out.println( "[" + i + ", " + j + "]: " + l_areagrid.getQuick( i, j ) );
            } );
        } );
    }

    /**
     * test agent grid
     */
    @Test
    public final void testAgentGrid()
    {
        final ObjectMatrix2D l_agentgrid = m_environment.moveablegrid();
        IntStream.rangeClosed( 1, l_agentgrid.rows() ).forEach( i ->
        {
            IntStream.rangeClosed( 1, l_agentgrid.columns() ).forEach( j ->
            {
                System.out.println( "[" + i + ", " + j + "]: " + l_agentgrid.getQuick( i, j ) );
            } );
        } );
    }

    /**
     * test moving pedestrian
     */
    @Test
    public final void testMovingPedestrian()
    {

    }

    /**
     * test call
     *
     * @param p_args command-line arguments
     */
    public static void main( final String[] p_args )
    {
        new TestCEnvironment().invoketest();
    }



}
