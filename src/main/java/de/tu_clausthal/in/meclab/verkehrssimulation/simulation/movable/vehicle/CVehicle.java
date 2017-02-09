package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle;

import cern.colt.matrix.DoubleMatrix1D;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IBaseAgent;

/**
 * vehicle class
 */
public class CVehicle extends IBaseAgent
{
    /**
     * sprite
     */
    private Sprite m_sprite;
    /**
     * defines the left bottom position (column / row / height / width  )
     */
    private final DoubleMatrix1D m_position;

    protected CVehicle()
    {
       m_position = null;
    }

    @Override
    public CVehicle call()
    {
        return this;
    }

    @Override
    public Sprite sprite()
    {
        return m_sprite;
    }

    @Override
    public void spriteinitialize( final float p_unit )
    {
    }

    @Override
    public final DoubleMatrix1D position()
    {
        return m_position;
    }
}
