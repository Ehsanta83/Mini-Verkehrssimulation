package org.lightjason.trafficsimulation;

import org.junit.Assert;
import org.lightjason.agentspeak.generator.IAgentGenerator;
import org.lightjason.trafficsimulation.actions.CBroadcast;
import org.lightjason.trafficsimulation.actions.CSend;
import org.lightjason.trafficsimulation.simulation.EObjectFactory;
import org.lightjason.trafficsimulation.simulation.IObject;
import org.lightjason.trafficsimulation.simulation.algorithm.routing.ERoutingFactory;
import org.lightjason.trafficsimulation.simulation.environment.IEnvironment;
import org.junit.Assume;
import org.junit.Before;
import org.lightjason.agentspeak.common.CCommon;
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.agent.IAgent;
import org.lightjason.agentspeak.language.score.IAggregation;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.LogManager;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;


/**
 * base test
 *
 * @todo fix documentation
 */
public abstract class IBaseTest
{
    protected IEnvironment m_environment;

    private final Map<String, IAgent<?>> m_agents = new ConcurrentHashMap<>();

    private final Set<IAction> m_actions = Stream.concat(
                                                Stream.of(
                                                    new CSend( m_agents ),
                                                    new CBroadcast( m_agents )
                                                ),
                                                CCommon.actionsFromPackage()
                                           ).collect( Collectors.toSet() );



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
                .generate( l_stream, m_actions.stream(), IAggregation.EMPTY )
                .generatesingle( 64, 64, 25, ERoutingFactory.JPSPLUS.get() )
                .raw();
        }
        catch ( final Exception l_exception )
        {
            l_exception.printStackTrace();
            assertTrue( false );
        }
    }

    /**
     * load configuration from yaml file
     */
    @Before
    public final void loadconfiguration()
    {
        CConfiguration.INSTANCE.loadfile( "src/main/resources/" + org.lightjason.trafficsimulation.CCommon.PACKAGEPATH + "configuration.yaml" );
    }

    protected final IAgentGenerator generator( final EObjectFactory p_factory, final String p_asl, final Object... p_arguments )
    {
        final Set<IAction> l_actions = org.lightjason.agentspeak.common.CCommon.actionsFromPackage().collect( Collectors.toSet() );

        try
            (
                final FileInputStream l_stream = new FileInputStream( p_asl );
            )
        {
            return  p_factory.generate(
                l_stream,
                l_actions.stream(),
                IAggregation.EMPTY,
                m_environment,
                p_arguments );
        }
        catch ( final Exception l_exeption )
        {
            l_exeption.printStackTrace();
            Assert.assertTrue( false );
        }
        return null;
    }

    /**
     * runs the object generation proecess
     *
     * @param p_file filename
     * @param p_factory generator
     * @param p_arguments generating arguments
     * @param <T> IObject
     * @return IObject
     */
    protected final <T extends IObject<?>> T generate( final String p_file, final EObjectFactory p_factory, final Object... p_arguments )
    {
        Assume.assumeNotNull( m_actions );
        Assume.assumeNotNull( m_environment );

        try
        (
            final FileInputStream l_stream = new FileInputStream( p_file );
        )
        {

            return p_factory.generate( l_stream, m_actions.stream(), IAggregation.EMPTY, m_environment, p_arguments ).generatesingle( p_arguments ).raw();

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
     * @param p_number number of agents
     * @param p_arguments generating arguments
     * @param <T> IObject
     * @return List of IObjects
     */
    protected final <T extends IObject<?>> List<T> generatemultiple( final String p_file, final EObjectFactory p_factory, final int p_number, final Object... p_arguments )
    {
        Assume.assumeNotNull( m_actions );
        Assume.assumeNotNull( m_environment );

        try
        (
            final FileInputStream l_stream = new FileInputStream( p_file );
        )
        {

            return p_factory.generate( l_stream, m_actions.stream(), IAggregation.EMPTY, m_environment )
                .generatemultiple( p_number, p_arguments )
                .map( IAgent::<T>raw )
                .collect( Collectors.toList() );

        }
        catch ( final Exception l_exception )
        {
            l_exception.printStackTrace();
            assertTrue( false );
            return null;
        }
    }

}
