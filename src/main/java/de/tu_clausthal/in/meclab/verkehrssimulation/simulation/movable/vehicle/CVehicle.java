package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle;

import cern.colt.matrix.DoubleMatrix1D;
import com.badlogic.gdx.graphics.Texture;
import de.tu_clausthal.in.meclab.verkehrssimulation.CCommon;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IMovable;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

/**
 * vehicle class
 */
public class CVehicle extends IMovable<CVehicle>
{
    public static final String TEXTURE_FILE_NAME = CCommon.PACKAGEPATH + "vehicles/{0}.png";

    /**
     * texture
     */
    private Texture m_texture;
    /**
     * width
     */
    private int m_width;
    /**
     * height
     */
    private int m_height;
    /**
     * speed
     */
    private int m_speed;
    /**
     * vehicle type
     */
    private String m_type;
    /**
     * visibility
     */
    private double m_visibility;

    /**
     * ctor
     *
     * @param p_agentconfiguration agent configuration
     * @param p_environment environment
     * @param p_position initialize position
     * @param p_rotation
     * @bug check parameter
     * @bug replace type with enum
     */
    protected CVehicle(
        final IAgentConfiguration<CVehicle> p_agentconfiguration,
        final IEnvironment p_environment,
        final DoubleMatrix1D p_position,
        final int p_rotation,
        final String p_type, final int p_width, final int p_height
    )
    {
        super( p_agentconfiguration, p_environment, p_position, p_rotation );

        m_type = p_type;
        m_width = p_width;
        m_height = p_height;
        final Random l_randomGenerator = new Random();
        m_speed = l_randomGenerator.nextInt( 70 ) + 1;
        //ToDo: calculate visibility
    }

    @Override
    public final Stream<ILiteral> literal( final IObject<?>... p_object )
    {
        return this.literal( Arrays.stream( p_object ) );
    }

    @Override
    public final Stream<ILiteral> literal( final Stream<IObject<?>> p_object )
    {
        return Stream.of( CLiteral.from( "vehicle",
                                         CLiteral.from( "speed", CRawTerm.from( m_speed ) ),
                                         CLiteral.from( "type", CRawTerm.from( m_type ) ),
                                         CLiteral.from( "visibility", CRawTerm.from( m_visibility ) )
        ) );
    }


}
