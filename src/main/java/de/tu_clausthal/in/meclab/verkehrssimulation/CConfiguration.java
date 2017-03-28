package de.tu_clausthal.in.meclab.verkehrssimulation;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * configuration and initialization of all simulation objects
 *
 * @bug refactor
 */
public final class CConfiguration
{
    /**
     * singleton instance
     */
    public static final CConfiguration INSTANCE = new CConfiguration();
    /**
     * map with configuration data
     */
    private final Map<String, Object> m_configuration = new ConcurrentHashMap<>();


    /**
     * ctor
     */

    private CConfiguration()
    {
    }

    /**
     * loads the configuration
     * @param p_path path elements
     * @return self reference
     */
    @SuppressWarnings( "unchecked" )
    public final CConfiguration loadfile( final String p_path )
    {

        try
            (
                final InputStream l_stream = new FileInputStream( orDefaultPath( p_path ) );
            )
        {

            final Map<String, ?> l_result = (Map<String, Object>) new Yaml().load( l_stream );
            if ( l_result != null )
            {
                m_configuration.clear();
                m_configuration.putAll( l_result );
            }

        } catch ( final IOException l_exception )
        {
            throw new RuntimeException( l_exception );
        }

        return this;
    }

    /**
     * loads the configuration from a string
     *
     * @param p_yaml yaml string
     * @return self reference
     */
    @SuppressWarnings( "unchecked" )
    public final CConfiguration loadstring( final String p_yaml )
    {
        final Map<String, ?> l_result = (Map<String, Object>) new Yaml().load( p_yaml );
        if ( l_result != null )
        {
            m_configuration.clear();
            m_configuration.putAll( l_result );
        }
        return this;
    }

    /**
     * set default path
     *
     * @param p_path path or null / empty
     * @return default path on empty or input path
     */
    private static String orDefaultPath( final String p_path )
    {
        return ( p_path == null ) || ( p_path.isEmpty() )
               ? CConfiguration.class.getResource( "configuration.yaml" ).getFile()
               : p_path;
    }

    /**
     * creates the default configuration
     *
     * @return full path
     * @throws IOException on any io error
     */
    public static String createdefault() throws IOException
    {
        final String l_path = Stream.of(
            System.getProperty( "user.home" ),
            ".minitrafficsim"
        ).collect( Collectors.joining( File.separator ) );

        new File( l_path ).mkdirs();
        Files.copy(
            CConfiguration.class.getResourceAsStream(  "configuration.yaml"   ),
            FileSystems.getDefault().getPath( l_path + File.separator + "configuration.yaml" ),
            StandardCopyOption.REPLACE_EXISTING
        );

        return l_path;
    }

    /**
     * returns a configuration value
     *
     * @param p_path path of the element
     * @tparam T returning type
     * @return value or null
     */
    public final <T> T get( final String... p_path )
    {
        return recursivedescent( m_configuration, p_path );
    }

    /**
     * returns a configuration value or on not
     * existing the default value
     *
     * @param p_default default value
     * @param p_path path of the element
     * @tparam T returning type
     * @return value / default vaue
     */
    public final <T> T getOrDefault( final T p_default, final String... p_path )
    {
        final T l_result = recursivedescent( m_configuration, p_path );
        return l_result == null
               ? p_default
               : l_result;
    }


    /**
     * recursive descent
     *
     * @param p_map configuration map
     * @param p_path path
     * @tparam T returning type parameter
     * @return value
     */
    @SuppressWarnings( "unchecked" )
    private static <T> T recursivedescent( final Map<String, ?> p_map, final String... p_path )
    {
        if ( ( p_path == null ) || ( p_path.length == 0 ) )
            throw new RuntimeException( "path need not to be empty" );

        final Object l_data = p_map.get( p_path[0].toLowerCase( Locale.ROOT ) );
        return ( p_path.length == 1 ) || ( l_data == null )
               ? (T) l_data
               : (T) recursivedescent( (Map<String, ?>) l_data, Arrays.copyOfRange( p_path, 1, p_path.length ) );
    }
}
