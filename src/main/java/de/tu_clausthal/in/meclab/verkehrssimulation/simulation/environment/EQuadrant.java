package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.SparseDoubleMatrix1D;
import cern.jet.math.Functions;


/**
 * quadrant
 *
 * @see https://en.wikipedia.org/wiki/Quadrant_(plane_geometry)
 * @see http://gamedev.stackexchange.com/questions/96099/is-there-a-quick-way-to-determine-if-a-vector-is-in-a-quadrant
 */
public enum EQuadrant
{
    UPPERRIGHT,
    UPPERLEFT,
    BOTTOMLEFT,
    BOTTOMRIGHT;

    /**
     * returns a quadrant based on the vector
     *
     * @param p_position position
     * @return quadrant relativ to zero position
     */
    public static EQuadrant quadrant( final DoubleMatrix1D p_position )
    {
        return EQuadrant.quadrant( new SparseDoubleMatrix1D( p_position.size() ), p_position );
    }

    /**
     * returns a quadrant based on the vector
     *
     * @param p_zero zero position
     * @param p_position position
     * @return quadrant relative to zero position
     */
    public static EQuadrant quadrant( final DoubleMatrix1D p_zero, final DoubleMatrix1D p_position )
    {
        final DoubleMatrix1D l_difference = new DenseDoubleMatrix1D( p_position.toArray() ).assign( p_zero, Functions.minus );
        return l_difference.getQuick( 1 ) < 0
               ? l_difference.getQuick( 0 ) < 0
                 ? BOTTOMLEFT
                 : UPPERLEFT
               : l_difference.getQuick( 0 ) < 0
                 ? BOTTOMRIGHT
                 : UPPERRIGHT;
    }

}
