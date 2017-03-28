package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import cern.colt.matrix.DoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.Arrays;
import java.util.stream.Stream;


/**
 * vehicles way class
 */
public class CVehiclesWay extends IBaseWay<CVehiclesWay>
{
    private static final String FUNCTOR = "vehicleway";

    /**
     * constructor
     *
     * @param p_leftbottom leftbottom position in grid
     * @param p_righttop righttop position in grid
     * @param p_rotation rotation
     */
    public CVehiclesWay( final IAgentConfiguration<CVehiclesWay> p_configuration, final IEnvironment<?> p_environment, final DoubleMatrix1D p_leftbottom, final DoubleMatrix1D p_righttop,
                         final int p_rotation
    )
    {
        super( p_configuration, p_environment, FUNCTOR, p_leftbottom, p_righttop, p_rotation );
    }

    @Override
    protected final Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object )
    {
        return Stream.of();
    }
}
