package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import cern.colt.matrix.DoubleMatrix1D;

/**
 * lane interface
 */
public interface ILane extends IVirtual
{
    /**
     * returns the current position of the object
     *
     * @return position tuple
     */
    DoubleMatrix1D position();
}
