package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

/**
 * vehicles way class
 *
 * @author Ehsan Tatasadi
 */
public class CVehiclesWay extends IBaseWay
{
    /**
     * constructor of the vehicles way class
     *
     * @param p_lanesCount the count of the lanes
     * @param p_blocksCountInALane the count of the blocks in a lane
     */
    public CVehiclesWay( final int p_lanesCount, final int p_blocksCountInALane )
    {
        super( p_lanesCount, p_blocksCountInALane );
    }
}
