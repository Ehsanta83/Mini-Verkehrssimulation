package org.lightjason.trafficsimulation.simulation.movable;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.lightjason.trafficsimulation.IBaseTest;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Collectors;


/**
 * test pedestrians
 *
 * @todo create test cases
 */
public class TestCPedestrians extends IBaseTest
{

    private CPedestrian m_pedestrian;

    /**
     * initialize pedestrian
     */
    @Before
    public final void initialize()
    {
        m_pedestrian = this.generate( "src/test/resources/pedestrian.asl", EObjectFactory.PEDESTRIAN, new DenseDoubleMatrix1D( new double[]{0, 0} ) );
    }


    /**
     * pedestrian test
     *
     * @throws Exception on execution error
     */
    @Test
    public final void test() throws Exception
    {
        Assume.assumeNotNull( m_pedestrian );

        m_pedestrian.call();
        System.out.println( m_pedestrian.literal().collect( Collectors.toSet() ) );
    }

    /**
     * main method
     * @param p_args args
     *
     * @throws Exception on any error
     */
    public static void main( final String[] p_args ) throws Exception
    {
        final TestCPedestrians l_test = new TestCPedestrians();

        l_test.initializeenvironment();
        l_test.initialize();
        l_test.test();
    }

}
