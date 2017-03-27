package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable;

import cern.colt.matrix.DoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle.CVehicle;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.beliefbase.CBeliefbasePersistent;
import org.lightjason.agentspeak.beliefbase.IBeliefbaseOnDemand;
import org.lightjason.agentspeak.beliefbase.storage.CSingleStorage;
import org.lightjason.agentspeak.common.CPath;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.ITerm;

import java.util.Arrays;
import java.util.stream.Stream;



/**
 * moveable object
 * @todo implement moving
 */
public abstract class IBaseMoveable<T extends IBaseMoveable<?>> extends IBaseAgent<T> implements IMoveable<T>
{
    private final String m_literalfunctor;
    /**
     * reference to the environment
     */
    private final IEnvironment<?> m_environment;
    /**
     * current position of the agent
     */
    private final DoubleMatrix1D m_position;


    /**
     * ctor
     *
     * @param p_configuration agent configuration
     */
    protected IBaseMoveable( final IAgentConfiguration<T> p_configuration, final IEnvironment<?> p_environment, final String p_literalfunctor, final DoubleMatrix1D p_position )
    {
        super( p_configuration );
        m_environment = p_environment;
        m_position = p_position;
        m_literalfunctor = p_literalfunctor;

        m_beliefbase
            .add( new CEnvironmentBeliefbase().create( "env", m_beliefbase ) )
            .add( new CBeliefbasePersistent<T>( new CSingleStorage<>() ).create( "extern", m_beliefbase ) );
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
            CLiteral.from( m_literalfunctor, m_beliefbase.stream( CPath.from( "extern" ) ).map( ITerm::<ITerm>raw ) )
        );
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
            return m_environment.literal( IBaseMoveable.this );
        }
    }
}
