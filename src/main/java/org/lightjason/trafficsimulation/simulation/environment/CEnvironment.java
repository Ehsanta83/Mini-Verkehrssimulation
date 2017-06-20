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


package org.lightjason.trafficsimulation.simulation.environment;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.SparseObjectMatrix2D;
import org.dyn4j.collision.narrowphase.Gjk;
import org.dyn4j.collision.narrowphase.NarrowphaseDetector;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.trafficsimulation.CConfiguration;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;
import org.lightjason.trafficsimulation.simulation.IBaseObject;
import org.lightjason.trafficsimulation.simulation.IObject;
import org.lightjason.trafficsimulation.simulation.algorithm.routing.IRouting;
import org.lightjason.trafficsimulation.simulation.virtual.CArea;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * environment class
 */
@IAgentAction
public final class CEnvironment extends IBaseAgent<IEnvironment> implements IEnvironment
{
    /**
     * collision detector
     */
    public static final NarrowphaseDetector COLLISIONDETECTOR = new Gjk();
    /**
     * serial id
     */
    private static final long serialVersionUID = -2422681046299478212L;
    /**
     * literal name
     */
    private static final String FUNCTOR = "environment";
    /**
     * routing algorithm
     */
    private final IRouting m_routing;
    /**
     * matrix with agents positions
     */
    private final ObjectMatrix2D m_objectgrid;
    /**
     * map of the areas
     */
    private final Map<String, CArea> m_areas = new ConcurrentHashMap<>();
    /**
     * map of the objects
     */
    private final Map<String, IBaseObject<?>> m_objects = new ConcurrentHashMap<>();


    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_column number of column cells
     * @param p_row number of row cells
     * @param p_size cell size
     * @bug fix cell size to floating-point
     */
    private CEnvironment(
        final IAgentConfiguration<IEnvironment> p_configuration,
        final int p_column, final int p_row, final double p_size, final IRouting p_routing
    )
    {
        super( p_configuration );

        if ( ( p_column < 1 ) || ( p_row < 1 ) || ( p_size < 1 ) )
            throw new IllegalArgumentException( "environment size must be greater or equal than one" );

        m_routing = p_routing;
        m_objectgrid = new SparseObjectMatrix2D( p_row, p_column );
    }

    /**
     * route
     * @param p_start start position
     * @param p_end target position
     * @return route list
     * @bug just for test before implementing routing algorithm
     */
    @Override
    public final List<DoubleMatrix1D> route( final DoubleMatrix1D p_start, final DoubleMatrix1D p_end )
    {
        final List<DoubleMatrix1D> l_route = new ArrayList<>();
        l_route.add( p_end );
        return l_route;
        //return m_routing.route( m_objectgrid, p_start, p_end );
    }

    @Override
    public final double routestimatedtime( final Stream<DoubleMatrix1D> p_route, final double p_speed )
    {
        return m_routing.estimatedtime( p_route, p_speed );
    }

