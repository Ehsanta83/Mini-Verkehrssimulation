package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stationary.trafficlight;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.IBaseTest;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.EObjectFactory;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Collectors;


/**
 * test pedestrian traffic light
 */
public final class TestCPedestrianTrafficLight extends IBaseTest
{

    private CTrafficLightPedestrian m_pedestrianlight;

    /**
     * initialize traffic light
     */
    @Before
    public final void initialize()
    {
        m_pedestrianlight = this.generate( "src/test/resources/pedestrianlight.asl", EObjectFactory.PEDESTRIAN_TRAFFICLIGHT, new DenseDoubleMatrix1D( new double[]{0, 0} ), 90 );
    }


    /**
     * pedestrian test
     *
     * @throws Exception on execution error
     */
    @Test
    public final void test() throws Exception
    {
        Assume.assumeNotNull( m_pedestrianlight );

        System.out.println( m_pedestrianlight.literal().collect( Collectors.toSet() ) );
        m_pedestrianlight.call();
        System.out.println( m_pedestrianlight.literal().collect( Collectors.toSet() ) );
    }

    /**
     * main method
     * @param p_args args
     *
     * @throws Exception on any error
     */
    public static void main( final String[] p_args ) throws Exception
    {
        final TestCPedestrianTrafficLight l_test = new TestCPedestrianTrafficLight();

        l_test.initializeenvironment();
        l_test.initialize();
        l_test.test();
    }

}
