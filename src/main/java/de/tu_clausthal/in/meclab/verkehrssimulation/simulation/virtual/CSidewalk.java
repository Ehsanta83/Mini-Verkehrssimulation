package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


/**
 * footway class
 */

public class CSidewalk extends IBaseLane<CSidewalk>
{

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_leftbottom left-bottom position
     * @param p_righttop right-up position
     * @todo check parameter
     */
    public CSidewalk(
        final IAgentConfiguration<CSidewalk> p_configuration,
        final List<Integer> p_leftbottom,
        final List<Integer> p_righttop
    )
    {
        super( p_configuration, p_leftbottom, p_righttop );
    }

    @Override
    public final Stream<ILiteral> literal( final IObject<?>... p_object )
    {
        return this.literal( Arrays.stream( p_object ) );
    }

    @Override
    public final Stream<ILiteral> literal( final Stream<IObject<?>> p_object )
    {
        return Stream.of();
    }
}

