/*
 * @cond LICENSE
 * ######################################################################################
 * # LGPL License                                                                       #
 * #                                                                                    #
 * # This file is part of the LightJason TrafficSimulation                              #
 * # Copyright (c) 2016-17, LightJason (info@lightjason.org)                            #
 * # This program is free software: you can redistribute it and/or modify               #
 * # it under the terms of the GNU Lesser General Public License as                     #
 * # published by the Free Software Foundation, either version 3 of the                 #
 * # License, or (at your option) any later version.                                    #
 * #                                                                                    #
 * # This program is distributed in the hope that it will be useful,                    #
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of                     #
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                      #
 * # GNU Lesser General Public License for more details.                                #
 * #                                                                                    #
 * # You should have received a copy of the GNU Lesser General Public License           #
 * # along with this program. If not, see http://www.gnu.org/licenses/                  #
 * ######################################################################################
 * @endcond
 */

package org.lightjason.trafficsimulation;

import org.lightjason.agentspeak.beliefbase.view.CViewMap;
import org.lightjason.agentspeak.beliefbase.view.IView;
import org.lightjason.agentspeak.common.IPath;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
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
     * name of the beliefbase
     */
    private static final String BELIEFBASE = "config";
    /**
     * map with configuration data
     */
    private final Map<String, Object> m_configuration = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );


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
                final InputStream l_stream = orDefaultPath( p_path );
        )
        {
            return this.set( new Yaml().load( l_stream ) );
        }
        catch ( final IOException l_exception )
        {
            throw new RuntimeException( l_exception );
        }
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
        return this.set( new Yaml().load( p_yaml ) );
    }


    /**
     * push data to the internal map
     *
     * @param p_data input data
     * @return self reference
     */
    @SuppressWarnings( "unchecked" )
    private CConfiguration set( final Object p_data )
    {
        final Map<String, ?> l_result = (Map<String, Object>) maptolowercase( p_data );
        if ( l_result != null )
        {
            m_configuration.clear();
            m_configuration.putAll( l_result );
        }
        return this;
    }


    /**
     * convert all map keys to lower-case
     *
     * @param p_value any value
     * @return object return
     */
    @SuppressWarnings( "unchecked" )
    private static Object maptolowercase( final Object p_value )
    {
        if ( !( p_value instanceof Map<?, ?> ) )
            return p_value;

        final Map<String, Object> l_map = new TreeMap<>( String.CASE_INSENSITIVE_ORDER );
        l_map.putAll(
                       ( (Map<String, Object>) p_value ).entrySet()
                                                       .parallelStream()
                                                       .collect( Collectors.toMap(
                                                           i -> i.getKey().toLowerCase(),
                                                           i -> maptolowercase( i.getValue() )
                                                       ) )
        );
        return l_map;
    }


    @Override
    public final int hashCode()
    {
        return 1;
    }

    @Override
    public boolean equals( final Object p_object )
    {
        return ( p_object != null ) && ( p_object instanceof CConfiguration ) && ( p_object.hashCode() == this.hashCode() );
    }

    @Override
    public final String toString()
    {
        return m_configuration.toString();
    }


    /**
     * set default path
     *
     * @param p_path path or null / empty
     * @return default path on empty or input path
     */
    private static InputStream orDefaultPath( final String p_path ) throws FileNotFoundException
    {
        return ( p_path == null ) || ( p_path.isEmpty() )
               ? CConfiguration.class.getResourceAsStream( "configuration.yaml" )
               : new FileInputStream( p_path );
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
    @SuppressWarnings( "unchecked" )
    public final <T> T get( final String... p_path )
    {
        return (T) recursivedescent( m_configuration, 0, p_path ).findFirst()
                                                              .map( Map.Entry::getValue )
                                                              .orElseThrow(
                                                                  () -> new RuntimeException(
                                                                            MessageFormat.format(
                                                                                "path {0] not found",
                                                                                Arrays.toString( p_path )
                                                                            ) )
                                                              );
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
    @SuppressWarnings( "unchecked" )
    public final <T> T getOrDefault( final T p_default, final String... p_path )
    {
        return (T) recursivedescent( m_configuration, 0, p_path ).findFirst().map( Map.Entry::getValue ).orElseGet( () -> p_default );
    }

    /**
     * returns a beliefbase view
     *
     * @param p_parent parent view
     * @return new view
     */
    public final IView view( final IView p_parent )
    {
        return new CViewMap( BELIEFBASE, m_configuration, p_parent );
    }


    /**
     * recursive descent of a given path
     *
     * @param p_map start map
     * @param p_index index of path
     * @param p_path path of the item
     * @return stream of items
     */
    @SuppressWarnings( "unchecked" )
    private static Stream<Map.Entry<String, Object>> recursivedescent( final Map<String, ?> p_map, final int p_index, final String... p_path )
    {
        final String l_key = p_path[p_index].toLowerCase( Locale.ROOT );
        final Object l_data =  p_map.get( l_key );

        return ( p_index < p_path.length - 1 )

               ? l_data instanceof Map<?, ?>
                 ? recursivedescent( (Map<String, Object>) l_data, p_index + 1, p_path )
                 : Stream.empty()

               : l_data instanceof Map<?, ?>
                 ? ( (Map<String, Object>) l_data ).entrySet().stream()
                 : Stream.of( new AbstractMap.SimpleImmutableEntry<>( l_key, l_data ) );
    }

    /**
     *
     * @param p_map map
     * @return stream of all items within the map
     * @todo fix path name with full path
     */
    @SuppressWarnings( "unchecked" )
    private static Stream<Map.Entry<String, Object>> mapstream( final Map<String, Object> p_map, final String p_prefix )
    {
        return p_map.entrySet()
                    .stream()
                    .flatMap( i -> i.getValue() instanceof Map<?, ?>
                                   ? mapstream( (Map<String, Object>) i.getValue(), p_prefix +  IPath.DEFAULTSEPERATOR + i.getKey() )
                                   : Stream.of( new AbstractMap.SimpleImmutableEntry<>( p_prefix +  IPath.DEFAULTSEPERATOR + i.getKey(), i.getValue() ) )
                    );
    }

}
