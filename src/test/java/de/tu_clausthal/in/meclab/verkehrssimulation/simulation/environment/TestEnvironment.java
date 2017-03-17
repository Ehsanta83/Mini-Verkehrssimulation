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
public class TestEnvironment
{
    /**
     * environment reference
     */
    private IEnvironment m_environment;

    @Before
    public void initialize()
    {
        try
        {
            CConfiguration.INSTANCE.load( CCommon.PACKAGEPATH + "configuration.yaml" );
            m_environment = CConfiguration.INSTANCE.environment();
        } catch ( IOException l_exception )
        {
            l_exception.printStackTrace();
            l_exception.printStackTrace();
            assertTrue( l_exception.getMessage() , false );
        } catch ( URISyntaxException l_exception )
        {
            l_exception.printStackTrace();
            l_exception.printStackTrace();
            assertTrue( l_exception.getMessage() , false );
        }
    }

    @Test
    public void test()
    {
        final ObjectMatrix2D l_lanepositions = m_environment.lanespositions();
        IntStream.rangeClosed( 1, l_lanepositions.rows() ).forEach( i ->
            {
              IntStream.rangeClosed( 1, l_lanepositions.columns() ).forEach( j ->
                  {
                      System.out.println( "[" + i + ", " + j + "]: " + l_lanepositions.getQuick( i, j ) );
                  }
              );
            }
        );
    }

    public static void main( String[] p_args )
    {
        final TestEnvironment l_test = new TestEnvironment();

        l_test.initialize();
        l_test.test();

    }
}
