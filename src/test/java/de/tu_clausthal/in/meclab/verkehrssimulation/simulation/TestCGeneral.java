package de.tu_clausthal.in.meclab.verkehrssimulation.simulation;

import java.util.Collections;
import java.util.logging.LogManager;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * general tests
 * @deprecated ??
 */
@Deprecated
public final class TestCGeneral
{
    static
    {
        LogManager.getLogManager().reset();
    }

    private TestCGeneral()
    {
    }

    /**
     * main method
     *
     * @param p_args args
     */
    public static void main( final String[] p_args )
    {
        IntStream.range( 0, 10 ).forEach( i ->
            System.out.println(
                Stream.of( "man", "woman" )
                    .collect( Collectors.collectingAndThen( Collectors.toList(), collected ->
                    {
                        Collections.shuffle( collected );
                        return collected.stream();
                    } ) ).collect( Collectors.toList() ).get( 0 )
            )
        );

    }
}
