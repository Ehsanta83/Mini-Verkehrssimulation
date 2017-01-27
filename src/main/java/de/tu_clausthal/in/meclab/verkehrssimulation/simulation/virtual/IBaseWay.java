package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IMovable;

/**
 * lanes abstract class
 *
 * @author Ehsan Tatasadi
 */
public abstract class IBaseWay
{
    /**
     * array of blocks in an IBaseWay
     * a block can be occupied with an IMovable object
     */
    private IMovable[][] m_blocks;

    /**
     * constructor
     *
     * @param p_lanesCount the count of lanes
     * @param p_blocksCountInALane the count of blocks in a lane
     */
    protected IBaseWay( final int p_lanesCount, final int p_blocksCountInALane )
    {
        m_blocks = new IMovable[p_lanesCount][p_blocksCountInALane];
    }

    /**
     * occupy a block of a lane with an IMovable object
     *
     * @param p_movable the IMovable object
     * @param p_laneIndex the lane index of the object
     * @param p_blockIndex the block index in the lane
     */
    public void occupyBlock( final IMovable p_movable,  final int p_laneIndex, final int p_blockIndex )
    {
        m_blocks[p_laneIndex][p_blockIndex] = p_movable;
    }

    /**
     * empty a block of a lane
     *
     * @param p_laneIndex the lane index of the object
     * @param p_blockIndex the block index in the lane
     */
    public void emptyBlock( final int p_laneIndex, final int p_blockIndex )
    {
        m_blocks[p_laneIndex][p_blockIndex] = null;
    }

    /**
     * if the block is occupied
     *
     * @param p_laneIndex the lane index of the object
     * @param p_blockIndex the block index in the lane
     * @return if the block is occupied
     */
    public boolean isBlockOccupied( final int p_laneIndex, final int p_blockIndex )
    {
        return m_blocks[p_laneIndex][p_blockIndex] != null;
    }

    /**
     * get the next occupied block index from the current index in the driving direction in a lane
     *
     * @param p_blockIndex the index of the block in the lane
     * @return the next occupied block index in the lane
     */
    public int getNextOccupiedBlockIndexFromIndexInALane( final int p_laneIndex, final int p_blockIndex )
    {
        int nextOccupiedBlockIndex = -1;
        for ( int i = ( p_blockIndex == -1 ) ? 0 : p_blockIndex + 1; i < m_blocks[p_laneIndex].length; i++ )
        {
            if ( m_blocks[p_laneIndex][i] != null )
            {
                nextOccupiedBlockIndex = i;
                break;
            }
        }
        return nextOccupiedBlockIndex;
    }
}
