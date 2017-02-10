package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.followingmodel;

import java.util.Random;

/**
 * nagel-schreckenberg class
 */
public class CNagelSchreckenberg implements IFollowingModel
{
    /**
     * singleton instance
     */
    public static final CNagelSchreckenberg INSTANCE = new CNagelSchreckenberg();

    @Override
    public int applyModelToAVehicle( int p_velocity, int p_blockIndex, int p_nextOccupiedBlockIndex )
    {
        int l_newVelocity = p_velocity;
        //1. rule in Nagel-Schreckenberg-Modell
        if ( l_newVelocity < 20 )
            l_newVelocity++;
        //2. rule in Nagel-Schreckenberg-Modell
        if ( p_nextOccupiedBlockIndex != -1 )
        {
            if ( p_blockIndex == -1 && p_nextOccupiedBlockIndex <= 20 )
            {
                l_newVelocity = p_nextOccupiedBlockIndex;
            }
            else
            {
                if ( l_newVelocity > p_nextOccupiedBlockIndex - p_blockIndex - 1 )
                {
                    l_newVelocity = p_nextOccupiedBlockIndex - p_blockIndex - 1;
                }
            }
        }
        //3. rule in Nagel-Schreckenberg-Modell
        // get a random number between 1,2,3
        final Random l_randomGenerator = new Random();
        final int l_random = l_randomGenerator.nextInt( 3 );
        if ( l_random == 0 && l_newVelocity >= 1 )
            l_newVelocity--;
        return l_newVelocity;
    }
}
