
package de.tu_clausthal.in.meclab.verkehrssimulation.simulation;

import cern.colt.matrix.DoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.ui.ISprite;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.stream.Stream;

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
    @Deprecated
    DoubleMatrix1D position();

    /**
     * get literal of the object
     *
     * @param p_object objects
     * @param <T> type of object
     * @return stream of literal
     */
    <T extends IObject> Stream<ILiteral> literal( final T... p_object );

    /**
     * get literal of the object
     *
     * @param p_object objects
     * @param <T> type of object
     * @return stream of literal
     */
    <T extends IObject> Stream<ILiteral> literal( final Stream<T> p_object );
}
