package org.lightjason.trafficsimulation.simulation.movable;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;
import org.lightjason.trafficsimulation.simulation.IObject;


/**
 * moveable interface
 *
 * @param <T> movable template
 */
public interface IMoveable<T extends IMoveable<?>> extends IObject<T>
{

    boolean moveable( final ObjectMatrix2D p_grid, final DoubleMatrix1D p_newposition );

}
