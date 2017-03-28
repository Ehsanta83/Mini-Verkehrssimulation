package de.tu_clausthal.in.meclab.verkehrssimulation;

import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.EObjectFactory;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.algorithm.routing.ERoutingFactory;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.environment.IEnvironment;
import org.junit.Assume;
import org.junit.Before;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.agent.IAgent;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.FileInputStream;
import java.util.List;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;


/**
 * base test
 *
 * @todo fix documentation
 */
public abstract class IBaseTest
{

    private static final Set<IAction> ACTIONS = org.lightjason.agentspeak.common.CCommon.actionsFromPackage().collect( Collectors.toSet() );

    protected IEnvironment m_environment;


    static
    {
        LogManager.getLogManager().reset();
    }

    /**
     * initialize environment
     *
     * @throws Exception on any error
     */
    @Before
    public final void initializeenvironment() throws Exception
    {
        try
        (
            final FileInputStream l_stream = new FileInputStream( "src/test/resources/environment.asl" );
        )
        {
            m_environment = EObjectFactory.ENVIRONMENT
                .generate( l_stream, ACTIONS, IAggregation.EMPTY )
                .generatesingle( 100, 100, 25, ERoutingFactory.JPSPLUS.get() )
                .raw();
        }
        catch ( final Exception l_exception )
        {
            l_exception.printStackTrace();
            assertTrue( false );
        }
    }

    /**
     * runs the object generation proecess
     *
     * @param p_file filename
     * @param p_factory generator
     * @param p_arguments generating arguments
     * @param <T>
     * @return
     */
    protected final <T extends IObject<?>> T generate( final String p_file, final EObjectFactory p_factory, final Object... p_arguments )
    {
        Assume.assumeNotNull( m_environment );

        try
        (
            final FileInputStream l_stream = new FileInputStream( p_file );
        )
        {

            return p_factory.generate( l_stream, ACTIONS, IAggregation.EMPTY, m_environment ).generatesingle( p_arguments ).raw();

        }
        catch ( final Exception l_exception )
        {
            l_exception.printStackTrace();
            assertTrue( false );
            return null;
        }
    }


    /**
     * runs the object generation proecess
     *
     * @param p_file filename
     * @param p_factory generator
     * @param p_number
     * @param p_arguments generating arguments
     * @param <T>
     * @return
     */
    protected final <T extends IObject<?>> List<T> generate( final String p_file, final EObjectFactory p_factory, final int p_number, final Object... p_arguments )
    {
        Assume.assumeNotNull( m_environment );

        try
        (
            final FileInputStream l_stream = new FileInputStream( p_file );
        )
        {

            return p_factory.generate( l_stream, ACTIONS, IAggregation.EMPTY, m_environment ).generatemultiple( p_number, p_arguments ).map( IAgent::<T>raw ).collect( Collectors.toList() );

        }
        catch ( final Exception l_exception )
        {
            l_exception.printStackTrace();
            assertTrue( false );
            return null;
        }
    }

}
