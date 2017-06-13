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

package org.lightjason.trafficsimulation.simulation;

import cern.colt.matrix.DoubleMatrix1D;
import org.apache.commons.lang3.tuple.Pair;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Transform;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.beliefbase.CBeliefbasePersistent;
import org.lightjason.agentspeak.beliefbase.IBeliefbaseOnDemand;
import org.lightjason.agentspeak.beliefbase.storage.CMultiStorage;
import org.lightjason.agentspeak.beliefbase.storage.CSingleStorage;
import org.lightjason.agentspeak.beliefbase.view.IView;
import org.lightjason.agentspeak.common.CCommon;
import org.lightjason.agentspeak.configuration.CDefaultAgentConfiguration;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.execution.IVariableBuilder;
import org.lightjason.agentspeak.language.fuzzy.operator.IFuzzyBundle;
import org.lightjason.agentspeak.language.instantiable.plan.IPlan;
import org.lightjason.agentspeak.language.instantiable.rule.IRule;
import org.lightjason.agentspeak.language.unify.IUnifier;
import org.lightjason.trafficsimulation.simulation.collision.IBoundingBox;
import org.lightjason.trafficsimulation.simulation.environment.CEnvironment;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;
import org.lightjason.trafficsimulation.ui.CHTTPServer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * base agent object
 *
 * @param <T> IObject
 */
@IAgentAction
public abstract class IBaseObject<T extends IObject<?>> extends IBaseAgent<T> implements IObject<T>, IBoundingBox
{
    /**
     * serial id
     */
    private static final long serialVersionUID = -5112452275211959224L;
    /**
     * the convex of the object
     */
    protected AtomicReference<Convex> m_convex = new AtomicReference<>();
    /**
     * the transformation matrix of the object
     */
    protected Transform m_transform = new Transform();
    /**
     * current position of the agent
     */
    protected final DoubleMatrix1D m_position;
    /**
     * environment reference
     */
    protected final IEnvironment m_environment;
    /**
     * functor definition
     */
    private final String m_functor;
    /**
     * name of the object
     */
    private final String m_name;
    /**
     * reference to external beliefbase
     */
    private final IView<T> m_external;


    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment environment reference
     * @param p_functor functor of the object literal
     * @param p_name name of the object
     */
    @SuppressWarnings( "unchecked" )
    protected IBaseObject( final IAgentConfiguration<T> p_configuration, final IEnvironment p_environment,
                           final String p_functor, final String p_name, final DoubleMatrix1D p_position,
                           final Convex p_convex )
    {
        super( p_configuration );
        m_functor = p_functor;
        m_name = p_name;
        m_environment = p_environment;
        m_position = p_position;
        m_convex.set( p_convex );
        m_convex.get().translate( p_position.get( 0 ), p_position.get( 1 ) );
        m_transform.translate( p_position.get( 0 ), p_position.get( 1 ) );

        m_beliefbase.add( new CEnvironmentBeliefbase().create( "env", m_beliefbase ) );
        m_external = m_beliefbase.beliefbase().view( "extern" );
    }

    @Override
    public final String name()
    {
        return m_name;
    }

    @Override
    public final Stream<ILiteral> literal( final IObject<?>... p_object )
    {
        return this.literal( Arrays.stream( p_object ) );
    }

    @Override
    public final Stream<ILiteral> literal( final Stream<IObject<?>> p_object )
    {
        return Stream.of(
            CLiteral.from(
                m_functor,
                Stream.concat(
                    Stream.concat(
                        Stream.of(
                            CLiteral.from( "name", CRawTerm.from( m_name ) )
                        ),
                        m_external.stream().map( i -> i.shallowcopysuffix() ).sorted().sequential()
                    ),
                    this.individualliteral( p_object ).sorted().sequential()
                )
            )
        );
    }

