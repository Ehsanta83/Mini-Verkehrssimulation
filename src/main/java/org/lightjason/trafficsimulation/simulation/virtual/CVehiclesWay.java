package org.lightjason.trafficsimulation.simulation.virtual;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.simulation.IObject;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;

import java.io.InputStream;
import java.util.stream.Stream;


/**
 * vehicles way class
 */
public final class CVehiclesWay extends IBaseWay<CVehiclesWay>
{
    private static final String FUNCTOR = "vehicleway";

    /**
     * constructor
     *
     * @param p_configuration agent configuration
     * @param p_environment
     * @param p_values
     * @bug check parameter
     */
    private CVehiclesWay(
        final IAgentConfiguration<CVehiclesWay> p_configuration,
        final IEnvironment p_environment, final String p_name,
        final Number... p_values
    )
    {
        super( p_configuration, p_environment, FUNCTOR, p_name, p_values );
    }



    @Override
    protected final Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object )
    {
        return Stream.of();
    }


    public static final class CGenerator extends IGenerator<CVehiclesWay>
    {

        /**
         * @param p_stream
         * @param p_actions
         * @param p_aggregation
         * @param p_environment @throws Exception on any error
         */
        public CGenerator( final InputStream p_stream, final Stream<IAction> p_actions,
                              final IAggregation p_aggregation,
                              final IEnvironment p_environment
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, CVehiclesWay.class, p_environment );
        }

        @Override
        protected final Pair<CVehiclesWay, Stream<String>> generate( final Object... p_data )
        {
            return new ImmutablePair<>( new CVehiclesWay( m_configuration, m_environment, FUNCTOR, (Number[]) p_data ), Stream.of() );
        }

    }
}
