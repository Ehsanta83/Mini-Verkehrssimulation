package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.pedestrian;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IMovableAgent;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.CTrigger;
import org.lightjason.agentspeak.language.instantiable.plan.trigger.ITrigger;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * pedestrian class
 */
public class CPedestrian extends IMovableAgent
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
     * @param p_environment        environment
     * @param p_agentconfiguration agent configuration
     */
    protected CPedestrian( final IEnvironment p_environment, final IAgentConfiguration<IMovableAgent> p_agentconfiguration )
    {
        //ToDo: later change position and rotation
        super( p_environment, p_agentconfiguration, new DenseDoubleMatrix1D( new double[]{0, 0} ), 0 );

        this.trigger(
            CTrigger.from(
                ITrigger.EType.ADDBELIEF,
                CLiteral.from( "gender", CRawTerm.from( "man" ) )
            )
        );

        /*
        //ToDo: is there a better way to choose random from a list
        m_gender = Stream.of( "man", "woman" )
            .collect( Collectors.collectingAndThen( Collectors.toList(), collected ->
            {
                Collections.shuffle( collected );
                return collected.stream();
            }
            ) ).collect( Collectors.toList() ).get( 0 );
        */
        final Random l_randomGenerator = new Random();
        m_speed = l_randomGenerator.nextInt( 10 ) + 1;
        m_type = Stream.of( "adult", "child", "eldery", "mother" )
                .collect( Collectors.collectingAndThen( Collectors.toList(), collected ->
                {
                    Collections.shuffle( collected );
                    return collected.stream();
                } ) ).collect( Collectors.toList() ).get( 0 );
        //ToDo: implement visibility
    }

    @Override
    public void spriteinitialize( float p_unit )
    {

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

    @Override
    public <T extends IObject> Stream<ILiteral> literal( final T... p_object )
    {
        return this.literal( Arrays.stream( p_object ) );
    }

    @Override
    public <T extends IObject> Stream<ILiteral> literal( final Stream<T> p_object )
    {
        return Stream.of( CLiteral.from( "pedestrian",
                CLiteral.from( "gender", CRawTerm.from( m_gender ) ),
                CLiteral.from( "speed", CRawTerm.from( m_speed ) ),
                CLiteral.from( "type", CRawTerm.from( m_type ) ),
                CLiteral.from( "visibility", CRawTerm.from( m_visibility ) )
        ) );
    }
}
