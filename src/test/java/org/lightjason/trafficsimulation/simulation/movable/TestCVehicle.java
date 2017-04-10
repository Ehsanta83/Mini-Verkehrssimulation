package org.lightjason.trafficsimulation.simulation.movable;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.lightjason.trafficsimulation.IBaseTest;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Collectors;


/**
 * test vehicle
 *
 * @todo create test cases
 */
public final class TestCVehicle extends IBaseTest
{

    private CVehicle m_vehicle;

    /**
     * initialize vehicle
     */
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
        System.out.println( m_vehicle.literal().collect( Collectors.toSet() ) );
    }


    /**
     * main method
     *
     * @param p_args args
     */
    public static void main( final String[] p_args )
    {
        new TestCVehicle().invoketest();
    }

}
