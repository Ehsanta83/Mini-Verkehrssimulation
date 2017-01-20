package de.tu_clausthal.in.meclab.verkehrssimulation;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.tu_clausthal.in.meclab.verkehrssimulation.ui.CScreen;

/**
 * main desktop application
 *
 * @author Ehsan Tatasadi
 */
public final class CMain
{
    /**
     * constructor
     */
    private CMain()
    {
    }

    /**
     * main method
     *
     * @param p_arg arguments
     */
    public static void main( final String[] p_arg )
    {
        final LwjglApplicationConfiguration l_config = new LwjglApplicationConfiguration();
        l_config.title = "Verkehrssimulation";
        l_config.width = 1024;
        l_config.height = 1024;
        new LwjglApplication( new CScreen(), l_config );
    }
}
