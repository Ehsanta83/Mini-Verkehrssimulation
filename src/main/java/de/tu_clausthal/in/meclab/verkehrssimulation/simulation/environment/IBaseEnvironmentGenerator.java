package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.algorithm.routing.IRouting;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.Set;


/**
 * environment agent generator
 *
 * @bug fix docu
 */
public abstract class IBaseEnvironmentGenerator extends IBaseAgentGenerator<IEnvironment>
{
    public IBaseEnvironmentGenerator( final InputStream p_stream, final Set<IAction> p_actions,
                                      final IAggregation p_aggregation
    ) throws Exception
    {
        super( p_stream, p_actions, p_aggregation );
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public final IEnvironment generatesingle( final Object... p_data )
    {
        return this.generate(
            m_configuration,
            ( (Number) p_data[0] ).intValue(), ( (Number) p_data[1] ).intValue(), ( (Number) p_data[2] ).doubleValue(),
            (IRouting) p_data[3]
        );
    }

    /**
     * generate
     *
     * @param p_configuration
     * @param p_rows
     * @param p_volumns
     * @param p_cellsize
     * @param p_routing
     * @return
     */
    protected abstract IEnvironment generate( final IAgentConfiguration<IEnvironment> p_configuration,
                                              final int p_rows, final int p_columns, final double p_cellsize,
                                              final IRouting p_routing
    );

}
