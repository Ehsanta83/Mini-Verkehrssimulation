package org.lightjason.trafficsimulation.simulation.movable;

import org.lightjason.trafficsimulation.simulation.IObject;
import org.lightjason.trafficsimulation.simulation.virtual.EArea;

import java.util.stream.Stream;


/**
 * moveable interface
 *
 * @param <T> movable template
 */
public interface IMoveable<T extends IMoveable<?>> extends IObject<T>
{
    /**
     * get the areas in which a moveable is allowed to move
     *
     * @return stream of areas
     */
    Stream<EArea> allowedareas();
}
