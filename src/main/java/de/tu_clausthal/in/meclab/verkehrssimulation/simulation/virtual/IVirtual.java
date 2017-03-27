package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.ui.ISprite;


/**
 * virual interface
 */
public interface IVirtual<T extends IVirtual<?>> extends IObject<T>, ISprite
{
}
