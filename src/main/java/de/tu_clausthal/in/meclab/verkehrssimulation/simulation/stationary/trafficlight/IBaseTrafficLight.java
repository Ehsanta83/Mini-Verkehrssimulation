package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stationary.trafficlight;

import cern.colt.matrix.DoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IBaseObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IBaseObjectGenerator;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;


/**
 * traffic light abstract class
 */
@IAgentAction
public abstract class IBaseTrafficLight<T extends IBaseTrafficLight<?, ?>, L extends Enum<?> & ITrafficLightColor> extends IBaseObject<T> implements ITrafficLight<T>
{
    /**
     * color of traffic light
     */
    private AtomicReference<L> m_color;
    /**
     * defines the left bottom position (row / column), width, height
     */
    private final DoubleMatrix1D m_position;
    /**
     * rotation of the traffic light
     */
    private final int m_rotation;
    /**
     * enum class
     */
    private final Class<L> m_light;


    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment environment reference
     * @param p_functor
     * @param p_light light class
     * @param p_position
     * @param p_rotation
     */
    protected IBaseTrafficLight( final IAgentConfiguration<T> p_configuration, final IEnvironment p_environment,
                                 final String p_functor, final Class<L> p_light,
                                 final DoubleMatrix1D p_position, final int p_rotation
    )
    {
        super( p_configuration, p_environment, p_functor );
        m_position = p_position;
        m_rotation = p_rotation;
        m_light = p_light;
    }



    /**
     * changes the color of the light
     */
    @IAgentActionFilter
    @IAgentActionName( name = "next" )
    private void next()
    {
        m_color.get().next();
    }


    /**
     * abstract generator for traffic lights
     *
     * @bug fix documentation
     */
    protected abstract static class IBaseGenerator<T extends IBaseTrafficLight<?, ?>> extends IBaseObjectGenerator<T>
    {


        /**
         * @param p_stream
         * @param p_actions
         * @param p_aggregation
         * @param p_environment
         * @throws Exception on any error
         */
        public IBaseGenerator( final InputStream p_stream, final Set<IAction> p_actions,
                               final IAggregation p_aggregation,
                               final IEnvironment p_environment
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, p_environment );
        }

        @Override
        @SuppressWarnings( "unchecked" )
        public final T generatesingle( final Object... p_data )
        {
            return this.generate( m_environment, (DoubleMatrix1D) p_data[0], (int) p_data[1] );
        }

        /**
         * generates the agent
         *
         * @param p_environment
         * @param p_position
         * @param p_rotation
         * @return
         */
        protected abstract T generate( final IEnvironment p_environment, final DoubleMatrix1D p_position, final int p_rotation );

    }
}
