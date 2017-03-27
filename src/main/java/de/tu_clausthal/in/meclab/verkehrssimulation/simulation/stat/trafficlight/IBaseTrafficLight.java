package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import cern.colt.matrix.DoubleMatrix1D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.IStatic;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.beliefbase.IBeliefbaseOnDemand;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;

import java.text.MessageFormat;
import java.util.stream.Stream;

/**
 * traffic light abstract class
 */
@IAgentAction
public abstract class IBaseTrafficLight<T extends Enum<T> & IETrafficLight> extends IBaseAgent<IBaseTrafficLight<T>> implements IStatic
{
    /**
     * color of traffic light
     */
    protected T m_color;
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
     * constructor
     *
     * @param p_position left bottom position
     * @param p_rotation rotation
     */
    protected IBaseTrafficLight( final IAgentConfiguration<IBaseTrafficLight<T>> p_configuration, final DoubleMatrix1D p_position, final int p_rotation )
    {
        super( p_configuration );
        m_position = p_position;
        m_rotation = p_rotation;

        // add environment beliefbase to the agent with the prefix "env"
        m_beliefbase.add( new CEnvironmentBeliefbase().create( "env", m_beliefbase ) );
    }



    @Override
    public IBaseTrafficLight<T> call() throws Exception
    {
        super.call();
        if ( m_color.getTexture() != null )
            m_sprite.setTexture( m_color.getTexture() );

        return this;
    }


    /**
     * changes the color of the light
     */
    @IAgentActionFilter
    @IAgentActionName( name= "next" )
    private synchronized void next()
    {
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
        Stream.of( EVehiclesTrafficLight.values() )
            .forEach( i -> i.setTexture(  new Texture( Gdx.files.internal( MessageFormat.format( EVehiclesTrafficLight.TEXTURE_FILE_NAME, i.toString().toLowerCase() ) ) ) ) );

        m_sprite = new Sprite( m_color.getTexture() );
        m_sprite.setPosition( (float) m_position.get( 1 ), (float) m_position.get( 0 ) );
        m_sprite.setSize( (float) m_position.get( 2 ) * p_unit, (float) m_position.get( 3 ) * p_unit );
        m_sprite.setOrigin( 0, 0 );
        m_sprite.setRotation( m_rotation );
    }

    @Override
    public final DoubleMatrix1D position()
    {
        return m_position;
    }


    /**
     * beliefbase for getting environment information
     */
    private final class CEnvironmentBeliefbase extends IBeliefbaseOnDemand<IBaseTrafficLight<T>>
    {

    }

}
