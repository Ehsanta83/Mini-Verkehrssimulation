package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight;

import cern.colt.matrix.DoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * pedestrian traffic light class
 */
public class CPedestriansTrafficLight extends IBaseTrafficLight<CPedestriansTrafficLight, EPedestriansTrafficLightColor>
{


    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_light
     * @param p_position
     * @param p_rotation
     */
    protected CPedestriansTrafficLight(
        final IAgentConfiguration<CPedestriansTrafficLight> p_configuration,
        final Class<EPedestriansTrafficLightColor> p_light,
        final DoubleMatrix1D p_position,
        final int p_rotation
    )
    {
        super( p_configuration, p_light, p_position, p_rotation );
    }

    @Override
    public final Stream<ILiteral> literal( final IObject<?>... p_object )
    {
        return this.literal( Arrays.stream( p_object ) );
    }

    @Override
    public final Stream<ILiteral> literal( final Stream<IObject<?>> p_object )
    {
        return Stream.of( CLiteral.from( "pedestrianstrafficlight",
                                         CLiteral.from( "color", CRawTerm.from( m_color ) )
                          )
        );
    }

}
