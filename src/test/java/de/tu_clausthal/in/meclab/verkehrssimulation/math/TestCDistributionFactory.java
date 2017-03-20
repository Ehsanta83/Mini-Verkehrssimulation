package de.tu_clausthal.in.meclab.verkehrssimulation.math;

import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.junit.Test;

/**
 * test distribution factory
 */
public class TestCDistributionFactory
{
    /**
     * test
     */
    @Test
    public void test()
    {
        final AbstractRealDistribution l_distribution = EDistributionFactory.from( "normal" ).generate( 0.5, 0.2 );
        System.out.println( l_distribution.sample() );
    }

    /**
     * main method
     * @param p_args args
     */
    public static void main( final String[] p_args )
    {
        final TestCDistributionFactory l_test = new TestCDistributionFactory();

        l_test.test();
    }
}
