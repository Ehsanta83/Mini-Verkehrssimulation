package org.lightjason.trafficsimulation.ui;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.lightjason.rest.CApplication;
import org.lightjason.trafficsimulation.CCommon;
import org.lightjason.trafficsimulation.CConfiguration;
import org.lightjason.trafficsimulation.simulation.IObject;

import java.awt.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.text.MessageFormat;
import java.util.stream.Stream;



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
     * run initialize without start-up of the server
     */
    public static void initialize()
    {}

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
     * @param p_agent agent object
     * @param p_group additional group
     * @return agent object
     */
    public static <T extends IObject<?>> T register( final T p_agent, final String... p_group )
    {
        if ( INSTANCE == null )
            return p_agent;

        INSTANCE.m_restagent.register( p_agent.name(), p_agent, p_group );

        return p_agent;
    }

    /**
     * register agent if server started
     *
     * @param p_agentgroup tupel of agent and stream of group names
     * @tparam T agent type
     * @return agent object
     */
    public static <T extends IObject<?>> T register( final Pair<T, Stream<String>> p_agentgroup )
    {
        return register( p_agentgroup.getLeft(), p_agentgroup.getRight().toArray( String[]::new ) );
    }

}
