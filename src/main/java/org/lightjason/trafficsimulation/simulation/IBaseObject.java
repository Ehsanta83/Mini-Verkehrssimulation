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
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.agent.fuzzy.IFuzzy;
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
import org.lightjason.agentspeak.language.IShallowCopy;
import org.lightjason.agentspeak.language.execution.IVariableBuilder;
import org.lightjason.agentspeak.language.execution.action.unify.IUnifier;
import org.lightjason.agentspeak.language.instantiable.plan.IPlan;
import org.lightjason.agentspeak.language.instantiable.rule.IRule;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;
import org.lightjason.trafficsimulation.ui.CHTTPServer;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * base agent object
 *
 * @param <T> IObject
 */
public abstract class IBaseObject<T extends IObject<?>> extends IBaseAgent<T> implements IObject<T>
{
    /**
     * current position of the agent
     */
    protected DoubleMatrix1D m_position;
    /**
     * functor definition
     */
    private final String m_functor;
    /**
     * name of the object
     */
    private final String m_name;
    /**
     * environment reference
     */
    private final IEnvironment m_environment;
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
                           final String p_functor, final String p_name, final DoubleMatrix1D p_position )
    {
        super( p_configuration );
        m_functor = p_functor;
        m_name = p_name;
        m_environment = p_environment;
        m_position = p_position;

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
                        m_external.stream().map( IShallowCopy::shallowcopysuffix ).sorted().sequential()
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

    /**
     * environment beliefbase
     *
     * @bug missing methods
     */
    private final class CEnvironmentBeliefbase extends IBeliefbaseOnDemand<T>
    {

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
         * @param p_aggregation aggregation
         * @param p_environment environment
         * @throws Exception on any error
         */
        protected IBaseGenerator( final InputStream p_stream, final Stream<IAction> p_actions,
                                  final IAggregation p_aggregation, final Class<? extends T> p_agentclass, final IEnvironment p_environment
        ) throws Exception
        {
            super( p_stream, Stream.concat( p_actions, CCommon.actionsFromAgentClass( p_agentclass ) ).collect( Collectors.toSet() ), p_aggregation );
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
        protected IAgentConfiguration<T> configuration( final IFuzzy<Boolean, T> p_fuzzy, final Collection<ILiteral> p_initalbeliefs, final Set<IPlan> p_plans,
                                                        final Set<IRule> p_rules,
                                                        final ILiteral p_initialgoal, final IUnifier p_unifier, final IAggregation p_aggregation,
                                                        final IVariableBuilder p_variablebuilder
        )
        {
            return new CConfiguration( p_fuzzy, p_initalbeliefs, p_plans, p_rules, p_initialgoal, p_unifier, p_aggregation, p_variablebuilder );
        }

        /**
         * agent configuration
         */
        private final class CConfiguration extends CDefaultAgentConfiguration<T>
        {
            public CConfiguration( final IFuzzy<Boolean, T> p_fuzzy, final Collection<ILiteral> p_initalbeliefs, final Set<IPlan> p_plans, final Set<IRule> p_rules,
                                   final ILiteral p_initialgoal,
                                   final IUnifier p_unifier, final IAggregation p_aggregation, final IVariableBuilder p_variablebuilder
            )
            {
                super( p_fuzzy, p_initalbeliefs, p_plans, p_rules, p_initialgoal, p_unifier, p_aggregation, p_variablebuilder );
            }

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
