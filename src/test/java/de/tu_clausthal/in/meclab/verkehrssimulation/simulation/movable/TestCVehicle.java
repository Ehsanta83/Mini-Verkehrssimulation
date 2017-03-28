package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.IBaseTest;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.EObjectFactory;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;


/**
 * test vehicle
 *
 * @todo create test cases
 */
public final class TestCVehicle extends IBaseTest
{

    private CVehicle m_vehicle;

    @Before
    public final void initialize()
    {
        m_vehicle = this.generate( "src/test/resources/vehicle.asl", EObjectFactory.VEHICLE, new DenseDoubleMatrix1D( new double[]{0, 0} ) );
    }


    /**
     * pedestrian test
     *
     * @throws Exception on execution error
     */
    @Test
    public final void test() throws Exception
    {
        Assume.assumeNotNull( m_vehicle );

        m_vehicle.call();
    }


    /**
     * main method
     * @param p_args args
     *
     * @throws Exception on any error
     */
    public static void main( final String[] p_args ) throws Exception
    {
        final TestCVehicle l_test = new TestCVehicle();

        l_test.initializeenvironment();
        l_test.initialize();
        l_test.test();
    }

}
