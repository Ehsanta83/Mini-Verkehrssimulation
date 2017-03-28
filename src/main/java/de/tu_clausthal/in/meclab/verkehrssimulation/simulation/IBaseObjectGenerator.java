package de.tu_clausthal.in.meclab.verkehrssimulation.simulation;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.agent.fuzzy.IFuzzy;
import org.lightjason.agentspeak.beliefbase.CBeliefbasePersistent;
import org.lightjason.agentspeak.beliefbase.storage.CSingleStorage;
import org.lightjason.agentspeak.beliefbase.view.IView;
import org.lightjason.agentspeak.configuration.CDefaultAgentConfiguration;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.execution.IVariableBuilder;
import org.lightjason.agentspeak.language.execution.action.unify.IUnifier;
import org.lightjason.agentspeak.language.instantiable.plan.IPlan;
import org.lightjason.agentspeak.language.instantiable.rule.IRule;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.Collection;
import java.util.Set;


/**
 * base agent generator
 * @todo implement
 * @param <T>
 */
public abstract class IBaseObjectGenerator<T extends IObject<?>> extends IBaseAgentGenerator<T>
{
    protected final IEnvironment<?> m_environment;

    /**
     *
     * @param p_stream
     * @param p_actions
     * @param p_aggregation
     * @param p_environment
     * @throws Exception
     */
    public IBaseObjectGenerator( final InputStream p_stream, final Set<IAction> p_actions,
                                 final IAggregation p_aggregation, final IEnvironment<?> p_environment
    ) throws Exception
    {
        super( p_stream, p_actions, p_aggregation );
        m_environment = p_environment;
    }

    @Override
    protected IAgentConfiguration<T> configuration( final IFuzzy<Boolean, T> p_fuzzy, final Collection<ILiteral> p_initalbeliefs, final Set<IPlan> p_plans,
                                                    final Set<IRule> p_rules,
                                                    final ILiteral p_initialgoal, final IUnifier p_unifier, final IAggregation p_aggregation,
                                                    final IVariableBuilder p_variablebuilder
    )
    {
        return new CConfiguration( p_fuzzy, p_initalbeliefs, p_plans, p_rules, p_initialgoal, p_unifier, p_aggregation, p_variablebuilder );
    }

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
        public final IView<T> beliefbase()
        {
            final IView<T> l_view = super.beliefbase();
            return l_view.add( new CBeliefbasePersistent<T>( new CSingleStorage<ILiteral, IView<T>, T>() ).create( "extern", l_view ) );
        }
    }
}
