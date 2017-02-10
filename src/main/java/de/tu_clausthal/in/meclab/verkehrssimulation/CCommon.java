package de.tu_clausthal.in.meclab.verkehrssimulation;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * common class
 */
public final class CCommon
{
    /**
     * package path
     */
    public static final String PACKAGEPATH = "de/tu_clausthal/in/meclab/verkehrssimulation/";

    /**
     * constructor
     */
    private CCommon()
    {
    }

    /**
     * returns a file from a resource e.g. Jar file
     *
     * @param p_file file
     * @return URL of file or null
     * @throws MalformedURLException on incorrect URL
     * @throws URISyntaxException on incorrect URI syntax
     */
    public static URL getResourceURL( final String p_file ) throws MalformedURLException, URISyntaxException
    {
        return CCommon.getResourceURL( new File( p_file ) );
    }

    /**
     * returns a file from a resource e.g. Jar file
     *
     * @param p_file file relative to the CMain
     * @return URL of file or null
     * @throws MalformedURLException on incorrect URL
     * @throws URISyntaxException on incorrect URI syntax
     */
    public static URL getResourceURL( final File p_file ) throws MalformedURLException, URISyntaxException
    {
        try
        {
            if ( p_file.exists() )
                return p_file.toURI().normalize().toURL();
            return CCommon.class.getClassLoader().getResource( p_file.toString().replace( File.separator, "/" ) ).toURI().normalize().toURL();
        }
        catch ( final Exception l_exception )
        {
            throw l_exception;
        }
    }
}
