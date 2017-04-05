package org.lightjason.trafficsimulation.simulation.virtual;

import java.util.Locale;

/**
 * area type
 */
public enum EArea
{
    DIRECTLANE,
    TURNLEFTLANE,
    INTERSECTION,
    SIDEWALK,
    FORBIDDEN;

    /**
     * get enum from string
     *
     * @param p_name string name
     * @return area
     */
    public static EArea from( final String p_name )
    {
        return EArea.valueOf( p_name.toUpperCase( Locale.ROOT ) );
    }
}