    /**
     * move a moveable
     *
     * @param p_object object, which should be moved (must store the current position)
     * @param p_newposition new position
     * @return object reference
     *
     * @todo implement moving in objectgrid
     * @bug check parameter and generics
     * @bug change moving out implementation
     */
    @Override
    public final synchronized IBaseObject<?> move( final IBaseObject<?> p_object, final DoubleMatrix1D p_newposition, final EDirection p_direction )
    {
        if ( p_newposition.get( 0 ) < 0 || p_newposition.get( 0 ) > m_objectgrid.columns()
            || p_newposition.get( 1 ) < 0 || p_newposition.get( 1 ) > m_objectgrid.rows() )
        {
            throw new RuntimeException( MessageFormat.format( "new goal position ([{0}, {1}]) is out of environment.", p_newposition.get( 0 ), p_newposition.get( 1 ) ) );
        }

        final DoubleMatrix1D l_oldposition = p_object.position().copy();
        final double l_xtranslate = p_newposition.getQuick( 0 ) - l_oldposition.getQuick( 0 );
        final double l_ytranslate = p_newposition.getQuick( 1 ) - l_oldposition.getQuick( 1 );
        p_object.position().setQuick( 0, p_newposition.getQuick( 0 ) );
        p_object.position().setQuick( 1, p_newposition.getQuick( 1 ) );
        p_object.convex().translate( l_xtranslate, l_ytranslate );
        m_objects.entrySet().stream()
            .filter( i -> !p_object.equals( i.getValue() ) )
            .collect( Collectors.toMap( Map.Entry::getKey, Map.Entry::getValue ) )
            .forEach( ( i, j ) ->
            {
                if ( p_object.intersects( j ) )
                {
                    p_object.position().setQuick( 0, l_oldposition.getQuick( 0 ) );
                    p_object.position().setQuick( 1, l_oldposition.getQuick( 1 ) );
                    p_object.convex().translate( -l_xtranslate, -l_ytranslate );
                    throw new RuntimeException( "Collision detected!" );
                }
            }
        );

        m_objectgrid.setQuick( (int) l_oldposition.getQuick( 0 ), (int) l_oldposition.getQuick( 1 ), null );
        m_objectgrid.setQuick( (int) p_newposition.getQuick( 0 ), (int) p_newposition.getQuick( 1 ), p_object );

        m_areas.entrySet().parallelStream().forEach( entry -> entry.getValue().addPhysical( p_object ) );
        return p_object;
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
    public final synchronized IObject<?> remove( final IObject<?> p_object )
    {
        //final DoubleMatrix1D l_position = this.clip( new DenseDoubleMatrix1D( p_object.position().toArray() ) );
        //m_moveablegrid.set( (int) l_position.get( 0 ), (int) l_position.get( 1 ), null );

        return p_object;
    }

    @Override
    public final synchronized boolean empty( final DoubleMatrix1D p_position )
    {
        return m_objectgrid.getQuick( (int) p_position.getQuick( 0 ), (int) p_position.getQuick( 1 ) ) == null;
    }


    @Override
    public final synchronized IObject<?> get( final DoubleMatrix1D p_position )
    {
        return (IObject) m_objectgrid.getQuick( (int) p_position.get( 0 ), (int) p_position.get( 1 ) );
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
    public final DoubleMatrix1D position()
    {
        return new DenseDoubleMatrix1D( new double[]{m_objectgrid.columns(), m_objectgrid.rows()} );
    }

    /**
     * add an object to the environment
     * @param p_object object
     * @todo implementing if another object is in this position and if the object is more than one cell
     */
    @Override
    public void addobject( final IBaseObject<?> p_object )
    {
        m_objectgrid.setQuick( (int) p_object.position().getQuick( 0 ), (int) p_object.position().getQuick( 1 ), p_object );
        m_objects.put( p_object.name(), p_object );

    }

    /**
     * add an area to the environment
     *
     * @param p_file asl file
     * @param p_data data
     * @throws Exception any error
     * @todo generator must be refactor for better file access
     * @todo modify exception handling with agent plan failure
     * @todo create better action naming schema
     */
    @IAgentActionFilter
    @IAgentActionName( name = "env/area/add" )
    private void addarea( final String p_file, final Object... p_data )
    {
        try
            (
                final FileInputStream l_stream = new FileInputStream( p_file );
            )
        {
            final CArea l_area = EObjectFactory.AREA.generate(
                l_stream,
                org.lightjason.agentspeak.common.CCommon.actionsFromPackage()
            )
                                                    .generatesingle( p_data )
                                                    .raw();
            m_areas.put( l_area.name(), l_area );
        }
        catch ( final Exception l_exception )
        {
            l_exception.printStackTrace();
        }
    }


    /**
     * removes an area
     *
     * @todo must be implemented
     */
    @IAgentActionFilter
    @IAgentActionName( name = "env/area/remove" )
    private void removearea()
    {
    }

    /**
     * generator of the environment
     */
    public static final class CGenerator extends IBaseEnvironmentGenerator
    {

        public CGenerator( final InputStream p_stream, final Stream<IAction> p_actions ) throws Exception
        {
            super( p_stream, p_actions, CEnvironment.class );
        }

        @Override
        @SuppressWarnings( "unchecked" )
        protected final IEnvironment generate( final IAgentConfiguration<IEnvironment> p_configuration, final int p_rows, final int p_columns,
                                               final double p_cellsize,
                                               final IRouting p_routing
        )
        {
            final IEnvironment l_agent = new CEnvironment( p_configuration, p_columns, p_rows, p_cellsize, p_routing );
            l_agent.beliefbase().add( CConfiguration.INSTANCE.view( l_agent.beliefbase() ) );
            return l_agent;
        }
    }
}
