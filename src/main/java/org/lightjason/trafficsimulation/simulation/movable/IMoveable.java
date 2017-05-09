package org.lightjason.trafficsimulation.simulation.movable;

import org.lightjason.trafficsimulation.simulation.IObject;


/**
 * moveable interface
 *
 * @param <T> movable template
 */
public interface IMoveable<T extends IMoveable<?>> extends IObject<T>
{

    //boolean movable( final ObjectMatrix2D p_grid, final DoubleMatrix1D p_newposition );

}
