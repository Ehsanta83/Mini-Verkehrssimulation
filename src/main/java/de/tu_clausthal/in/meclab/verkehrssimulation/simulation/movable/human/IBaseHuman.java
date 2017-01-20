package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.human;

import com.badlogic.gdx.graphics.g2d.Sprite;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IMovable;

/**
 * human abstract class
 *
 * @author Ehsan Tatasadi
 */
public abstract class IBaseHuman implements IMovable
{
    /**
     * sprite
     */
    private Sprite m_sprite;

    @Override
    public Object call()
    {
        return null;
    }

    @Override
    public Sprite sprite()
    {
        return m_sprite;
    }
}
