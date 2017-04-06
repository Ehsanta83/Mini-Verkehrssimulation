package org.lightjason.trafficsimulation.simulation;

import org.lightjason.trafficsimulation.CCommon;

import java.util.Collections;
import java.util.logging.LogManager;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * this class is defined for testing a code or a function in java (general). It is not related to simulation.
 *
 * @deprecated did we need this?
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
        CCommon.inttupelstream( 37, 64, 35, 36 ).forEach( System.out::println );

    }
}
