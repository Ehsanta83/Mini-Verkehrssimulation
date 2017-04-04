package org.lightjason.trafficsimulation.simulation.virtual;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.junit.Before;
import org.junit.Test;
import org.lightjason.trafficsimulation.IBaseTest;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;
import org.lightjason.trafficsimulation.simulation.environment.EDirection;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * test area
 */
public class TestCArea extends IBaseTest
{
    private List<CArea> m_areas;

    /**
     * initialize area
     */
    @Before
    public final void initialize()
    {
        m_areas = new LinkedList<>();

        m_areas.add( this.generate( "src/test/resources/area.asl",
            EObjectFactory.AREA,
            true,
            EArea.VEHICLE_WAY,
            Stream.of( EDirection.EAST ),
            new DenseDoubleMatrix1D( new double[]{0, 0} ) )
        );

        m_areas.add( this.generate( "src/test/resources/area.asl",
            EObjectFactory.AREA,
            true,
            EArea.SIDEWALK,
            Stream.of( EDirection.NORTH, EDirection.NORTHWEST, EDirection.WEST ),
            new DenseDoubleMatrix1D( new double[]{10, 10} ) )
        );

    }

    /**
     * area test
     *
     * @throws Exception on execution error
     */
    @SuppressWarnings( {"SimplifyStreamApiCallChains", "ConstantConditions"} )
    @Test
    public final void test() throws Exception
    {
        m_areas.stream()
            .forEach( area ->
                {
                    try
                    {
                        area.call();
                    }
                    catch ( final Exception l_exception )
                    {
                        l_exception.printStackTrace();
                        assertTrue( false );
                    }
                    System.out.println( area.literal().collect( Collectors.toSet() ) );
                }
            );
    }

    /**
     * main method
     *
     * @param p_args args
     *
     * @throws Exception on any error
     */
    public static void main( final String[] p_args ) throws Exception
    {
        final TestCArea l_test = new TestCArea();

        l_test.initializeenvironment();
        l_test.initialize();
        l_test.test();
    }
}
