
package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.algorithm.routing;

/**
 * factory for creating routing objects
 */
public enum ERoutingFactory
{
    JPSPLUS( new CJPSPlus() );

    /**
     * routing object
     */
    private final IRouting m_routing;

    /**
     * ctor
     *
     * @param p_routing routing object
     */
    ERoutingFactory( final IRouting p_routing )
    {
        m_routing = p_routing;
    }

    /**
     * returns routing object
     *
     * @return routing object
     */
    public final IRouting get()
    {
        return m_routing;
    }
}
