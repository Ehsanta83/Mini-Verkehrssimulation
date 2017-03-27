package de.tu_clausthal.in.meclab.verkehrssimulation;

import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * servlet class
 */
public class CServlet extends HttpServlet
{
    @Override
    protected void doGet( final HttpServletRequest p_req, final HttpServletResponse p_resp ) throws ServletException, IOException
    {
        p_resp.setStatus( HttpStatus.OK_200 );
        p_resp.getWriter().println( "Heeeeeeeeeeeeey" );
    }
}
