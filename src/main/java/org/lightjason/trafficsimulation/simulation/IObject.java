package org.lightjason.trafficsimulation.simulation;

import cern.colt.matrix.DoubleMatrix1D;
import org.lightjason.agentspeak.agent.IAgent;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.stream.Stream;


/**
 * object interface
 */
public interface IObject<T extends IAgent<?>> extends IAgent<T>
{

    /**
     * get literal of the object
     *
     * @param p_object objects
     * @return stream of literal
     */
    Stream<ILiteral> literal( final IObject<?>... p_object );

    /**
     * get literal of the object
     *
     * @param p_object objects
     * @return stream of literal
     */
    Stream<ILiteral> literal( final Stream<IObject<?>> p_object );

    /**
     * name of the object
     *
     * @return string name
     */
    String name();

    /**
     * position of the object
     * @return position
     */
    DoubleMatrix1D position();

}
