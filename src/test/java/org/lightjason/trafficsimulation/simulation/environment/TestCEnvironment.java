package org.lightjason.trafficsimulation.simulation.environment;

import org.lightjason.trafficsimulation.IBaseTest;
import org.junit.Test;


/**
 * test environment
 *
 * @todo create test cases
 */
public final class TestCEnvironment extends IBaseTest
{

    /**
     * pedestrian test
     *
     * @throws Exception on execution error
     */
    @Test
    public final void test() throws Exception
    {
        m_environment.call();
    }


    /**
     * main method
     * @param p_args args
     *
     * @throws Exception on any error
     */
    public static void main( final String[] p_args ) throws Exception
    {
        final TestCEnvironment l_test = new TestCEnvironment();

        l_test.initializeenvironment();
        l_test.test();
    }

}
