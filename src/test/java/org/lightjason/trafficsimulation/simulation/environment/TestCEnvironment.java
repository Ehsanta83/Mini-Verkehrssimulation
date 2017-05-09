package org.lightjason.trafficsimulation.simulation.environment;

import cern.colt.matrix.ObjectMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.junit.Before;
import org.junit.Ignore;
import org.lightjason.trafficsimulation.IBaseTest;
import org.junit.Test;
import org.lightjason.trafficsimulation.math.EDistributionFactory;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;
import org.lightjason.trafficsimulation.simulation.movable.CPedestrian;

import java.text.MessageFormat;
import java.util.stream.IntStream;


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
