package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import cern.colt.matrix.DoubleMatrix1D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import de.tu_clausthal.in.meclab.verkehrssimulation.CCommon;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.Arrays;
import java.util.stream.Stream;


/**
 * vehicles way class
 */
public class CVehiclesWay extends IBaseWay<CVehiclesWay>
{

    /**
     * constructor
     *
     * @param p_leftbottom leftbottom position in grid
     * @param p_righttop righttop position in grid
     * @param p_rotation rotation
     */
    public CVehiclesWay( final IAgentConfiguration<CVehiclesWay> p_configuration, final DoubleMatrix1D p_leftbottom, final DoubleMatrix1D p_righttop,
                         final int p_rotation
    )
    {
        super( p_configuration, p_leftbottom, p_righttop, p_rotation );
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
