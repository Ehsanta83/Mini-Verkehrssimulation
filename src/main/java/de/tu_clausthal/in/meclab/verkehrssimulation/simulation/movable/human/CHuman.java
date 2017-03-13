package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.human;

import cern.colt.matrix.DoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IAgent;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IBaseAgent;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * human class
 */
public class CHuman extends IBaseAgent {

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
     * @param p_environment        environment
     * @param p_agentconfiguration agent configuration
     * @param p_position           initialize position
     * @param p_rotation           rotation
     */
    protected CHuman(IEnvironment p_environment, IAgentConfiguration<IAgent> p_agentconfiguration, DoubleMatrix1D p_position, int p_rotation) {
        super(p_environment, p_agentconfiguration, p_position, p_rotation);
    }

    @Override
    public void spriteinitialize(float p_unit) {

    }

    @Override
    protected int speed() {
        return 0;
    }

    @Override
    protected double nearby() {
        return 0;
    }

    @Override
    public <T extends IObject> Stream<ILiteral> literal( final T... p_object)
    {
        return this.literal( Arrays.stream( p_object ) );
    }

    @Override
    public <T extends IObject> Stream<ILiteral> literal( final Stream<T> p_object)
    {
        return Stream.of( CLiteral.from( "human",
                CLiteral.from( "gender", CRawTerm.from( m_gender ) ),
                CLiteral.from( "speed", CRawTerm.from( m_speed ) ),
                CLiteral.from( "type", CRawTerm.from( m_type ) ),
                CLiteral.from( "visibility", CRawTerm.from( m_visibility ) )
        ) );
    }
}
