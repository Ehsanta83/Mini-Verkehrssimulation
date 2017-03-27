package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import cern.colt.matrix.DoubleMatrix1D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.beliefbase.IBeliefbaseOnDemand;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;


/**
 * traffic light abstract class
 */
@IAgentAction
public abstract class IBaseTrafficLight<T extends IBaseTrafficLight<?, ?>, L extends Enum<?> & ITrafficLightColor> extends IBaseAgent<T> implements ITrafficLight<T>
{
    /**
     * color of traffic light
     */
    private AtomicReference<L> m_color;
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
     * environment
     */
    private final IEnvironment<?> m_environment;

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment environment reference
     * @param p_light light class
     * @param p_position
     * @param p_rotation
     */
    protected IBaseTrafficLight( final IAgentConfiguration<T> p_configuration, final IEnvironment p_environment, final Class<L> p_light, final DoubleMatrix1D p_position, final int p_rotation
    )
    {
        super( p_configuration );
        m_environment = p_environment;
        m_position = p_position;
        m_rotation = p_rotation;
        m_light = p_light;

        m_beliefbase.add( new CEnvironmentBeliefbase().create( "env", m_beliefbase ) );
    }



    /**
     * changes the color of the light
     */
    @IAgentActionFilter
    @IAgentActionName( name= "next" )
    private void next()
    {
        m_color.get().next();
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

        m_sprite = new Sprite( m_color.get().getTexture() );
        m_sprite.setPosition( (float) m_position.get( 1 ), (float) m_position.get( 0 ) );
        m_sprite.setSize( (float) m_position.get( 2 ) * p_unit, (float) m_position.get( 3 ) * p_unit );
        m_sprite.setOrigin( 0, 0 );
        m_sprite.setRotation( m_rotation );
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
            CLiteral.from( "trafficlight",
                Stream.concat(
                    Stream.of(
                        CLiteral.from( "color", CRawTerm.from( m_color.get().toString().toLowerCase( Locale.ROOT ) ) )
                    ),
                    this.individual( p_object )
                )
            )
        );
    }

    /**
     * individual definition of the traffic light
     *
     * @param p_object accessable object stream
     * @return literal stream
     */
    protected abstract Stream<ILiteral> individual( final Stream<IObject<?>> p_object );


    /**
     * environment beliefbase
     * @bug missing methods
     */
    private final class CEnvironmentBeliefbase extends IBeliefbaseOnDemand<T>
    {

        @Override
        public final Stream<ILiteral> streamLiteral()
        {
            return m_environment.literal( IBaseTrafficLight.this );
        }
    }

}
