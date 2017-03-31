package org.lightjason.trafficsimulation.simulation.environment;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;
import org.lightjason.trafficsimulation.simulation.IObject;

import java.util.List;
import java.util.stream.Stream;


/**
 * environment interface
 *
 * @todo can be build the full environment e.g. lane structure, car generators and other things to an agent execution model, so can the environment agent create the full simulation structure?
 */
public interface IEnvironment extends IObject<IEnvironment>
{

    // --- main elements ---------------------------------------------------------------------------------------------------------------------------------------

    /**
     * clip position data
     *
     * @param p_position position vector
     * @return modified clipped vector
     */
    DoubleMatrix1D clip( final DoubleMatrix1D p_position );

    /**
     * returns the number of rows
     *
     * @return rows
     */
    int row();

    /**
     * returns the number of columns
     *
     * @return columns
     */
    int column();

    /**
     * returns the cell size
     *
     * @return cell size
     */
    int cellsize();

    /**
     * returns lanes positions
     *
     * @return matrix of lane positions
     */
    ObjectMatrix2D lanespositions();


    // --- dynamic object access -------------------------------------------------------------------------------------------------------------------------------

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
    IObject move( final IObject p_object, final DoubleMatrix1D p_position );

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

    /**
     * checks if a position is inside the environment
     *
     * @param p_position position
     * @return boolean result
     */
    boolean isinside( final DoubleMatrix1D p_position );

}
