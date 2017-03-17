package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * general tests
 * 
 */
public final class TestGeneral
{
    public static void main( final String[] p_args )
    {
        IntStream.range(0, 10).forEach( i ->
                System.out.println(
                        Stream.of( "man", "woman" )
                                .collect( Collectors.collectingAndThen( Collectors.toList(), collected ->
                                        {
                                            Collections.shuffle( collected );
                                            return collected.stream();
                                        }
                                ) ).collect( Collectors.toList() ).get(0)
                )
        );

    }
}
