package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle;

import cern.colt.matrix.DoubleMatrix1D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.tu_clausthal.in.meclab.verkehrssimulation.CCommon;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IAgent;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IBaseAgent;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;

import java.text.MessageFormat;

/**
 * vehicle class
 */
public class CVehicle extends IBaseAgent
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
     * ctor
     *
     * @param p_environment        environment
     * @param p_agentconfiguration agent configuration
     * @param p_position           initialize position
     */
    protected CVehicle( final IEnvironment p_environment, final IAgentConfiguration<IAgent> p_agentconfiguration, final DoubleMatrix1D p_position,
                        final int p_rotation, final int p_width, final int p_height )
    {
        super( p_environment, p_agentconfiguration, p_position, p_rotation );
        m_width = p_width;
        m_height = p_height;
    }

    @Override
    public void spriteinitialize( float p_unit )
    {
        if ( m_texture == null )
        {
            m_texture = new Texture( Gdx.files.internal( MessageFormat.format( TEXTURE_FILE_NAME, "car" ) ) );
        }
        // ToDo: later change car to vehicle type
        m_sprite = new Sprite( m_texture );
        m_sprite.setPosition( (float) m_position.get( 1 ), (float) m_position.get( 0 ) );
        m_sprite.setSize( m_width * p_unit, m_height * p_unit );
        m_sprite.setOrigin( 0, 0 );
        m_sprite.setRotation( m_rotation );
    }

    @Override
    protected int speed()
    {
        return 0;
    }

    @Override
    protected double nearby()
    {
        return 0;
    }
}
