package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stationary.trafficlight;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.IBaseTest;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.EObjectFactory;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Collectors;


/**
 * test vehicles traffic light
 */
public final class TestCVehiclesTrafficLight extends IBaseTest
{

    private CTrafficLightVehicle m_vehiclelight;

    /**
     * initialize vehicle light
     */
    @Before
    public final void initialize()
    {
        m_vehiclelight = this.generate( "src/test/resources/vehiclelight.asl", EObjectFactory.VEHICLE_TRAFFICLIGHT, new DenseDoubleMatrix1D( new double[]{0, 0} ), 180 );
    }


    /**
     * pedestrian test
     *
     * @throws Exception on execution error
     */
    @Test
    public final void test() throws Exception
    {
        Assume.assumeNotNull( m_vehiclelight );

        System.out.println( m_vehiclelight.literal().collect( Collectors.toSet() ) );
        m_vehiclelight.call();
        System.out.println( m_vehiclelight.literal().collect( Collectors.toSet() ) );
    }

    /**
     * main method
     * @param p_args args
     *
     * @throws Exception on any error
     */
    public static void main( final String[] p_args ) throws Exception
    {
        final TestCVehiclesTrafficLight l_test = new TestCVehiclesTrafficLight();

        l_test.initializeenvironment();
        l_test.initialize();
        l_test.test();
    }


}
