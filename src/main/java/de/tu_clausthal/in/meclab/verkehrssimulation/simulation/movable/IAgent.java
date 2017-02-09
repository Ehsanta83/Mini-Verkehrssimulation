package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;

/**
 * interface for the agentrouting,
 * inheritance from Java callable (for multithreading execution)
 */
public interface IAgent extends IObject, org.lightjason.agentspeak.agent.IAgent<IAgent>
{
}
