/**
 * 
 */
package de.tu_clausthal.in.meclab.verkehrssimulation.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.tu_clausthal.in.meclab.verkehrssimulation.core.Simulation;
import de.tu_clausthal.in.meclab.verkehrssimulation.core.classes.Parameters;

/**
 * @author Ehsan Tatasadi
 *
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Verkehrssimulation";
		config.width = Parameters.SCREEN_WIDTH;
		config.height = Parameters.SCREEN_HEIGHT;
		new LwjglApplication(new Simulation(), config);
	}
}
