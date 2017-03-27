package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle;

import cern.colt.matrix.DoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IBaseMoveable;
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
import org.lightjason.agentspeak.beliefbase.CBeliefbasePersistent;
import org.lightjason.agentspeak.beliefbase.storage.CSingleStorage;
import org.lightjason.agentspeak.common.CPath;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.ITerm;

import java.util.Arrays;
import java.util.stream.Stream;


/**
 * vehicle class
 */
@IAgentAction
public class CVehicle extends IBaseMoveable<CVehicle>
{
    /**
     * width
     */
    private int m_width;
    /**
     * height
     */
    private int m_height;

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment
     * @param p_position
     */
    public CVehicle(
        final IAgentConfiguration<CVehicle> p_configuration,
        final IEnvironment<?> p_environment,
        final DoubleMatrix1D p_position
    )
    {
        super( p_configuration, p_environment, "vehicle", p_position );


    }


    @IAgentActionFilter
    @IAgentActionName( name = "accelerate" )
    private void accelerate( final Number p_value )
    {

    }

    @IAgentActionFilter
    @IAgentActionName( name = "deccelerate" )
    private void deccelerate( final Number p_value )
    {

    }

}
