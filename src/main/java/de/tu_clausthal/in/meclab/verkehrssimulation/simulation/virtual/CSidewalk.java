package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * footway class
 */

public class CSidewalk extends IBaseLane
{
    /**
     * ctor
     *
     * @param p_leftbottom left-bottom position
     * @param p_righttop   right-up position
     */
    public CSidewalk( final List<Integer> p_leftbottom, final List<Integer> p_righttop )
    {
        super( p_leftbottom, p_righttop );
    }

    @Override
    public <T extends IObject> Stream<ILiteral> literal( final T... p_object )
    {
        return this.literal( Arrays.stream( p_object ) );
    }

    @Override
    public <T extends IObject> Stream<ILiteral> literal( final Stream<T> p_object )
    {
        return Stream.of( CLiteral.from( "lane",
                CLiteral.from( "passable", CRawTerm.from( m_passable ) )
        ) );
    }
}

