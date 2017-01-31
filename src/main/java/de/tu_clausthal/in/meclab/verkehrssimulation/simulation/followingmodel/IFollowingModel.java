package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.followingmodel;

/**
 * following model interface
 */
public interface IFollowingModel
{
    /**
     * change the velocity of the vehicle according to the following model
     *
     * @param p_velocity               velocity of the vehicle
     * @param p_blockIndex             block index of the street
     * @param p_nextOccupiedBlockIndex next occupied block index
     * @return new velocity of the vehicle
     */
    int applyModelToAVehicle( int p_velocity, int p_blockIndex, int p_nextOccupiedBlockIndex );
}
