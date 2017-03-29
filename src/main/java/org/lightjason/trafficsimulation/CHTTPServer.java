package org.lightjason.trafficsimulation;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.lightjason.agentspeak.agent.IAgent;
import org.lightjason.rest.CApplication;

import java.awt.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.text.MessageFormat;



/**
 * Jersey-Jetty-HTTP server for UI
 *
 * debug: -Dorg.eclipse.jetty.servlet.LEVEL=ALL
 *
 */
public final class CHTTPServer
{
    /**
     * webservcer instance
     */
    private static final CHTTPServer INSTANCE = CConfiguration.INSTANCE.<Boolean>getOrDefault( false, "httpserver", "enable" ) ? new CHTTPServer() : null;
    /**
     * server instance
     */
    private final Server m_server;
    /**
     * REST-API application
     */
    private final CApplication m_restagent = new CApplication();

    /**
     * ctor
     */
    private CHTTPServer()
    {
        // web context definition
        final WebAppContext l_webapp = new WebAppContext();

        // server process
        m_server = new Server(
            new InetSocketAddress( CConfiguration.INSTANCE.<String>getOrDefault( "localhost", "httpserver", "host" ),
                                   CConfiguration.INSTANCE.<Integer>getOrDefault( 8000, "httpserver", "port" )
            )
        );

        // set server / webapp connection
        m_server.setHandler( l_webapp );
        l_webapp.setServer( m_server );
        l_webapp.setContextPath( "/" );
        l_webapp.setWelcomeFiles( new String[]{"index.html", "index.htm"} );
        l_webapp.setResourceBase( CHTTPServer.class.getResource( MessageFormat.format( "{0}{1}{2}", "/", CCommon.PACKAGEPATH, "html" ) ).toExternalForm() );
        l_webapp.addServlet( new ServletHolder( new ServletContainer( m_restagent ) ), "/rest/*" );
    }

    /**
     * execute the server instance
     */
    public static void execute()
    {
        if ( INSTANCE == null )
            return;

        try
        {
            INSTANCE.m_server.start();

            // open browser if possible
            if ( ( CConfiguration.INSTANCE.<Boolean>getOrDefault( true, "openbrowser" ) ) && ( Desktop.isDesktopSupported() ) )
                Desktop.getDesktop().browse( new URI(
                                                 "http://" + CConfiguration.INSTANCE.<String>getOrDefault( "localhost", "httpserver", "host" )
                                                 + ":" + CConfiguration.INSTANCE.<Integer>getOrDefault( 8000, "httpserver", "port" )
                                             )
                );

            INSTANCE.m_server.join();

        }
        catch ( final Exception l_exception )
        {
            throw new RuntimeException( l_exception );
        }
        finally
        {
            INSTANCE.m_server.destroy();
        }
    }

    /**
     * register agent if server is started
     *
     * @param p_name name of the agent
     * @param p_agent agent object
     * @param p_group additional group
     */
    public static void register( final String p_name, final IAgent<?> p_agent, final String... p_group )
    {
        if ( INSTANCE == null )
            return;

        INSTANCE.m_restagent.register( p_name, p_agent, p_group );
    }

}
