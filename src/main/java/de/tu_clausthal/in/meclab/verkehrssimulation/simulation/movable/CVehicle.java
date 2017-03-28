package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable;

import cern.colt.matrix.DoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.stream.Stream;


/**
 * vehicle class
 */
@IAgentAction
public final class CVehicle extends IBaseMoveable<CVehicle>
{
    private static final String FUNCTOR = "vehicle";

    /**
     * width
     */
    private int m_width;
    /**
     * height
     */
    private int m_height;

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment
     * @param p_position
     */
    private CVehicle(
        final IAgentConfiguration<CVehicle> p_configuration,
        final IEnvironment p_environment,
        final DoubleMatrix1D p_position
    )
    {
        super( p_configuration, p_environment, FUNCTOR, p_position );

    }

    @Override
    protected final Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object )
    {
        return Stream.of();
    }


    @IAgentActionFilter
    @IAgentActionName( name = "accelerate" )
    private void accelerate( final Number p_value )
    {

    }

    @IAgentActionFilter
    @IAgentActionName( name = "deccelerate" )
    private void deccelerate( final Number p_value )
    {

    }


    public static final class CGenerator extends IGenerator<CVehicle>
    {

        /**
         * @param p_stream
         * @param p_actions
         * @param p_aggregation
         * @param p_environment
         * @throws Exception on any error
         */
        public CGenerator( final InputStream p_stream, final Stream<IAction> p_actions, final IAggregation p_aggregation,
                           final IEnvironment p_environment
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, CVehicle.class, p_environment );
        }

        @Override
        @SuppressWarnings( "unchecked" )
        public CVehicle generatesingle( final Object... p_data )
        {
            return new CVehicle( m_configuration, m_environment, (DoubleMatrix1D) p_data[0] );
        }
    }
}
