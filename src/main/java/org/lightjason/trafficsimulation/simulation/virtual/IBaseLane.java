package org.lightjason.trafficsimulation.simulation.virtual;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.simulation.IBaseObject;
import org.lightjason.trafficsimulation.simulation.IBaseObjectGenerator;
import org.lightjason.trafficsimulation.simulation.IObject;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;

import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Stream;


/**
 * abstract base lane class
 */
public abstract class IBaseLane<T extends IBaseLane<?>> extends IBaseObject<T> implements ILane<T>
{
    /**
     * status (passable, not passable)
     */
    private boolean m_passable = true;
    /**
     * defines the left upper position (row / column / height in cells / width in cells )
     */
    private final DoubleMatrix1D m_position;

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @todo check parameter
     */
    protected IBaseLane( final IAgentConfiguration<T> p_configuration, final IEnvironment p_environment,
                         final String p_functor, final String p_name, final Number... p_position
    )
    {
        super( p_configuration, p_environment, p_functor, p_name );
        m_position = new DenseDoubleMatrix1D( Arrays.stream( p_position ).mapToDouble( Number::doubleValue ).toArray() );
    }

    @Override
    protected Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object
    )
    {
        return Stream.of(
            CLiteral.from( "passable", CRawTerm.from( m_passable ) )
        );
    }


    public abstract static class IGenerator<T extends ILane<?>> extends IBaseObjectGenerator<T>
    {

        /**
         * @param p_stream
         * @param p_actions
         * @param p_aggregation
         * @param p_agentclass
         * @param p_environment @throws Exception on any error
         */
        protected IGenerator( final InputStream p_stream, final Stream<IAction> p_actions,
                              final IAggregation p_aggregation,
                              final Class<T> p_agentclass,
                              final IEnvironment p_environment
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, p_agentclass, p_environment );
        }
    }

}
