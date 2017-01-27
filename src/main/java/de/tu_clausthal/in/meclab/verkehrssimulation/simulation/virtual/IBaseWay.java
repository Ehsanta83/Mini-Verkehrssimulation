package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IMovable;

/**
 * Way abstract class
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
     * @param p_lanesCount the count of the lanes
     * @param p_blocksCountInALane the count of the blocks in a lane
     */
    protected IBaseWay( final int p_lanesCount, final int p_blocksCountInALane )
    {
        m_blocks = new IMovable[p_lanesCount][p_blocksCountInALane];
    }

    /**
     * occupy a block of a lane with an IMovable object
     *
     * @param p_movable the IMovable object
     * @param p_coordination the coordination of the IMovable object in the way [indexOfLane, indexOfBlock]
     */
    public void occupyBlock( final IMovable p_movable,  final int[] p_coordination )
    {
        m_blocks[p_coordination[0]][p_coordination[1]] = p_movable;
    }

    /**
     * empty a block of a lane
     *
     * @param p_coordination the coordination of the IMovable object in the way [indexOfLane, indexOfBlock]
     */
    public void emptyBlock( final int[] p_coordination )
    {
        m_blocks[p_coordination[0]][p_coordination[1]] = null;
    }

    /**
     * if the block is occupied
     *
     * @param p_coordination the coordination of the IMovable object in the way [indexOfLane, indexOfBlock]
     * @return if the block is occupied
     */
    public boolean isBlockOccupied( final int[] p_coordination )
    {
        return m_blocks[p_coordination[0]][p_coordination[1]] != null;
    }

    /**
     * get the next occupied block index from the current index in the driving direction in a lane
     *
     * @param p_coordination the coordination of the IMovable object in the way [indexOfLane, indexOfBlock]
     * @return the next occupied block index in the lane
     */
    public int getNextOccupiedBlockIndexFromIndexInALane( final int[] p_coordination )
    {
        int nextOccupiedBlockIndex = -1;
        for ( int i = ( p_coordination[1] == -1 ) ? 0 : p_coordination[1] + 1; i < m_blocks[p_coordination[0]].length; i++ )
        {
            if ( m_blocks[p_coordination[0]][i] != null )
            {
                nextOccupiedBlockIndex = i;
                break;
            }
        }
        return nextOccupiedBlockIndex;
    }
}
