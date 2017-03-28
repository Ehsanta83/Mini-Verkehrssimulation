package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.stream.Stream;


/**
 * intersection class
 */
public final class CIntersection extends IBaseLane<CIntersection>
{
    private static final String FUNCTOR = "intersection";

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment
     * @param p_position @todo check parameter
     */
    protected CIntersection(
        final IAgentConfiguration<CIntersection> p_configuration,
        final IEnvironment p_environment,
        final Number... p_position
    )
    {
        super( p_configuration, p_environment, FUNCTOR, p_position );
    }



    public static final class CGenerator extends IGenerator<CIntersection>
    {

        /**
         * @param p_stream
         * @param p_actions
         * @param p_aggregation
         * @param p_environment
         * @throws Exception on any error
         */
        public CGenerator( final InputStream p_stream, final Stream<IAction> p_actions,
                              final IAggregation p_aggregation,
                              final IEnvironment p_environment ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, CIntersection.class, p_environment );
        }

        @Override
        @SuppressWarnings( "unchecked" )
        public final CIntersection generatesingle( final Object... p_data )
        {
            return new CIntersection( m_configuration, m_environment, (Number[]) p_data );
        }
    }

}
