package org.lightjason.trafficsimulation.simulation.virtual;

import cern.colt.matrix.ObjectMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.junit.Assert;
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
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * test area
 */
public class TestCArea extends IBaseTest
{

    /**
     * agent generator
     */
    private IAgentGenerator m_agentgenerator;

    /**
     * initialize
     * @throws Exception on any error
     */
    @Before
    public final void initialize() throws Exception
    {
        initializeenvironment();

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
            Assert.assertTrue( false );
        }
    }

    /**
     * test agent call
     *
     * @throws Exception on execution error
     */
    @Test
    public final void testagentcall() throws Exception
    {
        final List<CArea> l_areas = new LinkedList<>();

        l_areas.add( (CArea) m_agentgenerator.generatesingle(
            new DenseDoubleMatrix1D( new double[]{0, 0, 1, 1} ),
            true,
            EArea.DIRECTLANE,
            Stream.of( EDirection.EAST )
             )
        );

        l_areas.add( (CArea) m_agentgenerator.generatesingle(
            new DenseDoubleMatrix1D( new double[]{10, 10, 11, 11} ),
            true,
            EArea.SIDEWALK,
            Stream.of( EDirection.NORTH, EDirection.NORTHWEST, EDirection.WEST ) )
        );
        l_areas.forEach( area ->
                {
                    try
                    {
                        area.call();
                    }
                    catch ( final Exception l_exception )
                    {
                        l_exception.printStackTrace();
                        Assert.assertTrue( false );
                    }
                    System.out.println( area.literal().collect( Collectors.toSet() ) );
                }
            );
    }

    /**
     * get area configuration from yaml
     * @todo why doesn't ".raw()" work?
     */
    @Test
    public final void testInitializeFromConfiguration()
    {
        loadconfiguration();

        final List<CArea> l_areas = new LinkedList<>();
        final List<Map<String, ?>> l_areaconfiguration = CConfiguration.INSTANCE.get( "area" );

        l_areaconfiguration.forEach( i ->
                {
                    try
                    {
                        l_areas.add(
                            (CArea) m_agentgenerator.generatesingle(
                                new DenseDoubleMatrix1D(
                                    Stream.concat(
                                        ( (List<Integer>) i.get( "leftbottom" ) ).stream(),
                                        ( (List<Integer>) i.get( "righttop" ) ).stream() )
                                        .mapToDouble( k -> k )
                                        .toArray() ),
                                true,
                                EArea.from( (String) i.get( "type" ) ),
                                ( (List<String>) i.get( "directions" ) ).stream()
                                    .map( j -> EDirection.from( j ) )
                            )
                        );
                    }
                    catch ( final Exception l_exeption )
                    {
                        l_exeption.printStackTrace();
                        Assert.assertTrue( false );
                    }
                }
            );
    }

    /**
     * test overlapping two areas
     */
    @Test
    public final void testPositionInEnvironment()
    {
        final CArea l_area1 =  (CArea) m_agentgenerator.generatesingle(
            new DenseDoubleMatrix1D( new double[]{37, 35, 64, 36} ),
            true,
            EArea.DIRECTLANE,
            Stream.of( EDirection.EAST ) );

        // area2 has overlapping with area1
        try
        {
            final CArea l_area2 =  (CArea) m_agentgenerator.generatesingle(
                new DenseDoubleMatrix1D( new double[]{37, 36, 38, 37} ),
                true,
                EArea.DIRECTLANE,
                Stream.of( EDirection.EAST ) );
            Assert.fail( "Expected an RuntimeException to be thrown" );
        }
        catch ( final RuntimeException l_exception )
        {
        }

        // area 3 has no overlapping with area 1
        final CArea l_area3 =  (CArea) m_agentgenerator.generatesingle(
            new DenseDoubleMatrix1D( new double[]{37, 31, 64, 32} ),
            true,
            EArea.DIRECTLANE,
            Stream.of( EDirection.EAST ) );
    }

    /**
     * test area grid content
     */
    @Test
    public final void testAreaGridContent()
    {
        m_agentgenerator.generatesingle(
            new DenseDoubleMatrix1D( new double[]{1, 1, 9, 9} ),
            true,
            EArea.SIDEWALK,
            Stream.of( EDirection.EAST ) );
        final ObjectMatrix2D l_areagrid = m_environment.areagrid();
        IntStream.rangeClosed( 1, l_areagrid.rows() ).forEach( i ->
        {
            IntStream.rangeClosed( 1, l_areagrid.columns() ).forEach( j ->
            {
                if ( i <= 9 && j <= 9 &&  !( l_areagrid.getQuick( i, j ) instanceof CArea ) )
                {
                    Assert.assertTrue( false );
                }
                if ( ( i > 9 || j > 9 ) && ( l_areagrid.getQuick( i, j ) != null ) )
                {
                    Assert.assertTrue( false );
                }
                System.out.println( "[" + i + ", " + j + "]: " + l_areagrid.getQuick( i, j ) );
            } );
        } );
    }

}
