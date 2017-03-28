package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stationary.trafficlight;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.IBaseTest;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.EObjectFactory;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;


/**
 * test vehicles traffic light
 */
public final class TestCVehiclesTrafficLight extends IBaseTest
{

    private CTrafficLightVehicle m_vehiclelight;


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

        m_vehiclelight.call();
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
