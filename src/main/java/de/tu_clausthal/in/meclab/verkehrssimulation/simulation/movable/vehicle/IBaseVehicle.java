package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.vehicle;

import cern.colt.matrix.DoubleMatrix1D;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual.CVehiclesWay;
import de.tu_clausthal.in.meclab.verkehrssimulation.ui.CScreen;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.followingmodel.CNagelSchreckenberg;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.movable.IMovable;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.stat.trafficlight.EVehiclesTrafficLight;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual.CStreet;

import java.util.HashMap;

/**
 * vehicle abstract class
 */
public abstract class IBaseVehicle implements IMovable
{

    /**
     * sprite
     */
    private Sprite m_sprite;
    /**
     * defines the left upper position (row / column / height / width  )
     */
    private final DoubleMatrix1D m_position;


    protected IBaseVehicle( )
    {
       m_position = null;

    }





    @Override
    public IBaseVehicle call()
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
