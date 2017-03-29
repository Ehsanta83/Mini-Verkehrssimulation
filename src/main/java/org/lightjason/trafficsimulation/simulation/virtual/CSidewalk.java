package org.lightjason.trafficsimulation.simulation.virtual;

import org.lightjason.trafficsimulation.simulation.IObject;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.stream.Stream;


/**
 * footway class
 */

public final class CSidewalk extends IBaseLane<CSidewalk>
{
    private static final String FUNCTOR = "sidealk";

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment
     * @param p_position @todo check parameter
     */
    private CSidewalk(
        final IAgentConfiguration<CSidewalk> p_configuration,
        final IEnvironment p_environment,
        final Number... p_position
    )
    {
        super( p_configuration, p_environment, FUNCTOR, p_position );
    }


    @Override
    protected final Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object )
    {
        return Stream.of();
    }


    public static final class CGenerator extends IGenerator<CSidewalk>
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
            super( p_stream, p_actions, p_aggregation, CSidewalk.class, p_environment );
        }

        @Override
        @SuppressWarnings( "unchecked" )
        public final CSidewalk generatesingle( final Object... p_data )
        {
            return new CSidewalk( m_configuration, m_environment, (Number[]) p_data );
        }
    }
}

