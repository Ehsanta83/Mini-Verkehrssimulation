package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.human;

import cern.colt.matrix.DoubleMatrix1D;
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
    /**
     * defines the left upper position (row / column / height in cells / width in cells )
     */
    private final DoubleMatrix1D m_position = null;

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
    public void spriteinitialize( final int p_cellsize, final float p_unit )
    {
    }

    @Override
    public final DoubleMatrix1D position()
    {
        return m_position;
    }
}
