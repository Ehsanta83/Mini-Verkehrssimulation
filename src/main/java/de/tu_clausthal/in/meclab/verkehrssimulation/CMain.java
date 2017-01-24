package de.tu_clausthal.in.meclab.verkehrssimulation;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.tu_clausthal.in.meclab.verkehrssimulation.ui.CScreen;

import java.io.IOException;
import java.net.URISyntaxException;

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
     * @throws IOException on io errors
     * @throws URISyntaxException on URI syntax error
     */
    public static void main( final String[] p_arg ) throws IOException, URISyntaxException
    {
        // --- read configuration and initialize simulation ui -------------------------------------------------------------------------------------------------

        CConfiguration.INSTANCE.load( CCommon.PACKAGEPATH + "configuration.yaml" );

        final LwjglApplicationConfiguration l_config = new LwjglApplicationConfiguration();
        l_config.title = "Verkehrssimulation";
        l_config.width = CConfiguration.INSTANCE.windowWidth();
        l_config.height = CConfiguration.INSTANCE.windowHeight();
        new LwjglApplication( new CScreen( null ), l_config );
    }
}
