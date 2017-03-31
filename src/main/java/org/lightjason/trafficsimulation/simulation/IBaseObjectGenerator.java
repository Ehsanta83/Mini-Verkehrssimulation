package org.lightjason.trafficsimulation.simulation;

import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.agent.fuzzy.IFuzzy;
import org.lightjason.agentspeak.beliefbase.CBeliefbasePersistent;
import org.lightjason.agentspeak.beliefbase.storage.CMultiStorage;
import org.lightjason.agentspeak.beliefbase.storage.CSingleStorage;
import org.lightjason.agentspeak.beliefbase.view.IView;
import org.lightjason.agentspeak.common.CCommon;
import org.lightjason.agentspeak.configuration.CDefaultAgentConfiguration;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.execution.IVariableBuilder;
import org.lightjason.agentspeak.language.execution.action.unify.IUnifier;
import org.lightjason.agentspeak.language.instantiable.plan.IPlan;
import org.lightjason.agentspeak.language.instantiable.rule.IRule;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.CHTTPServer;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;

import java.io.InputStream;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * base agent generator
 *
 * @tparam T agent type
 * @todo can we add a naming method, which can generate unique names for each agent?
 */
public abstract class IBaseObjectGenerator<T extends IObject<?>> extends IBaseAgentGenerator<T>
{
    protected final IEnvironment m_environment;

    /**
     * @param p_stream
     * @param p_actions
     * @param p_aggregation
     * @param p_environment
     * @throws Exception on any error
     */
    protected IBaseObjectGenerator( final InputStream p_stream, final Stream<IAction> p_actions,
                                    final IAggregation p_aggregation, final Class<T> p_agentclass, final IEnvironment p_environment
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
    protected abstract Pair<T,Stream<String>> generate( final Object... p_data );


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
