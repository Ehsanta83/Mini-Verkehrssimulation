package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment;

import cern.colt.matrix.ObjectMatrix2D;
import de.tu_clausthal.in.meclab.verkehrssimulation.CCommon;
import de.tu_clausthal.in.meclab.verkehrssimulation.CConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

/**
 * test environment
 */
public class TestCEnvironment
{
    /**
     * environment reference
     */
    private IEnvironment m_environment;

    /**
     * initialize
     */
    @Before
    public void initialize()
    {
        try
        {
            CConfiguration.INSTANCE.load( CCommon.PACKAGEPATH + "configuration.yaml" );
            m_environment = CConfiguration.INSTANCE.environment();
        }
        catch ( final IOException l_exception )
        {
            l_exception.printStackTrace();
            l_exception.printStackTrace();
            assertTrue( l_exception.getMessage(), false );
        }
        catch ( final URISyntaxException l_exception )
        {
            l_exception.printStackTrace();
            l_exception.printStackTrace();
            assertTrue( l_exception.getMessage(), false );
        }
    }

    /**
     * test
     */
    @Test
    public void test()
    {
        final ObjectMatrix2D l_lanepositions = m_environment.lanespositions();
        IntStream.rangeClosed( 1, l_lanepositions.rows() ).forEach( i ->
        {
            IntStream.rangeClosed( 1, l_lanepositions.columns() ).forEach( j ->
            {
                System.out.println( "[" + i + ", " + j + "]: " + l_lanepositions.getQuick( i, j ) );
            } );
        } );
    }

    /**
     * main method
     * @param p_args args
     */
    public static void main( final String[] p_args )
    {
        final TestCEnvironment l_test = new TestCEnvironment();

        l_test.initialize();
        l_test.test();

    }
}
