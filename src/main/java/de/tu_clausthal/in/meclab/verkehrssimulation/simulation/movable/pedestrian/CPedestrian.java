package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.pedestrian;

import cern.colt.matrix.DoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IMovable;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * pedestrian class
 */
public class CPedestrian extends IMovable<CPedestrian>
{

    /**
     * gender
     */
    private String m_gender;
    /**
     * speed
     */
    private int m_speed;
    /**
     * type
     */
    private String m_type;
    /**
     * visibility
     */
    private int m_visibility;

    /**
     * ctor
     *
     * @param p_agentconfiguration agent configuration
     * @param p_environment environment
     * @param p_position initialize position
     * @param p_rotation
     */
    protected CPedestrian(
        final IAgentConfiguration<CPedestrian> p_agentconfiguration,
        final IEnvironment p_environment,
        final DoubleMatrix1D p_position,
        final int p_rotation
    )
    {
        super( p_agentconfiguration, p_environment, p_position, p_rotation );
    }

    @Override
    public final Stream<ILiteral> literal( final IObject<?>... p_object )
    {
        return this.literal( Arrays.stream( p_object ) );
    }

    @Override
    public final Stream<ILiteral> literal( final Stream<IObject<?>> p_object )
    {
        return Stream.of( CLiteral.from( "pedestrian",
                                         CLiteral.from( "gender", CRawTerm.from( m_gender ) ),
                                         CLiteral.from( "speed", CRawTerm.from( m_speed ) ),
                                         CLiteral.from( "type", CRawTerm.from( m_type ) ),
                                         CLiteral.from( "visibility", CRawTerm.from( m_visibility ) )
        ) );
    }

}
