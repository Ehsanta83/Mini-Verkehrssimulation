package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.ui.ISprite;


/**
 * static interface
 */
public interface IStatic<T extends IStatic<?>> extends IObject<T>, ISprite
{
}
