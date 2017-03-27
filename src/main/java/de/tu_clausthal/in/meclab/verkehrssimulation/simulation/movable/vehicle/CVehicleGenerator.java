package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IBaseMoveableGenerator;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.Set;


public final class CVehicleGenerator extends IBaseMoveableGenerator<CVehicle>
{

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment
     */
    public CVehicleGenerator(
        final IAgentConfiguration<CVehicle> p_configuration,
        final IEnvironment<?> p_environment
    )
    {
        super( p_configuration, p_environment );
    }

    /**
     * generator
     */
    private final class CGenerator extends IGenerator
    {

        public CGenerator( final InputStream p_stream, final Set<IAction> p_actions,
                           final IAggregation p_aggregation
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation );
        }

        @Override
        public final CVehicle generatesingle( final Object... p_data )
        {
            return new CVehicle( m_configuration, m_environment, null );
        }
    }

}
