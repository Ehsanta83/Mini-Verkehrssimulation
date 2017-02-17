package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IAgent;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.agent.fuzzy.IFuzzy;
import org.lightjason.agentspeak.beliefbase.CBeliefbasePersistent;
import org.lightjason.agentspeak.beliefbase.storage.CMultiStorage;
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
import java.util.*;

/**
 * agent generator for dynamic / moving agents
 */
public class CVehicleGenerator extends IBaseAgentGenerator<IAgent>
{
    /**
     * environment reference
     */
    private final IEnvironment m_environment;
    /**
     * random generator
     */
    private final Random m_random = new Random();

    /**
     * ctor
     *
     * @param p_environment environment
     * @param p_stream input asl stream
     * @param p_actions action set
     * @param p_aggregation aggregation set
     * @throws Exception on any error
     */
    public CVehicleGenerator( final IEnvironment p_environment, final InputStream p_stream,
                             final Set<IAction> p_actions, final IAggregation p_aggregation
    ) throws Exception
    {
        super( p_stream, p_actions, p_aggregation, Collections.emptySet(), IVariableBuilder.EMPTY );
        m_environment = p_environment;
    }


    @Override
    protected final IAgentConfiguration<IAgent> configuration( final IFuzzy<Boolean, IAgent> p_fuzzy, final Collection<ILiteral> p_initalbeliefs,
                                                              final Set<IPlan> p_plans, final Set<IRule> p_rules,
                                                              final ILiteral p_initialgoal, final IUnifier p_unifier, final IAggregation p_aggregation,
                                                              final IVariableBuilder p_variablebuilder
    )
    {
        return new CAgentConfiguration( p_fuzzy, p_initalbeliefs, p_plans, p_rules, p_initialgoal, p_unifier, p_aggregation, p_variablebuilder );
    }

    @Override
    public IAgent generatesingle( final Object... p_data )
    {
        final List<Map<String, Object>> l_randomgeneratepositions = (List<Map<String, Object>>) p_data[0];
        int l_random = m_random.nextInt( 4 );
        final DenseDoubleMatrix1D l_position = new DenseDoubleMatrix1D(
            new double[]
                {
                    //row
                    ( (List<Integer>) l_randomgeneratepositions.get( l_random ).get( "position" ) ).get( 1 ),
                    //col
                    ( (List<Integer>) l_randomgeneratepositions.get( l_random ).get( "position" ) ).get( 0 )
                }
        );
        while ( !m_environment.empty( l_position ) )
        {
            l_random = m_random.nextInt( 4 );
            l_position.setQuick( 0, ( (List<List<Integer>>) p_data[0] ).get( l_random ).get( 1 ) );
            l_position.setQuick( 1, ( (List<List<Integer>>) p_data[0] ).get( l_random ).get( 0 ) );
        }

        return new CVehicle(
            m_environment,
            m_configuration,
            l_position,
            (int) l_randomgeneratepositions.get( l_random ).get( "rotation" ),
            //width
            (int) p_data[1],
            //height
            (int) p_data[2]
        );
    }


    /**
     * agent configuration
     */
    private static final class CAgentConfiguration extends CDefaultAgentConfiguration<IAgent>
    {

        /**
         * ctor
         *
         * @param p_fuzzy fuzzy operator
         * @param p_initalbeliefs set with initial beliefs
         * @param p_plans plans
         * @param p_rules rules
         * @param p_initialgoal initial goal
         * @param p_aggregation aggregation function
         * @param p_unifier unifier component
         * @param p_variablebuilder variable builder
         */
        CAgentConfiguration( final IFuzzy<Boolean, IAgent> p_fuzzy, final Collection<ILiteral> p_initalbeliefs,
                             final Set<IPlan> p_plans,
                             final Set<IRule> p_rules, final ILiteral p_initialgoal, final IUnifier p_unifier, final IAggregation p_aggregation,
                             final IVariableBuilder p_variablebuilder
        )
        {
            super( p_fuzzy, p_initalbeliefs, p_plans, p_rules, p_initialgoal, p_unifier, p_aggregation, p_variablebuilder );
        }

        @Override
        @SuppressWarnings( "unchecked" )
        public final IView<IAgent> beliefbase()
        {
            // generate root beliefbase - the beliefbase with the local environment-data is generated within the agent-ctor
            final IView<IAgent> l_beliefbase = new CBeliefbasePersistent<>( new CMultiStorage<ILiteral, IView<IAgent>, IAgent>() )
                .create( BELIEFBASEROOTNAME );

            m_initialbeliefs.parallelStream().forEach( i -> l_beliefbase.add( i.shallowcopy() ) );
            return l_beliefbase;
        }

    }
}
