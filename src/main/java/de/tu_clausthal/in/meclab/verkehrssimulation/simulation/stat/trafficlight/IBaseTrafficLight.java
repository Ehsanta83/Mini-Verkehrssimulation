package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import cern.colt.matrix.DoubleMatrix1D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;

import java.util.Arrays;


/**
 * traffic light abstract class
 */
@IAgentAction
public abstract class IBaseTrafficLight<T extends IBaseTrafficLight<?, ?>, L extends Enum<?> & IETrafficLightColor> extends IBaseAgent<T> implements ITrafficLight<T>
{
    /**
     * color of traffic light
     */
    protected L m_color;
    /**
     * sprite
     */
    private Sprite m_sprite;
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
     */
    protected IBaseTrafficLight( final IAgentConfiguration<T> p_configuration, final Class<L> p_light, final DoubleMatrix1D p_position, final int p_rotation
    )
    {
        super( p_configuration );
        m_position = p_position;
        m_rotation = p_rotation;
        m_light = p_light;
    }



    /**
     * changes the color of the light
     */
    @IAgentActionFilter
    @IAgentActionName( name= "next" )
    private synchronized void next()
    {
        m_color.next();
    }


    @Override
    public Sprite sprite()
    {
        return m_sprite;
    }

    @Override
    public void spriteinitialize( final float p_unit )
    {
        //initialize textures
        Arrays.stream( m_light.getEnumConstants() ).forEach( i -> i.setTexture( new Texture( Gdx.files.internal( i.path() ) ) ) );

        m_sprite = new Sprite( m_color.getTexture() );
        m_sprite.setPosition( (float) m_position.get( 1 ), (float) m_position.get( 0 ) );
        m_sprite.setSize( (float) m_position.get( 2 ) * p_unit, (float) m_position.get( 3 ) * p_unit );
        m_sprite.setOrigin( 0, 0 );
        m_sprite.setRotation( m_rotation );
    }

}
