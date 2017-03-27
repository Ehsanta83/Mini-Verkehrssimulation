package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.pedestrian;

import cern.colt.matrix.DoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IBaseMoveable;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle.CVehicle;
import org.lightjason.agentspeak.beliefbase.CBeliefbasePersistent;
import org.lightjason.agentspeak.beliefbase.storage.CSingleStorage;
import org.lightjason.agentspeak.common.CPath;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.ITerm;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;


/**
 * pedestrian class
 */
public final class CPedestrian extends IBaseMoveable<CPedestrian>
{



    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment
     * @param p_position
     */
    protected CPedestrian(
        final IAgentConfiguration<CPedestrian> p_configuration,
        final IEnvironment<?> p_environment,
        final DoubleMatrix1D p_position
    )
    {
        super( p_configuration, p_environment, "pedestrian", p_position );
    }


}
