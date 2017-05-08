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
     *
     * @throws Exception on any error
     */
    @Before
    public final void initialize() throws Exception
    {
        this.initializeenvironment();
        m_vehicle = this.generate( "src/test/resources/vehicle.asl", EObjectFactory.VEHICLE, new DenseDoubleMatrix1D( new double[]{35, 1, 35, 2} ) );
    }


    /**
     * vehicle test
     */
    @Test
    public final void test()
    {
        Assume.assumeNotNull( m_vehicle );

        System.out.println(
            executeagent( m_vehicle ).literal().collect( Collectors.toSet() )
        );
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
