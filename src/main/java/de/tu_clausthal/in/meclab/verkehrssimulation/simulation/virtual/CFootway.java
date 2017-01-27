package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

/**
 * footway class
 *
 * @author Ehsan Tatasadi
 */
public class CFootway extends IBaseWay
{
    /**
     * constructor of footway class
     *
     * @param p_lanesCount the count of lanes
     * @param p_blocksCountInALane the count of blocks in a lane
     */
    public CFootway( final int p_lanesCount, final int p_blocksCountInALane )
    {
        super( p_lanesCount, p_blocksCountInALane );
    }
}
