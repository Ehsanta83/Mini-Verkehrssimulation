
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
    DoubleMatrix1D position();

    <T extends IObject> Stream<ILiteral> literal( final T... p_object );
    <T extends IObject> Stream<ILiteral> literal( final Stream<T> p_object );
}
