package org.lightjason.trafficsimulation.simulation.environment;

import cern.colt.matrix.ObjectMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.junit.Before;
import org.junit.Ignore;
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
        /*
        m_pedestrian = (CPedestrian) generator(
            EObjectFactory.PEDESTRIAN,
            "src/test/resources/pedestrian.asl",
            EDistributionFactory.NORMAL,
            new double[]{5, 0.1},
            new double[]{7, 0.1}
        ).generatesingle();

        System.out.println( MessageFormat.format( "pedestrian position: [{0},{1}]", m_pedestrian.position().get( 0 ), m_pedestrian.position().get( 1 ) ) );
        */
    }

    /**
     * environment test
     *
     * @throws Exception on execution error
     */
    @Test
    @Ignore
    public final void testEnvironmentCall() throws Exception
    {
        m_environment.call();
    }

    /**
     * test moving pedestrian
     */
    @Test
    @Ignore
    public final void testMovingPedestrian()
    {
        m_environment.move( m_pedestrian, new DenseDoubleMatrix1D( new double[]{1, 2, 1, 2} ) );
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
