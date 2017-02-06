package de.tu_clausthal.in.meclab.verkehrssimulation;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.tu_clausthal.in.meclab.verkehrssimulation.ui.CScreen;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * main desktop application
 */
public final class CMain
{
    /**
     * logger
     */
    private static final Logger LOGGER = Logger.getLogger( CMain.class.getName() );

    /**
     * constructor
     */
    private CMain()
    {
    }

    /**
     * main method
     *
     * @param p_arg arguments
     * @throws IOException on io errors
     * @throws URISyntaxException on URI syntax error
     */
    public static void main( final String[] p_arg ) throws IOException, URISyntaxException
    {
        // --- read configuration and initialize simulation ui -------------------------------------------------------------------------------------------------

        CConfiguration.INSTANCE.load( CCommon.PACKAGEPATH + "configuration.yaml" );

        final LwjglApplicationConfiguration l_config = new LwjglApplicationConfiguration();
        l_config.title = "Verkehrssimulation";
        l_config.width = CConfiguration.INSTANCE.windowWidth();
        l_config.height = CConfiguration.INSTANCE.windowHeight();

        // open window
        LOGGER.info( MessageFormat.format( "open window with size [{0}x{1}]", l_config.width, l_config.height ) );
        final CScreen l_screen = new CScreen(
            Stream.of(
                CConfiguration.INSTANCE.ways().parallelStream(),
                CConfiguration.INSTANCE.trafficlights().parallelStream(),
                CConfiguration.INSTANCE.vehicles().parallelStream()
            )
                .flatMap( i -> i )
                .collect( Collectors.toList() ),

            CConfiguration.INSTANCE.environment()
        );
        new LwjglApplication( l_screen, l_config );
        CMain.execute( l_screen );
    }

    /**
     * execute simulation
     *
     * @param p_screen screen reference
     */
    private static void execute( final CScreen p_screen )
    {
        IntStream
            .range( 0, CConfiguration.INSTANCE.simulationsteps() )
            .mapToObj( i ->
            {
                Stream.concat(
                    Stream.of(
                        //CConfiguration.INSTANCE.evaluation(),
                        CConfiguration.INSTANCE.environment()
                    ),
                    Stream.concat(
                        Stream.concat(
                            CConfiguration.INSTANCE.ways().parallelStream(),
                            CConfiguration.INSTANCE.trafficlights().parallelStream() ),
                        CConfiguration.INSTANCE.vehicles().parallelStream()
                    )
                )
                    .parallel()
                    .forEach(j ->
                    {
                        try
                        {
                            j.call();
                        }
                        catch ( final Exception l_exception )
                        {
                            LOGGER.warning( l_exception.toString() );
                            if ( CConfiguration.INSTANCE.stackstrace() )
                                l_exception.printStackTrace( System.err );
                        }
                    } );
                // thread sleep for slowing down
                if ( CConfiguration.INSTANCE.threadsleeptime() > 0 )
                    try
                    {
                        Thread.sleep( CConfiguration.INSTANCE.threadsleeptime() );
                    }
                    catch ( final InterruptedException l_exception )
                    {
                        LOGGER.warning( l_exception.toString() );
                    }

                // checks that the simulation is closed
                return p_screen.isDisposed();
            } )
            .filter( i -> i )
            .findFirst();
    }
}
