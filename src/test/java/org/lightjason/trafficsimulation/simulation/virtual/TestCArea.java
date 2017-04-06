package org.lightjason.trafficsimulation.simulation.virtual;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.junit.Before;
import org.junit.Test;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.generator.IAgentGenerator;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.CConfiguration;
import org.lightjason.trafficsimulation.IBaseTest;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;
import org.lightjason.trafficsimulation.simulation.environment.EDirection;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * test area
 */
public class TestCArea extends IBaseTest
{
    /**
     * list of areas
     */
    private List<CArea> m_areas;

    /**
     * agent generator
     */
    private IAgentGenerator m_agentgenerator;

    /**
     * initialize area
     */
    @Before
    public final void initialize()
    {
        m_areas = new LinkedList<>();

        m_areas.add( this.generate( "src/test/resources/area.asl",
            EObjectFactory.AREA,
            true,
            EArea.DIRECTLANE,
            Stream.of( EDirection.EAST ),
            new DenseDoubleMatrix1D( new double[]{0, 0} ) )
        );

        m_areas.add( this.generate( "src/test/resources/area.asl",
            EObjectFactory.AREA,
            true,
            EArea.SIDEWALK,
            Stream.of( EDirection.NORTH, EDirection.NORTHWEST, EDirection.WEST ),
            new DenseDoubleMatrix1D( new double[]{10, 10} ) )
        );

    }

    /**
     * get area configuration from yaml
     * @todo why doesn't ".raw()" work?
     */
    @SuppressWarnings( {"SimplifyStreamApiCallChains", "unchecked", "Convert2MethodRef", "WeakerAccess"} )
    @Before
    public final void initializeFromConfiguration()
    {
        m_areas = new LinkedList<>();
        final List<Map<String, ?>> l_areaconfiguration = CConfiguration.INSTANCE.get( "area" );

        final Set<IAction> l_actions = org.lightjason.agentspeak.common.CCommon.actionsFromPackage().collect( Collectors.toSet() );

        try
            (
                final FileInputStream l_stream = new FileInputStream( "src/test/resources/area.asl" );
            )
        {
            m_agentgenerator = EObjectFactory.AREA.generate(
                l_stream,
                l_actions.stream(),
                IAggregation.EMPTY,
                m_environment );
        }
        catch ( final Exception l_exeption )
        {
            l_exeption.printStackTrace();
            assertTrue( false );
        }

            l_areaconfiguration.stream()
            .forEach( i ->
                {
                    try
                    {
                        m_areas.add(
                            (CArea) m_agentgenerator
                            .generatesingle(
                                true,
                                EArea.from( (String) i.get( "type" ) ),
                                ( (List<String>) i.get( "directions" ) ).stream()
                                    .map( j -> EDirection.from( j ) ),
                                new DenseDoubleMatrix1D(
                                    Stream.concat(
                                        ( (List<Integer>) i.get( "leftbottom" ) ).stream(),
                                        ( (List<Integer>) i.get( "righttop" ) ).stream() )
                                        .mapToDouble( k -> k )
                                        .toArray() )
                                )

                        );
                    }
                    catch ( final Exception l_exeption )
                    {
                        l_exeption.printStackTrace();
                        assertTrue( false );
                    }
                }
            );
    }

    /**
     * area test
     *
     * @throws Exception on execution error
     */
    @SuppressWarnings( {"SimplifyStreamApiCallChains", "ConstantConditions"} )
    @Test
    public final void test() throws Exception
    {
        m_areas.stream()
            .forEach( area ->
                {
                    try
                    {
                        area.call();
                    }
                    catch ( final Exception l_exception )
                    {
                        l_exception.printStackTrace();
                        assertTrue( false );
                    }
                    System.out.println( area.literal().collect( Collectors.toSet() ) );
                }
            );
    }

    /**
     * main method
     *
     * @param p_args args
     *
     * @throws Exception on any error
     */
    public static void main( final String[] p_args ) throws Exception
    {
        final TestCArea l_test = new TestCArea();

        l_test.initializeenvironment();
        //l_test.initialize();
        //l_test.test();

        l_test.loadconfiguration();
        l_test.initializeFromConfiguration();
        l_test.test();
    }
}
