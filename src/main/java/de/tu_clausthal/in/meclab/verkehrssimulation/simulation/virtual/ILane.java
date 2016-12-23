package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

/**
 * lane abstract class
 *
 * @author Ehsan Tatasadi
 */
public abstract class ILane
{
    /**
     * array of blocks of a lane
     * if a block is occupied the index value in array is true
     */
    private boolean[] m_blocks;

    /**
     * constructor
     */
    protected ILane( final int p_numberOfBlocks )
    {
        m_blocks = new boolean[p_numberOfBlocks];
    }

    /**
     * occupy the block of the lane
     *
     * @param p_blockIndex the index of the block
     */
    public void occupyBlock( final int p_blockIndex )
    {
        m_blocks[p_blockIndex] = true;
    }

    /**
     * empty the block of the lane
     * @param p_blockIndex the index of the block
     */
    public void emptyBlock( final int p_blockIndex )
    {
        m_blocks[p_blockIndex] = false;
    }

    /**
     * if the block is occupied
     *
     * @param p_blockIndex the index of the block
     * @return if block is occupied
     */
    public boolean isBlockOccupied( final int p_blockIndex )
    {
        return m_blocks[p_blockIndex];
    }

    /**
     * get the next occupied block index from the current index in the driving direction
     *
     * @param p_blockIndex the index of the block
     * @return the next occupied block index
     */
    public int getNextOccupiedBlockIndexFromIndex( final int p_blockIndex )
    {
        int nextOccupiedBlockIndex = -1;
        for ( int i = ( p_blockIndex == -1 ) ? 0 : p_blockIndex + 1; i < m_blocks.length; i++ )
        {
            if ( m_blocks[i] )
            {
                nextOccupiedBlockIndex = i;
                break;
            }
        }
        return nextOccupiedBlockIndex;
    }
}