    /**
     * define object literal addons
     *
     * @param p_object calling objects
     * @return literal stream
     */
    protected abstract Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object );

    @Override
    public final int hashCode()
    {
        return m_name.hashCode();
    }

    @Override
    public final boolean equals( final Object p_object )
    {
        return ( p_object != null ) && ( p_object instanceof IObject<?> ) && ( this.hashCode() == p_object.hashCode() );
    }

    @Override
    public final DoubleMatrix1D position()
    {
        return m_position;
    }

    @Override
    public final Convex convex()
    {
        return m_convex.get();
    }

    @Override
    public Transform transform()
    {
        return m_transform;
    }

    @Override
    public boolean intersects( final IBoundingBox p_boundingbox )
    {
        return CEnvironment.COLLISIONDETECTOR.detect( m_convex.get(), m_transform, p_boundingbox.convex(), p_boundingbox.transform() );
    }

    /**
     * get a stream of cells from position
     * @param p_position position
     * @return cells
     * @todo can it be optimized with refactoring inttuple with automatic cast?
     */
    public static Stream<Pair<Integer, Integer>> cells( final IObject<?> p_object, final DoubleMatrix1D p_position )
    {
        return null;
        /*
        // the middle of the cell is calculated with +0.5
        return org.lightjason.trafficsimulation.CCommon.inttupelstream(
            (int) ( p_position.get( 0 ) + 0.5 - p_object.radius() ),
            (int) ( p_position.get( 0 ) + 0.5 + p_object.radius() ),
            (int) ( p_position.get( 1 ) + 0.5 - p_object.radius() ),
            (int) ( p_position.get( 1 ) + 0.5 + p_object.radius() )
        );*/
    }


    @IAgentActionFilter
    @IAgentActionName( name = "boundingbox/resize" )
    private void resizeboundingbox( final int p_percent )
    {
        this.resizeconvex( Math.abs( p_percent ) );
    }


    /**
     * environment beliefbase
     *
     * @bug missing methods
     */
    private final class CEnvironmentBeliefbase extends IBeliefbaseOnDemand<T>
    {

        @Nonnull
        @Override
        public final Stream<ILiteral> streamLiteral()
        {
            return m_environment.literal( IBaseObject.this );
        }
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * base agent generator
     *
     * @tparam T agent type
     * @todo can we add a naming method, which can generate unique names for each agent?
     */
    protected abstract static class IBaseGenerator<T extends IObject<?>> extends IBaseAgentGenerator<T> implements IGenerator<T>
    {
        protected final IEnvironment m_environment;

        /**
         * @param p_stream stream
         * @param p_actions action
         * @param p_environment environment
         * @throws Exception on any error
         */
        protected IBaseGenerator( final InputStream p_stream, final Stream<IAction> p_actions,
                                  final Class<? extends T> p_agentclass, final IEnvironment p_environment
        ) throws Exception
        {
            super( p_stream, Stream.concat( p_actions, CCommon.actionsFromAgentClass( p_agentclass ) ).collect( Collectors.toSet() ) );
            m_environment = p_environment;
        }

        @Override
        public final T generatesingle( final Object... p_data )
        {
            return CHTTPServer.register( this.generate( p_data ) );
        }

        /**
         * generates the agent
         *
         * @param p_data creating arguments
         * @return agent object and group names
         */
        protected abstract Pair<T, Stream<String>> generate( final Object... p_data );


        @Override
        protected IAgentConfiguration<T> configuration( @Nonnull final IFuzzyBundle<Boolean> p_fuzzy, @Nonnull final Collection<ILiteral> p_initalbeliefs,
                                                        @Nonnull final Set<IPlan> p_plans, @Nonnull final Set<IRule> p_rules,
                                                        @Nullable final ILiteral p_initialgoal,
                                                        @Nonnull final IUnifier p_unifier, @Nonnull final IVariableBuilder p_variablebuilder
        )
        {
            return new CConfiguration( p_fuzzy, p_initalbeliefs, p_plans, p_rules, p_initialgoal, p_unifier, p_variablebuilder );
        }

        /**
         * agent configuration
         */
        private final class CConfiguration extends CDefaultAgentConfiguration<T>
        {
            public CConfiguration( final IFuzzyBundle<Boolean> p_fuzzy, final Collection<ILiteral> p_initalbeliefs, final Set<IPlan> p_plans, final Set<IRule> p_rules,
                                   final ILiteral p_initialgoal,
                                   final IUnifier p_unifier, final IVariableBuilder p_variablebuilder
            )
            {
                super( p_fuzzy, p_initalbeliefs, p_plans, p_rules, p_initialgoal, p_unifier, p_variablebuilder );
            }

            @Nonnull
            @Override
            @SuppressWarnings( "unchecked" )
            public final IView<T> beliefbase()
            {
                final IView<T> l_view = new CBeliefbasePersistent<T>( new CMultiStorage<>() ).create( BELIEFBASEROOTNAME );
                l_view.add( new CBeliefbasePersistent<T>( new CSingleStorage<ILiteral, IView<T>, T>() ).create( "extern", l_view ) );

                // add initial beliefs and clear initial beliefbase trigger
                m_initialbeliefs.parallelStream().forEach( i -> l_view.add( i.shallowcopy() ) );
                l_view.trigger();

                return l_view;
            }
        }
    }



}
