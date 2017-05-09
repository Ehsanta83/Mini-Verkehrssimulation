package org.lightjason.trafficsimulation.simulation.environment;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;
import cern.colt.matrix.impl.SparseObjectMatrix2D;
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.CCommon;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;
import org.lightjason.trafficsimulation.simulation.IObject;
import org.lightjason.trafficsimulation.simulation.algorithm.routing.IRouting;
import org.lightjason.trafficsimulation.simulation.movable.IMoveable;
import org.lightjason.trafficsimulation.simulation.virtual.CArea;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * environment class
 */
public final class CEnvironment extends IBaseAgent<IEnvironment> implements IEnvironment
{
    private static final String FUNCTOR = "environment";

    /**
     * routing algorithm
     */
    private final IRouting m_routing;
    /**
     * matrix with area positions
     */
    private final ObjectMatrix2D m_metainformation;
    /**
     * matrix with agents positions
     */
    private final ObjectMatrix2D m_physical;
    /**
     * map of the areas
     */
    private final Map<String, CArea> m_areas;

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_cellrows number of row cells
     * @param p_cellcolumns number of column cells
     * @param p_cellsize cell size
     * @bug fix cell size to floating-point
     */
    private CEnvironment(
        final IAgentConfiguration<IEnvironment> p_configuration,
        final int p_cellrows, final int p_cellcolumns, final double p_cellsize, final IRouting p_routing
    )
    {
        super( p_configuration );

        if ( ( p_cellcolumns < 1 ) || ( p_cellrows < 1 ) || ( p_cellsize < 1 ) )
            throw new IllegalArgumentException( "environment size must be greater or equal than one" );

        m_routing = p_routing;
        m_metainformation = new SparseObjectMatrix2D( p_cellrows, p_cellcolumns );
        m_physical = new SparseObjectMatrix2D( p_cellrows, p_cellcolumns );
        m_areas = new ConcurrentHashMap<>();
    }


    @Override
    public final List<DoubleMatrix1D> route( final DoubleMatrix1D p_start, final DoubleMatrix1D p_end )
    {
        return m_routing.route( m_physical, p_start, p_end );
    }

    @Override
    public final double routestimatedtime( final Stream<DoubleMatrix1D> p_route, final double p_speed )
    {
        return m_routing.estimatedtime( p_route, p_speed );
    }

    /**
     * move a moveable
     *
     * @param p_moveable object, which should be moved (must store the current position)
     * @param p_newposition new position
     * @return object reference
     *
     * @bug check parameter and generics
     */
    @Override
    public final synchronized IMoveable move( final IMoveable p_moveable, final DoubleMatrix1D p_newposition )
    {
        /*
        if ( p_moveable.movable( m_physical, p_newposition ) )



        final Supplier<Stream<Pair<Integer, Integer>>> l_newcells = () -> CCommon.inttupelstream(
            (int) ( p_newposition.get( 0 ) - p_newposition.get( 2 ) / 2 ),
            (int) ( p_newposition.get( 0 ) + p_newposition.get( 2 ) / 2 ),
            (int) ( p_newposition.get( 1 ) - p_newposition.get( 3 ) / 2 ),
            (int) ( p_newposition.get( 1 ) + p_newposition.get( 3 ) / 2 )
        );
        l_newcells.get()
            .filter( i -> ( m_physical.getQuick( i.getLeft(), i.getRight() ) != null ) && ( m_physical.getQuick( i.getLeft(), i.getRight() ) != p_moveable ) )
            .findFirst()
            .orElseThrow( () -> new RuntimeException( "Can't move: new position occupied." ) );

        CCommon.inttupelstream(
            (int) ( p_moveable.position().get( 0 ) - p_moveable.position().get( 2 ) / 2 ),
            (int) ( p_moveable.position().get( 0 ) + p_moveable.position().get( 2 ) / 2 ),
            (int) ( p_moveable.position().get( 1 ) - p_moveable.position().get( 3 ) / 2 ),
            (int) ( p_moveable.position().get( 1 ) + p_moveable.position().get( 3 ) / 2 )
        ).forEach( i -> m_physical.setQuick( i.getLeft(), i.getRight(), null ) );

        l_newcells.get().forEach( i -> m_physical.setQuick( i.getLeft(), i.getRight(), p_moveable ) );

        p_moveable.position().setQuick( 0, p_newposition.getQuick( 0 ) );
        p_moveable.position().setQuick( 1, p_newposition.getQuick( 1 ) );
*/
        m_areas.entrySet().parallelStream().forEach( entry -> entry.getValue().addPhysical( p_moveable ) );
        return p_moveable;
    }

    @Override
    public final synchronized IObject get( final DoubleMatrix1D p_position )
    {
        return (IObject) m_physical.getQuick( (int) p_position.get( 0 ), (int) p_position.get( 1 ) );
    }

    /**
     * remove object
     *
     * @param p_object element
     * @return
     *
     * @bug check parameter
     */
    @Override
    public final synchronized IObject remove( final IObject p_object )
    {
        //final DoubleMatrix1D l_position = this.clip( new DenseDoubleMatrix1D( p_object.position().toArray() ) );
        //m_moveablegrid.set( (int) l_position.get( 0 ), (int) l_position.get( 1 ), null );

        return p_object;
    }

    @Override
    public final synchronized boolean empty( final DoubleMatrix1D p_position )
    {
        return m_physical.getQuick( (int) p_position.getQuick( 0 ), (int) p_position.getQuick( 1 ) ) == null;
    }

    @Override
    public final Stream<ILiteral> literal( final IObject<?>... p_object )
    {
        return this.literal( Arrays.stream( p_object ) );
    }

    @Override
    public final Stream<ILiteral> literal( final Stream<IObject<?>> p_object )
    {
        return Stream.of();
    }

    @Override
    public final String name()
    {
        return FUNCTOR;
    }

    @Override
    public DoubleMatrix1D position()
    {
        return null;
    }

    /**
     * value clipping
     *
     * @param p_value value
     * @param p_max maximum
     * @return modifed value
     */
    private static double clip( final double p_value, final double p_max )
    {
        return Math.max( Math.min( p_value, p_max - 1 ), 0 );
    }

    /**
     * add an area to the environment
     *
     * @param p_file asl file
     * @param p_data data
     * @throws Exception any error
     * @todo change actions ?
     */
    @IAgentActionFilter
    @IAgentActionName( name = "addarea" )
    private void addArea( final String p_file, final Object... p_data ) throws Exception
    {
        final CArea l_area = EObjectFactory.AREA.generate(
            CEnvironment.class.getResourceAsStream( p_file ),
            org.lightjason.agentspeak.common.CCommon.actionsFromPackage(),
            IAggregation.EMPTY)
            .generatesingle( p_data )
            .raw();
        m_areas.put( l_area.name(), l_area );
    }

    /**
     * generator of the environment
     */
    public static final class CGenerator extends IBaseEnvironmentGenerator
    {

        public CGenerator( final InputStream p_stream, final Stream<IAction> p_actions,
                           final IAggregation p_aggregation
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation );
        }

        @Override
        protected final IEnvironment generate( final IAgentConfiguration<IEnvironment> p_configuration, final int p_rows, final int p_columns,
                                               final double p_cellsize,
                                               final IRouting p_routing
        )
        {
            return new CEnvironment(
                                     p_configuration,
                                     p_rows,
                                     p_columns,
                                     p_cellsize,
                                     p_routing
            );
        }
    }
}
