package org.lightjason.trafficsimulation.simulation.environment;

import org.junit.Before;
import org.junit.Test;
import org.lightjason.trafficsimulation.IBaseTest;


/**
 * test environment
 *
 * @todo create test cases
 */
public final class TestCEnvironment extends IBaseTest
{

    /**
     * initialize
     *
     * @throws Exception on any error
     */
    @Before
    public final void initilize() throws Exception
    {
        this.initializeenvironment();
    }

    /**
     * environment test
     *
     * @throws Exception on execution error
     */
    @Test
    public final void testEnvironmentCall() throws Exception
    {
        m_environment.call();
    }

    /**
     * test call
     *
     * @param p_args command-line arguments
     */
    public static void main( final String[] p_args )
    {
        new TestCEnvironment().invoketest();
    }



}
