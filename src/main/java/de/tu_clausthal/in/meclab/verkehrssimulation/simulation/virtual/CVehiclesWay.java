package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import de.tu_clausthal.in.meclab.verkehrssimulation.CCommon;

import java.util.List;

/**
 * vehicles way class
 */
public class CVehiclesWay extends IBaseWay
{
    /**
     * constructor
     *
     * @param p_position list of left bottom position
     * @param p_rotation rotation
     * @param p_width width
     * @param p_height height
     */
    public CVehiclesWay( final List<Integer> p_position, final int p_rotation, final int p_width, final int p_height )
    {
        super( p_position, p_rotation, p_width, p_height );
    }

    @Override
    public void spriteinitialize( final float p_unit )
    {
        super.spriteinitialize(  p_unit, new Texture( Gdx.files.internal( CCommon.PACKAGEPATH + "street.png" ) ) );
    }
}
