package org.lightjason.trafficsimulation.simulation.environment;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;
import org.lightjason.trafficsimulation.simulation.IBaseObject;
import org.lightjason.trafficsimulation.simulation.IObject;
import org.lightjason.trafficsimulation.simulation.movable.IMoveable;
import org.lightjason.trafficsimulation.simulation.virtual.CArea;

import java.util.List;
import java.util.stream.Stream;


/**
 * environment interface
 */
public interface IEnvironment extends IObject<IEnvironment>
{
    /**
     * calculate route
     *
     * @param p_start start position
     * @param p_end target position
     * @return list of tuples of the cellindex
     */
    List<DoubleMatrix1D> route( final DoubleMatrix1D p_start, final DoubleMatrix1D p_end );

    /**
     * calculate estimated time of a route
     *
     * @param p_route current route
     * @param p_speed current speed
     * @return estimated time
     */

    double routestimatedtime( final Stream<DoubleMatrix1D> p_route, final double p_speed );

    /**
     * sets an object to the position and changes the object position
     *
     * @param p_object object, which should be moved (must store the current position)
     * @param p_position new position
     * @return updated object or object which uses the cell
     */
    IMoveable move( final IMoveable p_object, final DoubleMatrix1D p_position );

    /**
     * returns an object from the given position
     *
     * @param p_position position vector
     * @return object or null
     */
    IObject get( final DoubleMatrix1D p_position );

    /**
     * removes an element from a position
     *
     * @param p_object element
     * @return element
     */
    IObject remove( final IObject p_object );

    /**
     * checks if a position is empty
     *
     * @param p_position position
     * @return boolean result
     */
    boolean empty( final DoubleMatrix1D p_position );

}
