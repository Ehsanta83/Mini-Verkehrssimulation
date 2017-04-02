package org.lightjason.trafficsimulation.simulation;

import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.beliefbase.IBeliefbaseOnDemand;
import org.lightjason.agentspeak.beliefbase.view.IView;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.IShallowCopy;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;

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
     * name of the object
     */
    private final String m_name;
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
     * @param p_name name of the object
     */
    @SuppressWarnings( "unchecked" )
    protected IBaseObject( final IAgentConfiguration<T> p_configuration, final IEnvironment p_environment, final String p_functor, final String p_name )
    {
        super( p_configuration );
        m_functor = p_functor;
        m_name = p_name;
        m_environment = p_environment;

        m_beliefbase.add( new CEnvironmentBeliefbase().create( "env", m_beliefbase ) );
        m_external = m_beliefbase.beliefbase().view( "extern" );
    }

    @Override
    public final String name()
    {
        return m_name;
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
                    Stream.concat(
                        Stream.of(
                            CLiteral.from( "name", CRawTerm.from( m_name ) )
                        ),
                        m_external.stream().map( IShallowCopy::shallowcopysuffix ).sorted().sequential()
                    ),
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

    @Override
    public final int hashCode()
    {
        return m_name.hashCode();
    }

    @Override
    public final boolean equals( final Object p_object )
    {
        return ( p_object != null ) && ( p_object instanceof IObject<?> ) && ( this.hashCode() == p_object.hashCode() );
    }

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
