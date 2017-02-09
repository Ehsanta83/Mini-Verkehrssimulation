
package de.tu_clausthal.in.meclab.verkehrssimulation.simulation;

import cern.colt.matrix.DoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.ui.ISprite;

/**
 * object interface
 */
public interface IObject extends ISprite
{

    /**
     * returns the current position of the object
     *
     * @return position tuple
     */
    DoubleMatrix1D position();

}
