package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stationary.trafficlight;

/**
 * traffic light enumeration interface
 *
 * @bug remove texture for jetty
 */
public interface ITrafficLightColor<T extends Enum<?>>
{

    /**
     * returns the path of the light texture
     *
     * @return
     */
    String path();

    /**
     * changes the color
     *
     * @return new color reference
     */
    T next();

}
