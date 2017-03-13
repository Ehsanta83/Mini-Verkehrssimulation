package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IExecutable;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.List;
import java.util.stream.Stream;

/**
 *
 */
public abstract class IBaseLane implements ILane
{
    /**
     * status (passable, not passable)
     */
    protected boolean m_passable;
    /**
     * defines the left upper position (row / column / height in cells / width in cells )
     */
    private final DoubleMatrix1D m_position;

    /**
     * ctor
     *
     * @param p_leftbottom left-bottom position
     * @param p_righttop right-up position
     */
    public IBaseLane(final List<Integer> p_leftbottom, final List<Integer> p_righttop )
    {
        m_position = new DenseDoubleMatrix1D( new double[]{
                p_leftbottom.get( 0 ), p_righttop.get( 0 ),
                p_leftbottom.get( 1 ), p_righttop.get( 1 ),
                p_righttop.get( 0 ) - p_leftbottom.get( 0 ) + 1,
                p_righttop.get( 1 ) - p_leftbottom.get( 1 ) + 1
        } );
    }

    @Override
    public Sprite sprite() {
        return null;
    }

    @Override
    public void spriteinitialize(float p_unit) {

    }

    @Override
    public DoubleMatrix1D position() {
        return null;
    }

    @Override
    public IExecutable call() throws Exception {
        return null;
    }
}
