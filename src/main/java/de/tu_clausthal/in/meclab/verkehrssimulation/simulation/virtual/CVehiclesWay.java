package de.tu_clausthal.in.meclab.verkehrssimulation.simulation.virtual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import de.tu_clausthal.in.meclab.verkehrssimulation.CCommon;
import de.tu_clausthal.in.meclab.verkehrssimulation.simulation.IObject;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * vehicles way class
 */
public class CVehiclesWay extends IBaseWay
{
    /**
     * filename of the texture pic
     */
    private static final String TEXTURE_FILE_NAME = CCommon.PACKAGEPATH + "vehicles_way.png";

    /**
     * constructor
     *
     * @param p_spriteposition list of left bottom position of the sprite
     * @param p_leftbottom leftbottom position in grid
     * @param p_righttop righttop position in grid
     * @param p_rotation rotation
     */
    public CVehiclesWay( final List<Integer> p_spriteposition, final List<Integer> p_leftbottom, final List<Integer> p_righttop, final int p_rotation )
    {
        super( p_spriteposition, p_leftbottom, p_righttop, p_rotation );
    }

    @Override
    public void spriteinitialize( final float p_unit )
    {
        super.spriteinitialize(  p_unit, new Texture( Gdx.files.internal( TEXTURE_FILE_NAME ) ) );
    }

    @Override
    public <T extends IObject> Stream<ILiteral> literal( final T... p_object )
    {
        return this.literal( Arrays.stream( p_object ) );
    }

    @Override
    public <T extends IObject> Stream<ILiteral> literal( final Stream<T> p_object )
    {
        return null;
    }
}
