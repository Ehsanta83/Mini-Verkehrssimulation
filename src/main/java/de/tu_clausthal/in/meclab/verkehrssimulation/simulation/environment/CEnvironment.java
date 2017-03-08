
package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.SparseObjectMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import de.tu_clausthal.in.meclab.verkehrssimulation.CCommon;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.algorithm.routing.IRouting;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual.IBaseWay;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * environment class
 */
public class CEnvironment implements IEnvironment
{
    /**
     * logger
     */
    private static final Logger LOGGER = Logger.getLogger( CEnvironment.class.getName() );
    /**
     * algebra object
     */
    private static final Algebra ALGEBRA = new Algebra();
    /**
     * routing algorithm
     */
    private final IRouting m_routing;
    /**
     * row number
     */
    private final int m_row;
    /**
     * column number
     */
    private final int m_column;
    /**
     * cell size
     */
    private final int m_cellsize;
    /**
     * matrix with ways positions
     */
    private final ObjectMatrix2D m_lanespositions;
    /**
     * matrix with agents positions
     */
    private final ObjectMatrix2D m_agentspostions;


    /**
     * create environment
     *  @param p_cellrows number of row cells
     * @param p_cellcolumns number of column cells
     * @param p_cellsize cell size
     * @param p_ways list of all ways
     */
    public CEnvironment( final int p_cellrows, final int p_cellcolumns, final int p_cellsize, final IRouting p_routing, final List<? extends IBaseWay> p_ways )
    {
        if ( ( p_cellcolumns < 1 ) || ( p_cellrows < 1 ) || ( p_cellsize < 1 ) )
            throw new IllegalArgumentException( "environment size must be greater or equal than one" );

        m_row = p_cellrows;
        m_column = p_cellcolumns;
        m_cellsize = p_cellsize;
        m_routing = p_routing;
        m_lanespositions = new SparseObjectMatrix2D( m_row, m_column );
        m_agentspostions = new SparseObjectMatrix2D( m_row, m_column );

        p_ways.forEach( i -> CCommon.inttupelstream( i ).forEach( j -> m_lanespositions.setQuick( j.getLeft(), j.getRight(), i ) ) );
    }

    @Override
    public final IEnvironment call()
    {
        return this;
    }

    @Override
    public final int row()
    {
        return m_row;
    }

    @Override
    public final int column()
    {
        return m_column;
    }

    @Override
    public final int cellsize()
    {
        return m_cellsize;
    }

    // --- grid-access (routing & position) --------------------------------------------------------------------------------------------------------------------
    //ToDo: change all methods with two matrices

    @Override
    public final List<DoubleMatrix1D> route( final DoubleMatrix1D p_start, final DoubleMatrix1D p_end )
    {
        return m_routing.route(m_lanespositions, p_start, p_end );
    }

    @Override
    public final double routestimatedtime( final Stream<DoubleMatrix1D> p_route, final double p_speed )
    {
        return m_routing.estimatedtime( p_route, p_speed );
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public final synchronized IObject move( final IObject p_object, final DoubleMatrix1D p_position )
    {
        final DoubleMatrix1D l_position = this.clip( new DenseDoubleMatrix1D( p_position.toArray() ) );

        // check of the target position is free, if not return object, which blocks the cell
        final IObject l_object = (IObject) m_agentspostions.getQuick( (int) l_position.getQuick( 0 ), (int) l_position.getQuick( 1 ) );
        if ( l_object != null )
            return l_object;

        // cell is free, move the position and return updated object
        m_agentspostions.set( (int) p_object.position().get( 0 ), (int) p_object.position().get( 1 ), null );
        m_agentspostions.set( (int) l_position.getQuick( 0 ), (int) l_position.getQuick( 1 ), p_object );
        p_object.position().setQuick( 0, l_position.getQuick( 0 ) );
        p_object.position().setQuick( 1, l_position.getQuick( 1 ) );

        return p_object;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public final synchronized IObject get( final DoubleMatrix1D p_position )
    {
        return (IObject) m_agentspostions.getQuick( (int) CEnvironment.clip( p_position.get( 0 ), m_row ), (int) CEnvironment.clip( p_position.get( 1 ), m_column ) );
    }

    @Override
    public final synchronized IObject remove( final IObject p_object )
    {
        final DoubleMatrix1D l_position = this.clip( new DenseDoubleMatrix1D( p_object.position().toArray() ) );
        m_agentspostions.set( (int) l_position.get( 0 ), (int) l_position.get( 1 ), null );

        return p_object;
    }

    @Override
    public final synchronized boolean empty( final DoubleMatrix1D p_position )
    {
        final DoubleMatrix1D l_position = this.clip( new DenseDoubleMatrix1D( p_position.toArray() ) );
        return m_agentspostions.getQuick( (int) l_position.getQuick( 0 ), (int) l_position.getQuick( 1 ) ) == null;
    }

    @Override
    public final boolean isinside( final DoubleMatrix1D p_position )
    {
        return ( p_position.getQuick( 0 ) >= 0 )
            && ( p_position.getQuick( 1 ) >= 0 )
            && ( p_position.getQuick( 0 ) < m_row )
            && ( p_position.getQuick( 1 ) < m_column );
    }

    @Override
    public final DoubleMatrix1D clip( final DoubleMatrix1D p_position )
    {
        // clip position values if needed
        p_position.setQuick( 0, CEnvironment.clip( p_position.getQuick( 0 ), m_row ) );
        p_position.setQuick( 1, CEnvironment.clip( p_position.getQuick( 1 ), m_column ) );

        return p_position;
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

    // --- visualization ---------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public final TiledMap map()
    {
        return new TmxMapLoader().load( CCommon.PACKAGEPATH + "background.tmx" );
    }
}