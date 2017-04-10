package org.lightjason.trafficsimulation.runtime;

import org.lightjason.agentspeak.agent.IAgent;

import java.util.stream.Stream;


/**
 * a single experiment run
 */
public interface IExperiment
{

    /**
     * returns a stream of the agents
     *
     * @return agent stream
     */
    Stream<IAgent<?>> agents();

    /**
     * maximum simulation steps
     *
     * @return simulation steps
     */
    int simulationsteps();

    /**
     * returns tha simulation statistic
     *
     * @return statistic
     */
    IStatistic statistic();

    /**
     * execute agents in parallel
     *
     * @return boolean flag for parallel execution
     */
    boolean parallel();

}
