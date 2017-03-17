package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle;

import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IAgent;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import org.lightjason.agentspeak.language.execution.IVariableBuilder;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.InputStream;
import java.util.*;

/**
 * agent generator for dynamic / moving agents
 */
public class CVehicleGenerator extends IBaseAgentGenerator<IAgent>
{
    /**
     * environment reference
     */
    private final IEnvironment m_environment;
    /**
     * random generator
     */
    private final Random m_random = new Random();

    /**
     * ctor
     *
     * @param p_environment environment
     * @param p_stream input asl stream
     * @param p_actions action set
     * @param p_aggregation aggregation set
     * @throws Exception on any error
     */
    public CVehicleGenerator( final IEnvironment p_environment, final InputStream p_stream,
                             final Set<IAction> p_actions, final IAggregation p_aggregation
    ) throws Exception
    {
        super( p_stream, p_actions, p_aggregation, Collections.emptySet(), IVariableBuilder.EMPTY );
        m_environment = p_environment;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public IAgent generatesingle( final Object... p_data )
    {
        final List<Map<String, Object>> l_randomgeneratepositions = (List<Map<String, Object>>) p_data[0];
        int l_random = m_random.nextInt( l_randomgeneratepositions.size() );
        final DenseDoubleMatrix1D l_position = new DenseDoubleMatrix1D(
            new double[]
                {
                    //row
                    ( (List<Integer>) l_randomgeneratepositions.get( l_random ).get( "position" ) ).get( 1 ),
                    //col
                    ( (List<Integer>) l_randomgeneratepositions.get( l_random ).get( "position" ) ).get( 0 )
                }
        );
        while ( !m_environment.empty( l_position ) )
        {
            l_random = m_random.nextInt( 4 );
            l_position.setQuick( 0, ( (List<List<Integer>>) p_data[0] ).get( l_random ).get( 1 ) );
            l_position.setQuick( 1, ( (List<List<Integer>>) p_data[0] ).get( l_random ).get( 0 ) );
        }

        return new CVehicle(
            m_environment,
            m_configuration,
            l_position,
            (int) l_randomgeneratepositions.get( l_random ).get( "rotation" ),
            //type
            (String) p_data[1],
            //width
            (int) p_data[2],
            //height
            (int) p_data[3]
        );
    }
}
