package org.lightjason.trafficsimulation.math;

import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.junit.Test;
import org.lightjason.trafficsimulation.IBaseTest;


/**
 * test distribution factory
 */
public class TestCDistributionFactory extends IBaseTest
{
    /**
     * test
     */
    @Test
    public void test()
    {
        final AbstractRealDistribution l_distribution = EDistributionFactory.from( "normal" ).generate( 0.5, 0.2 );
        System.out.println( l_distribution.sample() + ", " + l_distribution.getNumericalMean() );
    }

    /**
     * main method
     * @param p_args args
     */
    public static void main( final String[] p_args )
    {
        new TestCDistributionFactory().invoketest();
    }
}
