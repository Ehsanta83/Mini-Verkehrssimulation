package de.tu_clausthal.in.meclab.verkehrssimulation;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.MessageFormat;


/**
 * main desktop application
 *
 * @bug clean-up
 */
public final class CMain
{

    /**
     * constructor
     */
    private CMain()
    {
    }

    /**
     * main method
     *
     * @param p_args arguments
     * @throws IOException on io errors
     * @throws URISyntaxException on URI syntax error
     */
    public static void main( final String[] p_args ) throws IOException, URISyntaxException
    {
        // --- define CLI options ------------------------------------------------------------------------------------------------------------------------------

        final Options l_clioptions = new Options();
        l_clioptions.addOption( "help", false, "shows this information" );
        l_clioptions.addOption( "generateconfig", false, "generate default configuration" );
        l_clioptions.addOption( "config", true, "path to configuration directory (default: <user home>/.asimov/configuration.yaml)" );

        final CommandLine l_cli;
        try
        {
            l_cli = new DefaultParser().parse( l_clioptions, p_args );
        }
        catch ( final Exception l_exception )
        {
            System.err.println( "command-line arguments parsing error" );
            System.exit( -1 );
            return;
        }



        // --- process CLI arguments and initialize configuration ----------------------------------------------------------------------------------------------

        if ( l_cli.hasOption( "help" ) )
        {
            new HelpFormatter().printHelp( new java.io.File( CMain.class.getProtectionDomain().getCodeSource().getLocation().getPath() ).getName(), l_clioptions );
            return;
        }

        if ( l_cli.hasOption( "generateconfig" ) )
        {
            System.out.println( MessageFormat.format( "default configuration was created under [{0}]", CConfiguration.createdefault() ) );
            return;
        }

        // load configuration and start the http server (if possible)
        CConfiguration.INSTANCE.loadfile( l_cli.getOptionValue( "config", "" ) );


        // execute server
        CHTTPServer.execute();
    }


}
