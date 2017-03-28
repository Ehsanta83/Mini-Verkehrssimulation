package de.tu_clausthal.in.meclab.verkehrssimulation;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * common class
 *
 * @bug check
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
     * creates an int-pair-stream of an element
     *
     * @param p_object object but position must be return a vector with 4 elements
     * @return int-pair-stream
     *
    public static Stream<Pair<Integer, Integer>> inttupelstream( final ILane p_object )
    {
    return CCommon.inttupelstream(
    (int) p_object.position().get( 0 ), (int) ( p_object.position().get( 0 ) + p_object.position().get( 2 ) - 1 ),
    (int) p_object.position().get( 1 ), (int) ( p_object.position().get( 1 ) + p_object.position().get( 3 ) - 1 )
    );
    }
     */


    /**
     * creates an int-pair-stream
     *
     * @param p_firststart start value of first component
     * @param p_firstend end (inclusive) of first component
     * @param p_secondstart start value of second component
     * @param p_secondend end (inclusive) of second component
     * @return int-pair-sream
     */
    public static Stream<Pair<Integer, Integer>> inttupelstream( final int p_firststart, final int p_firstend, final int p_secondstart, final int p_secondend )
    {
        final Supplier<IntStream> l_inner = () -> IntStream.rangeClosed( p_secondstart, p_secondend );
        return IntStream.rangeClosed( p_firststart, p_firstend ).boxed().flatMap( i -> l_inner.get().mapToObj( j -> new ImmutablePair<>( i, j ) ) );
    }

    /**
     * returns a file from a resource e.g. Jar file
     *
     * @param p_file file
     * @return URL of file or null
     *
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
     *
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
