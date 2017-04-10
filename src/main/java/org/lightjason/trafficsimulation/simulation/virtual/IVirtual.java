package org.lightjason.trafficsimulation.simulation.virtual;

import org.lightjason.trafficsimulation.simulation.IObject;


/**
 * virual interface
 *
 * @todo can we reduce the virtual structure to an "area" ?
 */
public interface IVirtual<T extends IVirtual<?>> extends IObject<T>
{
}
