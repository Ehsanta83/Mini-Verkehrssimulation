package de.tu_clausthal.in.meclab.verkehrssimulation;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

/**
 * configuration class
 *
 * @author Ehsan Tatasadi
 */
public final class CConfiguration
{
    /**
     * singleton instance
     */
    public static final CConfiguration INSTANCE = new CConfiguration();
    /**
     * window height
     */
    private int m_windowheight;
    /**
     * window width
     */
    private int m_windowwidth;

    /**
     * constructor
     */
    private CConfiguration()
    {
    }

    /**
     * loads the configuration from a file
     *
     * @param p_input YAML configuration file
     * @return instance
     *
     * @throws IOException on io errors
     * @throws URISyntaxException on URI syntax error
     */
    @SuppressWarnings( "unchecked" )
    public final CConfiguration load( final String p_input ) throws IOException, URISyntaxException
    {
        final URL l_path = CCommon.getResourceURL( p_input );

        // read configuration
        final Map<String, Object> l_data = (Map<String, Object>) new Yaml().load( l_path.openStream() );

        m_windowwidth = ( (Map<String, Integer>) l_data.getOrDefault( "window", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "width", 800 );
        m_windowheight = ( (Map<String, Integer>) l_data.getOrDefault( "window", Collections.<String, Integer>emptyMap() ) ).getOrDefault( "height", 600 );

        return this;
    }


    /**
     * return window height
     *
     * @return height
     */
    final int windowHeight()
    {
        return m_windowheight;
    }

    /**
     * return window width
     *
     * @return width
     */
    final int windowWidth()
    {
        return m_windowwidth;
    }
}
