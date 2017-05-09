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
    /**
     * check if the object can move in the grid
     *
     * @param p_grid grid
     * @param p_newposition new position of the object
     * @return if object can move
     */
    boolean moveable( final ObjectMatrix2D p_grid, final DoubleMatrix1D p_newposition );

    /**
     * move the object in the grid
     *
     * @param p_grid grid
     * @param p_newposition new position
     */
    void move( final ObjectMatrix2D p_grid, final DoubleMatrix1D p_newposition );

}
