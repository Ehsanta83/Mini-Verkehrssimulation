package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IExecutable;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.ui.ITileMap;
import cern.colt.matrix.DoubleMatrix1D;

/**
 * environment interface
 */
public interface IEnvironment extends IExecutable, ITileMap
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


    // --- dynamic object access -------------------------------------------------------------------------------------------------------------------------------

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
