package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;

import java.util.List;

/**
 * lane class
 */
public class CLane implements ILane
{
    /**
     * defines the left upper position (row / column / height in cells / width in cells )
     */
    private final DoubleMatrix1D m_position;

    /**
     *
     * @param p_leftbottom left-bottom position
     * @param p_righttop right-up position
     */
    public CLane( final List<Integer> p_leftbottom, final List<Integer> p_righttop )
    {
        m_position = new DenseDoubleMatrix1D( new double[]{
            p_leftbottom.get( 0 ), p_righttop.get( 0 ),
            p_leftbottom.get( 1 ), p_righttop.get( 1 ),
            p_righttop.get( 0 ) - p_leftbottom.get( 0 ) + 1,
            p_righttop.get( 1 ) - p_leftbottom.get( 1 ) + 1
        } );
    }

    @Override
    public DoubleMatrix1D position()
    {
        return m_position;
    }
}
