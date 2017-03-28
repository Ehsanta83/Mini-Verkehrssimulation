package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IBaseObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.List;
import java.util.stream.Stream;


/**
 * abstract base lane class
 */
public abstract class IBaseLane<T extends IBaseLane<?>> extends IBaseObject<T> implements ILane<T>
{
    /**
     * status (passable, not passable)
     */
    private boolean m_passable = true;
    /**
     * defines the left upper position (row / column / height in cells / width in cells )
     */
    private final DoubleMatrix1D m_position;

    /**
     * ctor
     *
     * @param p_leftbottom left-bottom position
     * @param p_righttop right-up position
     * @param p_configuration agent configuration
     * @todo check parameter
     */
    protected IBaseLane( final IAgentConfiguration<T> p_configuration, final IEnvironment p_environment, final String p_functor,
                         final List<Integer> p_leftbottom, final List<Integer> p_righttop
    )
    {
        super( p_configuration, p_environment, p_functor );
        m_position = new DenseDoubleMatrix1D( new double[]{
            p_leftbottom.get( 0 ),
            p_leftbottom.get( 1 ),
            p_righttop.get( 0 ) - p_leftbottom.get( 0 ) + 1,
            p_righttop.get( 1 ) - p_leftbottom.get( 1 ) + 1
        } );
    }

    @Override
    protected Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object
    )
    {
        return Stream.of(
            CLiteral.from( "passable", CRawTerm.from( m_passable ) )
        );
    }
}
