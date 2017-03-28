package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.pedestrian;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IBaseMoveableGenerator;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.Set;
import java.util.stream.Stream;


/**
 * pedestrian generator
 *
 * @todo build with an agent
 */
public final class CPedestrianGenerator extends IBaseMoveableGenerator<CPedestrian>
{
    private static final String FUNCTOR = "pedestriangenerator";

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment
     */
    protected CPedestrianGenerator( final IAgentConfiguration<CPedestrian> p_configuration, final IEnvironment p_environment )
    {
        super( p_configuration, p_environment, FUNCTOR );
    }

    @Override
    protected final Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object
    )
    {
        return Stream.of();
    }


    /**
     * generator
     *
     * @bug check null value on generating
     */
    private final class CGenerator extends IGenerator
    {

        /**
         * @param p_stream
         * @param p_actions
         * @param p_aggregation
         * @param p_environment
         * @throws Exception
         */
        public CGenerator( final InputStream p_stream, final Set<IAction> p_actions, final IAggregation p_aggregation,
                           final IEnvironment p_environment
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, p_environment );
        }

        @Override
        public CPedestrian generatesingle( final Object... p_data )
        {
            return new CPedestrian( m_configuration, m_environment, null );
        }
    }
}
