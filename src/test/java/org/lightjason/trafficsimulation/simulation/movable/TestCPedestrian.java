package org.lightjason.trafficsimulation.simulation.movable;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.lightjason.trafficsimulation.IBaseTest;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;

import java.util.stream.Collectors;


/**
 * test pedestrians
 *
 * @todo create test cases
 */
public final class TestCPedestrian extends IBaseTest
{

    /**
     * area generator
     */
    private CPedestrian m_pedestrian;

    /**
     * initialize pedestrian
     *
     * @throws Exception on initialize environment error
     */
    @Before
    public final void initialize() throws Exception
    {
        this.initializeenvironment();
        m_pedestrian = this.generate( "src/test/resources/pedestrian.asl", EObjectFactory.PEDESTRIAN, new DenseDoubleMatrix1D( new double[]{0, 0} ) );
    }

    /**
     * test pedestrian call
     *
     * @throws Exception on execution error
     */
    @Test
    public final void testagentcall() throws Exception
    {
        Assume.assumeNotNull( m_pedestrian );

        System.out.println(
            executeagent(
                m_pedestrian
            ).literal().collect( Collectors.toSet() )
        );
    }


    /**
     * test call
     *
     * @param p_args command-line arguments
     */
    public static void main( final String[] p_args )
    {
        new TestCPedestrian().invoketest();
    }
}
