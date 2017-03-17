package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * pedestrian traffic light class
 */
public class CPedestriansTrafficLight extends IBaseTrafficLight<EPedestriansTrafficLight>
{
    /**
     * the number of persons, who wait behind the traffic light
     * ToDo: implementing the code for this
     */
    private int m_numberofpersons;

    /**
     * traffic light constructor
     *
     * @param p_position left bottom position
     * @param p_rotation rotation
     * @param p_width width
     * @param p_height height
     * @param p_startColor start color of the traffic light
     * @param p_startColorDuration duration of the start color
     * @param p_duration duration of the traffic light colors
     */
    public CPedestriansTrafficLight( final List<Integer> p_position, final int p_rotation, final int p_width, final int p_height,
                                    final EPedestriansTrafficLight p_startColor, final int p_startColorDuration, final int... p_duration )
    {
        super( p_position, p_rotation, p_width, p_height, p_startColor, p_startColorDuration, p_duration );
    }

    @Override
    public <T extends IObject> Stream<ILiteral> literal( final T... p_object )
    {
        return this.literal( Arrays.stream( p_object ) );
    }

    @Override
    public <T extends IObject> Stream<ILiteral> literal( final Stream<T> p_object )
    {
        return Stream.of( CLiteral.from( "pedestrianstrafficlight",
                CLiteral.from( "color", CRawTerm.from( m_color ) ),
                CLiteral.from( "duration", CRawTerm.from( m_duration ) ),
                CLiteral.from( "numberofpersons", CRawTerm.from( m_numberofpersons ) )
        ) );
    }
}
