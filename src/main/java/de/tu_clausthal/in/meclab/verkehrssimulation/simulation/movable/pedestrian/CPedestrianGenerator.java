package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.pedestrian;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IBaseMoveable;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IBaseMoveableGenerator;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle.CVehicle;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import org.lightjason.agentspeak.language.execution.IVariableBuilder;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.Collections;
import java.util.Set;


/**
 * pedestrian generator
 *
 * @todo build with an agent
 */
public final class CPedestrianGenerator extends IBaseMoveableGenerator<CPedestrian>
{

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment
     */
    protected CPedestrianGenerator( final IAgentConfiguration<CPedestrian> p_configuration, final IEnvironment<?> p_environment )
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
        public CPedestrian generatesingle( final Object... p_data )
        {
            return null;
        }
    }
}
