package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.human;

import com.badlogic.gdx.graphics.g2d.Sprite;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IExecutable;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IMovable;

/**
 * human abstract class
 */
public abstract class IBaseHuman implements IMovable
{
    /**
     * sprite
     */
    private Sprite m_sprite;

    @Override
    public IBaseHuman call()
    {
        return this;
    }

    @Override
    public Sprite sprite()
    {
        return m_sprite;
    }

    @Override
    public void spriteinitialize(final int p_xPosition, final int p_yPosition, final int p_rotation)
    {
    }
}
