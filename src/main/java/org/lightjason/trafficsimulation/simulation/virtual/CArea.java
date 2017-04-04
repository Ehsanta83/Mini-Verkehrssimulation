package org.lightjason.trafficsimulation.simulation.virtual;

import cern.colt.matrix.DoubleMatrix1D;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.score.IAggregation;
import org.lightjason.trafficsimulation.simulation.IBaseObject;
import org.lightjason.trafficsimulation.simulation.IBaseObjectGenerator;
import org.lightjason.trafficsimulation.simulation.IObject;
import org.lightjason.trafficsimulation.simulation.environment.EDirection;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

/**
 * area class
 */
public final class CArea extends IBaseObject<CArea> implements IVirtual<CArea>
{
    /**
     * functor
     */
    private static final String FUNCTOR = "area";
    /**
     *
     */
    private static final AtomicLong COUNTER = new AtomicLong();
    /**
     * if the area is passable
     */
    private final boolean m_passable;
    /**
     * the type of area
     */
    private final EArea m_type;
    /**
     * in which direction one can move in this area
     */
    private final Stream<EDirection> m_directions;
    /**
     * position
     */
    private DoubleMatrix1D m_position;


    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment   environment reference
     * @param p_name          name of the object
     */
    private CArea( final IAgentConfiguration<CArea> p_configuration, final IEnvironment p_environment, final String p_name,
                   final boolean p_passable, final EArea p_type, final Stream<EDirection> p_directions, final DoubleMatrix1D p_position )
    {
        super( p_configuration, p_environment, FUNCTOR, p_name, p_position );
        m_passable = p_passable;
        m_type = p_type;
        m_directions = p_directions;
    }

    @Override
    protected Stream<ILiteral> individualliteral( final Stream<IObject<?>> p_object )
    {
        return Stream.of(
            CLiteral.from( "passable", CRawTerm.from( m_passable ) ),
            CLiteral.from( "type", CRawTerm.from( m_type.toString().toLowerCase() ) ),
            CLiteral.from( "directions",  m_directions.map( i -> CRawTerm.from( i.toString().toLowerCase() ) ) )
        );
    }

    /**
     * generator class
     */
    public static final class CGenerator extends IBaseObjectGenerator<CArea>
    {
        /**
         * ctor
         *
         * @param p_stream stream
         * @param p_actions actions
         * @param p_aggregation aggregation
         * @param p_environment environment
         * @throws Exception on any error
         */
        public CGenerator( final InputStream p_stream,
                           final Stream<IAction> p_actions,
                           final IAggregation p_aggregation,
                           final IEnvironment p_environment,
                           final Object... p_arguments
        ) throws Exception
        {
            super( p_stream, p_actions, p_aggregation, CArea.class, p_environment );
        }

        @Override
        @SuppressWarnings( "unchecked" )
        protected final Pair<CArea, Stream<String>> generate( final Object... p_data )
        {
            return new ImmutablePair<>(
                new CArea(
                    m_configuration,
                    m_environment,
                    MessageFormat.format( "{0} {1}", FUNCTOR, COUNTER.getAndIncrement() ),
                    (boolean) p_data[0],
                    (EArea) p_data[1],
                    (Stream<EDirection>) p_data[2],
                    (DoubleMatrix1D) p_data[3]
                ),

                Stream.of( FUNCTOR )
            );
        }

        /**
         * reset the object counter
         */
        public static void resetcount()
        {
            COUNTER.set( 0 );
        }
    }
}
