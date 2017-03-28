package de.tu_clausthal.in.meclab.verkehrssimulation.simulation;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.beliefbase.IBeliefbaseOnDemand;
import org.lightjason.agentspeak.common.CPath;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.Arrays;
import java.util.stream.Stream;


/**
 * base agent object
 *
 * @param <T>
 */
public abstract class IBaseObject<T extends IObject<?>> extends IBaseAgent<T> implements IObject<T>
{
    /**
     * functor definition
     */
    private final String m_functor;
    /**
     * environment reference
     */
    private final IEnvironment m_environment;

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment environment reference
     * @param p_functor functor of the object literal
     */
    public IBaseObject( final IAgentConfiguration<T> p_configuration, final IEnvironment p_environment, final String p_functor )
    {
        super( p_configuration );
        m_functor = p_functor;
        m_environment = p_environment;

        m_beliefbase.add( new CEnvironmentBeliefbase().create( "env", m_beliefbase ) );
    }

    @Override
    public final Stream<ILiteral> literal( final IObject<?>... p_object
    )
    {
        return this.literal( Arrays.stream( p_object ) );
    }

    @Override
    public final Stream<ILiteral> literal( final Stream<IObject<?>> p_object )
    {
        return Stream.of(
            CLiteral.from(
                m_functor,
                Stream.concat(
                    m_beliefbase.stream( CPath.from( "extern" ) ),
                    this.individualliteral( p_object )
                )
            )
        );
    }

    /**
     * define object literal addons
     *
     * @param p_object calling objects
     * @return literal stream
     */
    protected abstract Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object );


    /**
     * environment beliefbase
     *
     * @bug missing methods
     */
    private final class CEnvironmentBeliefbase extends IBeliefbaseOnDemand<T>
    {

        @Override
        public final Stream<ILiteral> streamLiteral()
        {
            return m_environment.literal( IBaseObject.this );
        }
    }
}
