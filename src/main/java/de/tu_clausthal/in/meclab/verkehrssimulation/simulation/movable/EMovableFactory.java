package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.pedestrian.CPedestrianGenerator;
import org.lightjason.agentspeak.generator.IGenerator;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

/**
 * movable factory
 */
public enum EMovableFactory
{
    PEDESTRIAN,
    VEHICLE;

    /**
     * get generator
     *
     * @param p_asl asl file of the agent
     * @return generator
     * @throws Exception exception
     */
    public final IGenerator<IMovableAgent> generate( final InputStream p_asl, final Map<String, Object> p_distributionconfiguration, final Map<String, Object> p_agentconfiguration) throws Exception
    {
        switch ( this )
        {
            case PEDESTRIAN: return new CPedestrianGenerator( p_asl, p_distributionconfiguration, p_agentconfiguration, null, null, null );

            case VEHICLE: return null;

            default:
                throw new RuntimeException( MessageFormat.format( "not set {0}", this ) );
        }
    }

    /**
     * get enum from string
     * @param p_name string name
     * @return a movable
     */
    public static EMovableFactory from( final String p_name )
    {
        return EMovableFactory.valueOf( p_name.toUpperCase( Locale.ROOT ) );
    }
}
