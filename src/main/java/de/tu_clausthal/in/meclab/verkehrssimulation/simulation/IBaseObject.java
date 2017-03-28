package de.tu_clausthal.in.meclab.verkehrssimulation.simulation;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.apache.commons.compress.archivers.sevenz.CLI;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.beliefbase.IBeliefbaseOnDemand;
import org.lightjason.agentspeak.beliefbase.view.IView;
import org.lightjason.agentspeak.common.CPath;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.IShallowCopy;

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
     * reference to external beliefbase
     */
    private final IView<T> m_external;

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment environment reference
     * @param p_functor functor of the object literal
     */
    @SuppressWarnings( "unchecked" )
    protected IBaseObject( final IAgentConfiguration<T> p_configuration, final IEnvironment p_environment, final String p_functor )
    {
        super( p_configuration );
        m_functor = p_functor;
        m_environment = p_environment;

        m_beliefbase.add( new CEnvironmentBeliefbase().create( "env", m_beliefbase ) );
        m_external = m_beliefbase.beliefbase().view( "extern" );
    }

    @Override
    public final Stream<ILiteral> literal( final IObject<?>... p_object )
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
                    m_external.stream().map( IShallowCopy::shallowcopysuffix ).sorted().sequential(),
                    this.individualliteral( p_object ).sorted().sequential()
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
