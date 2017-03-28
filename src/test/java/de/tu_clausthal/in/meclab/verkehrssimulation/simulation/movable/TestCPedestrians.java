package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable;

import de.tu_clausthal.in.meclab.verkehrssimulation.IBaseTest;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.EObjectFactory;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 * test pedestrians
 *
 * @todo create test cases
 */
public class TestCPedestrians extends IBaseTest
{

    private CPedestrian m_pedestrian;


    @Before
    public final void initialize()
    {
        m_pedestrian = this.generate( "src/test/resources/pedestrian.asl", EObjectFactory.PEDESTRIAN );
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
