package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.pedestrian.CPedestrianGenerator;
import org.lightjason.agentspeak.generator.IGenerator;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Locale;

/**
 * movable factory
 */
public enum EMovableFactory
{
    PEDESTRIAN,
    CAR,
    BUS;

    /**
     * get generator
     *
     * @param p_asl asl file of the agent
     * @return generator
     * @throws Exception exception
     */
    public final IGenerator<IMovableAgent> generate( final InputStream p_asl ) throws Exception
    {
        switch ( this )
        {
            case PEDESTRIAN: return new CPedestrianGenerator( p_asl, null, null, null );

            case CAR: return null;

            case BUS: return null;

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
