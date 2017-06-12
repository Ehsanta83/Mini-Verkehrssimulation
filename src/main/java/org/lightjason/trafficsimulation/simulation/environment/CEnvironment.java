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
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.trafficsimulation.CConfiguration;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;
import org.lightjason.trafficsimulation.simulation.IObject;
import org.lightjason.trafficsimulation.simulation.algorithm.routing.IRouting;
import org.lightjason.trafficsimulation.simulation.movable.IMoveable;
import org.lightjason.trafficsimulation.simulation.virtual.CArea;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;


/**
 * environment class
 */
@IAgentAction
public final class CEnvironment extends IBaseAgent<IEnvironment> implements IEnvironment
{
    /**
     * serial id
     */
    private static final long serialVersionUID = -993173756668246918L;
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
    private final Map<String, CArea> m_areas;


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
        m_areas = new ConcurrentHashMap<>();
    }

    @Override
    public final List<DoubleMatrix1D> route( final DoubleMatrix1D p_start, final DoubleMatrix1D p_end )
    {
        return m_routing.route( m_objectgrid, p_start, p_end );
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
    public final synchronized IMoveable<?> move( final IMoveable<?> p_moveable, final DoubleMatrix1D p_newposition )
    {
        if ( !p_moveable.moveable( m_objectgrid, p_newposition ) )
            return p_moveable;

        p_moveable.move( m_objectgrid, p_newposition );
        m_areas.entrySet().parallelStream().forEach( entry -> entry.getValue().addPhysical( p_moveable ) );
        return p_moveable;
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
